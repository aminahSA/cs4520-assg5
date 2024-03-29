package com.cs4520.assignment5

import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels

class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DatabaseProvider.getDatabase(applicationContext)

            setContent {
                // Create a NavHost
                Navigation()
        }
    }

    override fun onResume() {
        super.onResume()
        productViewModel.fetchProducts()
    }
}
