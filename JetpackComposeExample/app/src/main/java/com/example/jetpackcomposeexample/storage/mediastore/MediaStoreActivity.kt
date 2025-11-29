package com.example.jetpackcomposeexample.storage.mediastore


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.jetpackcomposeexample.image.NetworkImageCoil
import kotlinx.coroutines.launch

class MediaStoreActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var url by remember { mutableStateOf("") }
            var showImage by remember { mutableStateOf(false) }
            val context = LocalContext.current

            Column(Modifier.padding(16.dp)) {

                Text("MediaStore: Download Image",
                    style = MaterialTheme.typography.headlineMedium)

                Spacer(Modifier.height(16.dp))

                // URL input field
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("Paste image URL") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                // Show Image button
                Button(
                    onClick = { showImage = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Preview Image")
                }

                Spacer(Modifier.height(16.dp))

                // Show image if preview enabled
                if (showImage && url.isNotBlank()) {
                    NetworkImageCoil(url)
                }

                Spacer(Modifier.height(20.dp))

                // Download button
                Button(
                    onClick = {
                        downloadImage(url, context) { success ->
                            Toast.makeText(
                                context,
                                if (success) "Saved to Gallery!" else "Failed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Download to Gallery")
                }
            }
        }
    }

    /** Loads bitmap using Coil and saves via MediaStoreUtils */
    private fun downloadImage(
        url: String,
        context: android.content.Context,
        callback: (Boolean) -> Unit
    ) {
        val loader = ImageLoader(context)

        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false) // needed to get Bitmap
            .build()

        lifecycleScope.launch {
            val result = try {
                (loader.execute(request) as SuccessResult).drawable
            } catch (e: Exception) {
                callback(false)
                return@launch
            }

            val bitmap = (result as BitmapDrawable).bitmap
            val saved = MediaStoreUtils.saveImageToGallery(context, bitmap)
            callback(saved)
        }
    }

}
