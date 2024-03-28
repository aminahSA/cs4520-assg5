package com.cs4520.assignment5

import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            setContent {
                // Create a NavHost
                Navigation()
        }
    }
}
