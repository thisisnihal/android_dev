package com.example.jetpackcomposeexample.storage.files


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ExternalStorageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fs = FileStorageUtils

        setContent {

            var fileName by remember { mutableStateOf("") }
            var fileContent by remember { mutableStateOf("") }
            var output by remember { mutableStateOf("") }

            var fileList by remember { mutableStateOf(fs.listExternal(this)) }

            Column(Modifier.padding(16.dp)) {

                Text("External (App Scoped) Storage CRUD",
                    style = MaterialTheme.typography.headlineMedium)

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = fileName,
                    onValueChange = { fileName = it },
                    label = { Text("File Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = fileContent,
                    onValueChange = { fileContent = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        fs.writeExternal(this@ExternalStorageActivity, fileName, fileContent)
                        fileList = fs.listExternal(this@ExternalStorageActivity)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Create/Update File") }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        output = fs.readExternal(this@ExternalStorageActivity, fileName)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Read File") }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        fs.deleteExternal(this@ExternalStorageActivity, fileName)
                        fileList = fs.listExternal(this@ExternalStorageActivity)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Delete File") }

                Spacer(Modifier.height(16.dp))

                Text("Output:\n$output")

                Spacer(Modifier.height(16.dp))
                Divider()
                Spacer(Modifier.height(16.dp))

                Text("External Files:", style = MaterialTheme.typography.titleMedium)
                LazyColumn {
                    items(fileList) { file ->
                        Text("• $file")
                    }
                }
            }
        }
    }
}
