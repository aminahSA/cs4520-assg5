package com.cs4520.assignment5

import com.cs4520.assignment5.Api.ENDPOINT
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET(ENDPOINT)
    suspend fun getProducts(@Query("page") page: Int? = null): Response<List<Product>>

    companion object {
        fun create(): ProductService {
            val retrofit = Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ProductService::class.java)
        }
    }
}