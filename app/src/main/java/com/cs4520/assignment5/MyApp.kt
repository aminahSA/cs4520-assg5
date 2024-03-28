package com.cs4520.assignment5

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class MyApp : Application() {

    companion object {
        private lateinit var instance: MyApp

        @Composable
        fun getAppContext(): Context {
            return LocalContext.current

        }
    }

    init {
        instance = this
    }
}
