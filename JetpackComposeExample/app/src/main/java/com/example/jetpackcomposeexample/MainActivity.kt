package com.example.jetpackcomposeexample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeexample.animation.AnimationExample
import com.example.jetpackcomposeexample.image.ImageExample
import com.example.jetpackcomposeexample.navigation.NavExample
import com.example.jetpackcomposeexample.storage.StorageActivity
import com.example.jetpackcomposeexample.text.SimpleTextActivity
import com.example.jetpackcomposeexample.ui.theme.JetpackComposeExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeExampleTheme() {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    val examples = listOf(
        "Simple Text Example" to SimpleTextActivity::class.java,
        "Navigation Example" to NavExample::class.java,
        "Animation Example" to AnimationExample::class.java,
        "Image Example" to ImageExample::class.java,
        "Storage Example" to StorageActivity::class.java
        )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(examples.size) { i ->
            Button(
                onClick = {
                    context.startActivity(Intent(context, examples[i].second))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(examples[i].first)
            }
        }
    }
}