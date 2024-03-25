package com.cs4520.assignment5

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(navController: NavController?) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { /* Handle next action */ })
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { /* Handle done action */ })
        )

        Button(
            onClick = { /* Handle login button click */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Login")
        }
    }

//    var username by remember { mutableStateOf(TextFieldValue()) }
//    var password by remember { mutableStateOf(TextFieldValue()) }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        OutlinedTextField(
//            value = username,
//            onValueChange = { username = it },
//            label = { Text("Username") },
//            modifier = Modifier.padding(16.dp)
//        )
//
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password") },
//            modifier = Modifier.padding(16.dp)
//        )
//
//        Button(
//            onClick = { /* Handle login button click */ },
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text("Login")
//        }
//    }
}

//@Composable
//fun MyApp() {
//    Scaffold(
//        content = {
//            LoginScreen()
//        }
//    )
//}

@Preview
@Composable
fun LoginScreenPreview() {
    // Provide a dummy NavController instance for preview
    val dummyNavController: NavController? = null
    LoginScreen(navController = null)
}