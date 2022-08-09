package com.example.thesisaudiobook.model

class IdAudioBook {
    var idAudioBookPlayList: Int? = null
    var idAudioBook: String? = null

    constructor() {}
    constructor(idAudioBookPlayList: Int?, idAudioBook: String?) {
        this.idAudioBookPlayList = idAudioBookPlayList
        this.idAudioBook = idAudioBook
    }

    override fun toString(): String {
        return "IdAudioBook{" +
                "idAudioBookPlayList=" + idAudioBookPlayList +
                ", idAudioBook='" + idAudioBook + '\'' +
                '}'
    }
}