package com.example.thesisaudiobook.utils;

import com.example.thesisaudiobook.model.PlayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PlayListService {
    @POST("createPlayList")
    Call<PlayList> createPlayList(@Body PlayList playList);

}
