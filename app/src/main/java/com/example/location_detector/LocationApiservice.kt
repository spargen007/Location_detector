package com.example.location_detector


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface LocationApiservice {
    @GET("city.json")
    suspend fun getlocations(): Response<List<Location>>
}

object RetrofitBuilder{
    private const val BASE_URL = "https://raw.githubusercontent.com/spargen007/network_data/main/"
    val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: LocationApiservice = getRetrofit().create(LocationApiservice::class.java)
}
