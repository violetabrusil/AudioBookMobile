package com.example.thesisaudiobook.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thesisaudiobook.utils.RetrofitServiceAudioBook
import kotlinx.coroutines.launch

class AudioBookViewModel : ViewModel()  {

    val audioBookList : MutableLiveData<List<AudioBookList>> = MutableLiveData()

    fun getAudioBooks() {
        viewModelScope.launch {
            audioBookList.value = RetrofitServiceAudioBook.retrofit.getAllAudioBooks()
        }
    }
}