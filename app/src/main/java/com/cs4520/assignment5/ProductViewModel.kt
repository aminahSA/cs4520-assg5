package com.cs4520.assignment5

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ProductViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: MutableLiveData<List<Product>>
        get() = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean>
        get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: MutableLiveData<String?>
        get() = _error

    private val _noResults = MutableLiveData<Boolean>()
    val noResults: MutableLiveData<Boolean>
        get() = _noResults

    private val productService = ProductService.create()

    private lateinit var myAppContext: Context

    private var db = DatabaseProvider.getDatabase(null)

    @Composable
    fun Initialize() {
        myAppContext = MyApp.getAppContext()
    }

    fun fetchProducts(page: Int? = null) {
        _isLoading.value = true
        Log.d("My", "set to loading.")

        viewModelScope.launch {
            try {
                //check if device is online and if so get the products from the database
                if (isNetworkAvailable(myAppContext)) {
                    Log.d("My", "network available.")
                    val response = productService.getProducts(page)
                    if (response.isSuccessful) {
                        Log.d("My", "response successful.")
                        _products.value = response.body() ?: emptyList()
                        if (_products.value.isNullOrEmpty()) {
                            Log.d("My", "empty product set.")
                            _noResults.value = true
                        } else {
                            Log.d("My", "product set is not empty.")
                            // Save products to the database
                            viewModelScope.launch {
                                val toSave = _products.value
                                if (toSave != null) {
                                    Log.d("My","toSave size:" + toSave.size.toString())
                                }
                                if (toSave != null) {
                                    db.saveProducts(toSave)
                                    Log.d("My", "products saved to database.")
                                    Log.d("My", db.retrieveProducts().size.toString())
                                }
                            }
                        }
                    } else {
                        Log.d("My", "an error was encountered")
                        _error.value = "Error: ${response.code()} ${response.message()}"
                    }
                } else {
                    Log.d("My", "device was offline.")
                    // Fetch data from the database if offline
                    viewModelScope.launch {
                        _products.value = db.retrieveProducts()
                        Log.d("My", "products fetched from database.")
                        if (_products.value.isNullOrEmpty()) {
                            Log.d("My", "no products in database.")
                            _noResults.value = true
                    }
                    }
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
                Log.d("My", "no longer loading.")
            }
        }
    }

    // Check network connectivity
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // For devices running Android Q (API 29) or later
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        }

        // For devices running Android versions earlier than Android Q
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


}
