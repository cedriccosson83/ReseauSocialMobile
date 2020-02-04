package com.example.isocial

class Post{

    var nameUser : String ?= null
    var textContent : String ?= null

    constructor(nameUser: String?, textContent: String?) {
        this.nameUser = nameUser
        this.textContent = textContent
    }
}