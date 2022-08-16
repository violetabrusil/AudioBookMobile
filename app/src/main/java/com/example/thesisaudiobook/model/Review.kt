package com.example.thesisaudiobook.model

class Review {

    private var idReview: Int? = null
    private var audiobook: AudioBookList? = null
    private var userId: String? = null
    private var comment: String? = null
    private var rating: String? = null

    constructor() {}

    constructor(
        idReview: Int?,
        audiobook: AudioBookList?,
        userId: String?,
        comment: String?,
        rating: String?
    ) {
        this.idReview = idReview
        this.audiobook = audiobook
        this.userId = userId
        this.comment = comment
        this.rating = rating
    }
}