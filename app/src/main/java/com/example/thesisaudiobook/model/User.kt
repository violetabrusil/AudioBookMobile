package com.example.thesisaudiobook.model

class User {
    var userName: String? = null
    var email: String? = null
    var access: String? = null
    var photo: String? = null
    var rol: String? = null

    constructor() {}
    constructor(userName: String?, email: String?, access: String?, photo: String?, rol: String?) {
        this.userName = userName
        this.email = email
        this.access = access
        this.photo = photo
        this.rol = rol
    }
}