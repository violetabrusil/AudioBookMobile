package com.example.thesisaudiobook.model

import com.example.thesisaudiobook.model.IdAudioBook

class PlayList {
    var idPlayList: Int? = null
    var namePlayList: String? = null
    var userId: String? = null
    var idAudioBooks: Set<IdAudioBook>? = null

    constructor() {}
    constructor(
        idPlayList: Int?,
        namePlayList: String?,
        userId: String?,
        idAudioBooks: Set<IdAudioBook>?
    ) {
        this.idPlayList = idPlayList
        this.namePlayList = namePlayList
        this.userId = userId
        this.idAudioBooks = idAudioBooks
    }

    override fun toString(): String {
        return "PlayList{" +
                "idPlayList=" + idPlayList +
                ", namePlayList='" + namePlayList + '\'' +
                ", userId='" + userId + '\'' +
                ", idAudioBooks=" + idAudioBooks +
                '}'
    }
}