package com.example.happybirthday

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.ui.theme.HappyBirthdayTheme
import com.example.happybirthday.ui.theme.Purple80
import com.example.happybirthday.ui.theme.PurpleGrey80

class Intro1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HappyBirthdayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Nihal", modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!, How are you doing?",
        modifier = modifier.padding(24.dp),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    HappyButton(
        "Click me", modifier = modifier
    )
    HappyImagePng(modifier = modifier)
    HappyImageVector(modifier = modifier)
    CounterButton(modifier = modifier)
    BoxComponent(modifier = modifier)
    ColumnComponent(modifier = modifier)
    RowComponent(modifier = modifier)
    EditTextComponent(modifier = modifier)
    OutlineEditTextComponent(modifier = modifier)
}

@Composable
fun HappyButton(text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = {
            Log.d("testing", "Greeting Composable is called")
        }, modifier = modifier
            .padding(80.dp)
            .fillMaxWidth()
    ) {
        Text("Click Me")
    }
}

@Composable
fun HappyImagePng(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.ic_launcher_background),
        contentDescription = null,
        modifier = modifier
            .padding(top = 140.dp)
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun HappyImageVector(modifier: Modifier = Modifier) {
    Image(
        imageVector = Icons.Default.AccountBox,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(top = 350.dp)
            .height(50.dp)
    )
}

@Composable
fun CounterButton(modifier: Modifier = Modifier) {
    val counter =
        remember { mutableStateOf(0) } // remember preserve the state while re-rendering the compose
    Button(
        onClick = {
            counter.value += 1
        },
        modifier = modifier
            .padding(top = 200.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
    ) {
        Text("Counter: ${counter.value}")
    }
}

@Composable
fun BoxComponent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "I am at Bottom center",
            modifier = modifier.align(Alignment.BottomCenter),
            color = Purple80
        )
        Text(
            "I am at Top Center",
            modifier = modifier.align(Alignment.TopCenter),
            color = Purple80
        )

    }
}


@Composable
fun ColumnComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            "First text element in column comp",
            modifier = modifier,
            color = PurpleGrey80,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Second text element in column comp",
            modifier = modifier,
            color = PurpleGrey80,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
fun RowComponent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 400.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            "First text element in row comp",
            modifier = modifier,
            color = PurpleGrey80,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Second text element in row comp",
            modifier = modifier,
            color = PurpleGrey80,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
fun EditTextComponent(modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }

    TextField(
        value = name,
        onValueChange = { it ->
            name = it
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        placeholder = { Text("Type here!!") }

    )
}

@Composable
fun OutlineEditTextComponent(modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        modifier = modifier
            .padding(top = 100.dp)
            .fillMaxWidth(),
        placeholder = { Text("Type here!!") }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HappyBirthdayTheme {
        Greeting("Nihal")
    }
}