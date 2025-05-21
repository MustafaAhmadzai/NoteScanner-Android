package com.example.notescanningolderversion.network

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

suspend fun sendImageToServer(file: File, context: Context) {
    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
    val multipart = MultipartBody.Part.createFormData("file", file.name, requestBody)

    try {
        val response = RetrofitClient.apiService.uploadImage(multipart)
        if (response.isSuccessful) {
            val result = response.body()
            Log.d("API", "Summary: ${result?.summary}")
            Log.d("API", "PDF: ${result?.pdf_file}")
            result?.pdf_file?.let {
                pdf -> downloadAndOpenPdf(context, pdf)
            }
        } else {
            Log.e("API", "Upload failed: ${response.code()}")
        }
    } catch (e: Exception) {
        Log.e("API", "Error: ${e.localizedMessage}")
    }
}

suspend fun downloadAndOpenPdf(context: Context, filename: String) {
    try {
        val response = RetrofitClient.apiService.downloadPdf(filename)

        if (response.isSuccessful) {
            val file = File(context.cacheDir, filename)
            response.body()?.byteStream()?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }

            // Open PDF
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(intent, "Open PDF"))

        } else {
            Log.e("API", "PDF download failed: ${response.code()}")
        }
    } catch (e: Exception) {
        Log.e("API", "Download error: ${e.localizedMessage}")
    }
}

