package com.example.notescanningolderversion.network

import com.example.notescanningolderversion.model.ProcessResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

const val BASE_URL = "https://notescanner.onrender.com/"

interface ApiService {
    @Multipart
    @POST("process/")
    suspend fun uploadImage(@Part file: MultipartBody.Part): Response<ProcessResponse>

    @GET("download/{filename}")
    @Streaming
    suspend fun downloadPdf(@Path("filename") filename: String): Response<ResponseBody>
}