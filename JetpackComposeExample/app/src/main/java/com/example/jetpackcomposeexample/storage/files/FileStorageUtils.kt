package com.example.jetpackcomposeexample.storage.files


import android.content.Context
import java.io.File

object FileStorageUtils {

    // ---------- INTERNAL STORAGE ----------

    fun writeInternal(context: Context, name: String, content: String) {
        context.openFileOutput(name, Context.MODE_PRIVATE)
            .use { it.write(content.toByteArray()) }
    }

    fun readInternal(context: Context, name: String): String {
        return try {
            context.openFileInput(name).bufferedReader().readText()
        } catch (e: Exception) {
            "File not found!"
        }
    }

    fun deleteInternal(context: Context, name: String) {
        File(context.filesDir, name).delete()
    }

    fun listInternal(context: Context): List<String> {
        return context.filesDir.listFiles()?.map { it.name } ?: emptyList()
    }


    // ---------- EXTERNAL STORAGE (App Scoped) ----------

    fun writeExternal(context: Context, name: String, content: String) {
        val file = File(context.getExternalFilesDir(null), name)
        file.writeText(content)
    }

    fun readExternal(context: Context, name: String): String {
        val file = File(context.getExternalFilesDir(null), name)
        return if (file.exists()) file.readText() else "File not found!"
    }

    fun deleteExternal(context: Context, name: String) {
        val file = File(context.getExternalFilesDir(null), name)
        if (file.exists()) file.delete()
    }

    fun listExternal(context: Context): List<String> {
        return context.getExternalFilesDir(null)
            ?.listFiles()?.map { it.name } ?: emptyList()
    }
}
