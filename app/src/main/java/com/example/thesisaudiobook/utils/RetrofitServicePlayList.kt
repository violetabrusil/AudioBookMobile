package com.example.thesisaudiobook.utils

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServicePlayList {
    var retrofit: Retrofit? = null
        private set

    private fun initializeRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.97:8080/api/playList/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    init {
        initializeRetrofit()
    }
}