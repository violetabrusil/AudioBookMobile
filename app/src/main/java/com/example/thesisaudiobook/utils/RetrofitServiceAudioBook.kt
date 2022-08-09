package com.example.thesisaudiobook.utils

import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServiceAudioBook {
    var retrofit: Retrofit? = null
        private set

    private fun initializeRetrofit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = okhttp3.OkHttpClient.Builder().addInterceptor(interceptor).build()
        retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.97:8080/api/audioBook/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()


    }

    init {
        initializeRetrofit()
    }
}