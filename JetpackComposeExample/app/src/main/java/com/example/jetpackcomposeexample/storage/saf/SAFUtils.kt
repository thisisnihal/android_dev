package com.example.jetpackcomposeexample.storage.saf

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.webkit.MimeTypeMap
import androidx.documentfile.provider.DocumentFile

object SAFUtils {

    /** Convert Uri → DocumentFile */
    fun fromUri(context: Context, uri: Uri): DocumentFile? {
        return DocumentFile.fromTreeUri(context, uri)
    }

    /** List all files inside a picked directory */
    fun listFiles(context: Context, treeUri: Uri): List<DocumentFile> {
        val docFile = DocumentFile.fromTreeUri(context, treeUri)
        return docFile?.listFiles()?.toList() ?: emptyList()
    }

    /** Get file MIME type */
    fun getMimeType(uri: Uri, context: Context): String? {
        return context.contentResolver.getType(uri)
            ?: MimeTypeMap.getFileExtensionFromUrl(uri.toString())?.let {
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(it)
            }
    }

    /** Read file if it is text */
    fun readTextFile(context: Context, uri: Uri): String {
        return try {
            context.contentResolver.openInputStream(uri)
                ?.bufferedReader()?.readText()
                ?: "Unable to read"
        } catch (e: Exception) {
            "Not a readable text file!"
        }
    }

    /** Get file size (human readable) */
    fun getFileSize(file: DocumentFile): String {
        val size = file.length()
        return when {
            size < 1024 -> "$size B"
            size < 1024 * 1024 -> "${size / 1024} KB"
            else -> "${size / (1024 * 1024)} MB"
        }
    }
}