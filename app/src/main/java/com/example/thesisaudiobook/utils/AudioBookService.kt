package com.example.thesisaudiobook.utils

import com.example.thesisaudiobook.model.AudioBookList
import retrofit2.Call
import retrofit2.http.GET

interface AudioBookService {

    @GET("getAllAudioBooks")
    fun getAllAudioBooks(): Call<List<AudioBookList>>
}