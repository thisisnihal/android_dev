package com.example.navtutorial

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyAppNavigation() {
    val navController  = rememberNavController()


    NavHost(navController =  navController, startDestination = Routes.ScreenA, builder = {
        composable(Routes.ScreenA) {
            ScreenA(navController)
        }
        composable(Routes.ScreenB + "/{name}") {
            val name = it.arguments?.getString("name")
            ScreenB(navController, name?: "No Name")
        }

    })
}