package com.example.thesisaudiobook.model

import java.io.Serializable

class AudioBookList : Serializable {

    private var idAudioBook: Int? = null
    private var titleAudioBook: String? = null
    private var author: String? = null
    private var sipnosis: String? = null
    private var urlImage: String? = null
    private var urlAudio: String? = null
    private var gender: String? = null
    private var yearOfPublication: String? = null
    private var reviews: Set<Review>? = null
    private var userId: String? = null

    constructor(){}

    constructor(
        idAudioBook: Int?,
        titleAudioBook: String?,
        author: String?,
        sipnosis: String?,
        urlImage: String?,
        urlAudio: String?,
        gender: String?,
        yearOfPublication: String?,
        reviews: Set<Review>,
        userId: String?
    ) {
        this.idAudioBook = idAudioBook
        this.titleAudioBook = titleAudioBook
        this.author = author
        this.sipnosis = sipnosis
        this.urlImage = urlImage
        this.urlAudio = urlAudio
        this.gender = gender
        this.yearOfPublication = yearOfPublication
        this.reviews = reviews
        this.userId = userId
    }

    fun getIdAudioBook(): Int? {
        return this.idAudioBook
    }

    fun setIdAudioBook(idAudioBook: Int?) {
        this.idAudioBook = idAudioBook!!
    }

    fun getTitleAudiobook(): String? {
        return this.titleAudioBook
    }

    fun setTitleAudiobook(titleAudioBook: String?) {
        this.titleAudioBook = titleAudioBook!!
    }

    fun getAuthor(): String? {
        return this.author
    }

    fun setAuthor(author: String?) {
        this.author = author!!
    }

    fun getUrlImage(): String? {
        return this.urlImage
    }

    fun setURLImage(author: String?) {
        this.urlImage = urlImage!!
    }

    fun getUrlAudio(): String? {
        return this.urlAudio
    }

    fun setUrlAudio(urlAudio: String?) {
        this.urlAudio = urlAudio!!
    }

    fun getGender(): String? {
        return this.gender
    }

    fun setGender(gender: String?) {
        this.gender = gender!!
    }





}