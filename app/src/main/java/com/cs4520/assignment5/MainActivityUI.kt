package com.cs4520.assignment5

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MainActivityUI() {
    Column {
        Text(text = "Hello, Jetpack Compose!")
        Button(onClick = { /* Handle button click */ }) {
            Text(text = "Click me!")
        }
    }
}