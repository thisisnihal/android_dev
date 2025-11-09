package com.example.navtutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navtutorial.ui.theme.NavTutorialTheme

class MainActivity : ComponentActivity() {

    private val viewModel_1: StateTestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavTutorialTheme {
                val viewModel: StateTestViewModel =
                    ViewModelProvider(this)[StateTestViewModel::class.java]
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {


                    //-------------------------------------
                  //  MyAppNavigation()
                    //-----------------------------------

                    //-------------------------------------
                    // state example screen using viewModel
                 //   ScreenC(viewModel_1) // both viewModel as well as viewModel_1 would work seamlessly

                    //-----------------------------------


                    //-------------------------------------

                    LoginScreen()

                    //-----------------------------------
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavTutorialTheme {
        Greeting("Android")
    }
}