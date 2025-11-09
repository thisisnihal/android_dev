package com.example.navtutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.TextStyle


@Composable
fun LoginScreen() {

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    val brush = remember {
        Brush.linearGradient(
            colors = listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue, Color.Magenta)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "logo",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )
        Text(text = "Welcome Back", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Login to your Account")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(brush = brush),
            onValueChange = {
                email = it
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            label = {
                Text(text = "Email address")
            })

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password, singleLine = true, onValueChange = {
            password = it
        }, label = {
            Text(text = "Password")
        }, visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {}) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

//        TextButton(onClick = {}) {
//            Text(text = "Forgot Password?")
//        }

        Text(text = "Forgot Password?", modifier = Modifier.clickable {

        })

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Or sign in with")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "logo",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {

                    },
                contentScale = ContentScale.Fit
            )

            Image(
                painter = painterResource(id = R.drawable.twitter),
                contentDescription = "logo",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {

                    },
                contentScale = ContentScale.Fit
            )

            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "logo",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {

                    },
                contentScale = ContentScale.Fit
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}