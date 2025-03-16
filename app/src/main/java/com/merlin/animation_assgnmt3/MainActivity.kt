package com.merlin.animation_assgnmt3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
//import androidx.navigation.compose.rememberNavController
import com.merlin.animation_assgnmt3.ui.theme.Animation_Assgnmt3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Animation_Assgnmt3Theme {

                    NavigationGraph()

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main_menu"
    ) {
        composable("main_menu") {
            MainMenuScreen(navController)
        }

        composable("animation1") {
            Animation1Screen(navController)
        }

        composable("animation2") {
            Animation2Screen(navController)
        }

        composable("animation3") {
            Animation3Screen(navController)
        }

        composable("animation4") {
            Animation4Screen(navController)
        }
    }

}