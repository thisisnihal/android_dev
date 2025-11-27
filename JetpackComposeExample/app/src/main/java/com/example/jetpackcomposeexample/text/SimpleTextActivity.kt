package com.example.jetpackcomposeexample.text

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.example.jetpackcomposeexample.ui.theme.JetpackComposeExampleTheme

class SimpleTextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeExampleTheme(darkTheme = true) { Text("This is Simple Text Example!") }
        }
    }
}