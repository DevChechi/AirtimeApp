package com.perpetua.eazytopup.apis

import com.perpetua.eazytopup.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import com.google.gson.GsonBuilder

import com.google.gson.Gson




class RetrofitInstance {
    companion object{
        private val retrofit by lazy {
            //logging responses with HttpLoggingInterceptor
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            //okhttp client using the interceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

//            val gson = GsonBuilder()
//                .setLenient()
//                .create()
            //build
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(AirtimeTopupApi::class.java)
        }
    }
}