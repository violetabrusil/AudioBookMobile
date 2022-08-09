package com.example.thesisaudiobook.utils

import retrofit2.http.POST
import com.example.thesisaudiobook.model.PlayList
import retrofit2.Call
import retrofit2.http.Body

interface PlayListService {
    @POST("createPlayList")
    fun createPlayList(@Body playList: PlayList?): Call<PlayList?>?
}