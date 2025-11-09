package com.example.navtutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun ScreenC (viewModel: StateTestViewModel) {

    val name by viewModel.name.observeAsState(initial = "")

    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyText(name)
        MyTextField(name, onNameChange = {
            viewModel.onNameChange(it)
        })
    }
}

@Composable
fun MyText(name: String) {
    Text(text = "Hello $name", style = TextStyle(fontSize = 30.sp))
}

@Composable
fun MyTextField(name:String, onNameChange : (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = {
            onNameChange(it)
        }
    )
}