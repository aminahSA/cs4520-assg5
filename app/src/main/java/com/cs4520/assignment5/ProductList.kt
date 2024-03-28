package com.cs4520.assignment5

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text


@Composable
fun ProductListScreen() {
    val viewModel: ProductViewModel = viewModel()
    viewModel.Initialize()
    val productsState = remember { mutableStateOf<List<Product>>(emptyList()) }
    val loadingState = remember { mutableStateOf(false) }
    val noResultsState = remember { mutableStateOf(false) }
    val errorState = remember { mutableStateOf<String?>(null) }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(viewModel) {
        val productsObserver = Observer<List<Product>> { products ->
            if(!products.isNullOrEmpty()) {
                productsState.value = products
            }
        }

        val loadingObserver = Observer<Boolean> { isLoading ->
            loadingState.value = isLoading
        }

        val noResultsObserver = Observer<Boolean> { noResults ->
            noResultsState.value = noResults
        }

        val errorObserver = Observer<String?> { error ->
            if (error == null) {
                errorState.value = null
            } else {
                errorState.value = error
            }
        }

        viewModel.products.observe(lifecycleOwner, productsObserver)
        viewModel.isLoading.observe(lifecycleOwner, loadingObserver)
        viewModel.noResults.observe(lifecycleOwner, noResultsObserver)
        viewModel.error.observe(lifecycleOwner, errorObserver)

        onDispose {
            viewModel.products.removeObserver(productsObserver)
            viewModel.isLoading.removeObserver(loadingObserver)
            viewModel.noResults.removeObserver(noResultsObserver)
            viewModel.error.removeObserver(errorObserver)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.fetchProducts()
        Log.d("My", "products being fetched.")
    }

    val products = productsState.value
    val anError = errorState.value

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        if (loadingState.value) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
    } else if (noResultsState.value) {
        NoResults()
    } else if (anError != null) {
        DisplayError(anError)
    } else {ProductList(products = products)}
    }
}

@Composable
fun ProductList(products: List<Product>) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            itemsIndexed(products) { index, product ->
                ItemUI(product)
            }
        }
    }
}

@Composable
fun NoResults() {
    Box (modifier = Modifier.fillMaxSize()){
        Text(text = "no results to be displayed.", style = MaterialTheme.typography.body1)
    }
}

@Composable
fun DisplayError(error: String) {
    Box (modifier = Modifier.fillMaxSize()){
        Text(text = error, style = MaterialTheme.typography.body1)
    }
}

