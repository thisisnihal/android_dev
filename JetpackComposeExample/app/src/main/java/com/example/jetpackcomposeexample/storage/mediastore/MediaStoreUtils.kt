package com.example.jetpackcomposeexample.storage.mediastore

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore

object MediaStoreUtils {

    /** Saves a bitmap into MediaStore → Pictures */
    fun saveImageToGallery(
        context: Context,
        bitmap: Bitmap,
        filename: String = "image_${System.currentTimeMillis()}.jpg"
    ): Boolean {
        return try {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/StorageCourse")
            }

            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            ) ?: return false

            context.contentResolver.openOutputStream(uri)?.use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }

            true

        } catch (e: Exception) {
            false
        }
    }
}