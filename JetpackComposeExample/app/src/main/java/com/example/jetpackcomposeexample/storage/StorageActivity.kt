package com.example.jetpackcomposeexample.storage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeexample.storage.datastore.DataStoreActivity
import com.example.jetpackcomposeexample.storage.files.ExternalStorageActivity
import com.example.jetpackcomposeexample.storage.files.InternalStorageActivity
import com.example.jetpackcomposeexample.storage.mediastore.MediaStoreActivity
import com.example.jetpackcomposeexample.storage.room.RoomActivity
import com.example.jetpackcomposeexample.storage.saf.SafDirectoryPickerActivity
import com.example.jetpackcomposeexample.ui.theme.JetpackComposeExampleTheme

class StorageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeExampleTheme{
                Column(Modifier.padding(20.dp)) {

                    Button(onClick = {
                        startActivity(Intent(this@StorageActivity, RoomActivity::class.java))
                    }) { Text("Room CRUD") }

                    Button(onClick = {
                        startActivity(Intent(this@StorageActivity, DataStoreActivity::class.java))
                    }) { Text("DataStore Demo") }

                Button(onClick = {
                    startActivity(Intent(this@StorageActivity, InternalStorageActivity::class.java))
                }) { Text("Internal Storage CRUD") }

                Button(onClick = {
                    startActivity(Intent(this@StorageActivity, ExternalStorageActivity::class.java))
                }) { Text("External Storage CRUD") }

                Button(onClick = {
                    startActivity(Intent(this@StorageActivity, MediaStoreActivity::class.java))
                }) { Text("MediaStore (Save Image)") }

                Button(onClick = {
                    startActivity(Intent(this@StorageActivity, SafDirectoryPickerActivity::class.java))
                }) { Text("SAF (Pick File)") }
                }
            }
        }
    }
}
