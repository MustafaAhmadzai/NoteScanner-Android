package com.example.notescanningolderversion

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notescanningolderversion.ui.theme.NoteScanningOlderVersionTheme
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.example.notescanningolderversion.network.sendImageToServer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteScanningOlderVersionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScanNoteButton(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ScanNoteButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var photoFile by remember { mutableStateOf<File?>(null) }
    var capturedImagePath by remember { mutableStateOf<String?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            capturedImagePath = photoFile?.absolutePath
        }
    }

    LaunchedEffect(capturedImagePath) {
        capturedImagePath?.let { path ->
            val file = File(path)
            sendImageToServer(file, context)
        }
    }

    Button(
        onClick = {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
            photoFile = file

            val photoUri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }

            cameraLauncher.launch(intent)
        },
        modifier = modifier.padding(16.dp)
    ) {
        Text("Scan Note")
    }
}


@Preview(showBackground = true)
@Composable
fun ScanNoteButtonPreview() {
    NoteScanningOlderVersionTheme {
        ScanNoteButton()
    }
}

