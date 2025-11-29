package com.example.jetpackcomposeexample.storage.saf


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile

class SafDirectoryPickerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val saf = SAFUtils

        // SAF Folder picker launcher
        val pickFolderLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocumentTree()
        ) { uri ->
            if (uri != null) {
                // Persist access permission
                contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            pickedFolder.value = uri
        }

        setContent {

            var selectedFileContent by remember { mutableStateOf("") }
            val folderUri by pickedFolder
            var fileList by remember { mutableStateOf<List<DocumentFile>>(emptyList()) }

            // If folder changed, update file list
            LaunchedEffect(folderUri) {
                if (folderUri != null) {
                    fileList = saf.listFiles(this@SafDirectoryPickerActivity, folderUri!!)
                }
            }

            Column(Modifier.padding(16.dp)) {

                Text(
                    "SAF: Pick Folder & Browse Files",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = { pickFolderLauncher.launch(null) },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Pick Folder") }

                Spacer(Modifier.height(16.dp))
                Divider()
                Spacer(Modifier.height(16.dp))

                if (folderUri != null) {
                    Text("Selected Folder:\n${folderUri.toString()}")

                    Spacer(Modifier.height(16.dp))
                    Text("Files:", style = MaterialTheme.typography.titleMedium)

                    LazyColumn {
                        items(fileList) { file ->
                            FileItem(
                                file = file, onClick = {
                                    val mime =
                                        saf.getMimeType(file.uri, this@SafDirectoryPickerActivity)
                                    selectedFileContent = if (mime?.startsWith("text") == true) {
                                        saf.readTextFile(this@SafDirectoryPickerActivity, file.uri)
                                    } else {
                                        "Unsupported file type ($mime)"
                                    }
                                })
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                if (selectedFileContent.isNotEmpty()) {
                    Divider()
                    Spacer(Modifier.height(16.dp))

                    Text("File Content:", style = MaterialTheme.typography.titleMedium)
                    Text(selectedFileContent)
                }
            }
        }
    }

    companion object {
        val pickedFolder = mutableStateOf<Uri?>(null)
    }
}

@Composable
fun FileItem(
    file: DocumentFile, onClick: () -> Unit
) {
    Column(
        Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Text("📄 ${file.name}", style = MaterialTheme.typography.titleMedium)
        Text("Size: ${SAFUtils.getFileSize(file)}")
        Text("Type: ${file.type ?: "Unknown"}", style = MaterialTheme.typography.bodySmall)
    }
}
