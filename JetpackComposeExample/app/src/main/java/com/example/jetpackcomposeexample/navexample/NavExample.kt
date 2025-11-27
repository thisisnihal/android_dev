package com.example.jetpackcomposeexample.navexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeexample.ui.theme.JetpackComposeExampleTheme

class NavExample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeExampleTheme {
                App()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    // Fallback to ScreenA if null
    val currentScreen =
        AppScreen.valueOf(backStackEntry?.destination?.route ?: AppScreen.ScreenA.name)

    val canNavigateBack = navController.previousBackStackEntry != null

    Scaffold(
        topBar = {
            MyTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() })
        },
        contentWindowInsets = WindowInsets.systemBars,
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppNavHost(navController)
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreen.ScreenA.name) {
        composable(route = AppScreen.ScreenA.name) {
            ScreenA(onNextClick = {
                navController.navigate(AppScreen.ScreenB.name)
            })
        }

        composable(route = AppScreen.ScreenB.name) {
            ScreenB(
                onBackClick = { navController.navigateUp() },
                onNextClick = { navController.navigate(AppScreen.ScreenC.name) },
            )
        }

        composable(route = AppScreen.ScreenC.name) {
            ScreenC(onBackClick = { navController.navigateUp() }, onResetClick = {
                navController.popBackStack(
                    route = AppScreen.ScreenA.name, inclusive = false
                )
            })
        }

    }
}

@Composable
private fun ScreenA(
    onNextClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Screen A", style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNextClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Navigate to Screen B")
        }
    }
}


@Composable
private fun ScreenB(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Screen B", style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBackClick, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Navigate to Screen A")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onNextClick, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Navigate to Screen C")
        }

    }
}


@Composable
private fun ScreenC(
    onBackClick: () -> Unit,
    onResetClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Screen C", style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBackClick, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Navigate to Screen B")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onResetClick, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Navigate to Screen A (PopupTo with Inclusive)")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyTopAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary,
    ), title = {
        Text(text = currentScreen.title)
    }, modifier = modifier, navigationIcon = {
        if (canNavigateBack) {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                )
            }
        }
    })
}


@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    App()
}