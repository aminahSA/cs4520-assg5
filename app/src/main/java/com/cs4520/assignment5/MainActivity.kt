package com.cs4520.assignment5

import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            setContent {
                    // Create a NavHost
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login_fragment") {
                        // Specify the login fragment as the initial destination
                        composable("login_fragment") {
                            LoginScreen(navController = navController)
                        }
                        // Add other destinations as needed
                    }
        }
    }
}
