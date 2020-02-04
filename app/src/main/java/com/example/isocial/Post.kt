package com.example.isocial

class Post{

    var user : User ?= null
    var textContent : String ?= null

    constructor(user: User?, textContent: String?) {
        this.user = user
        this.textContent = textContent
    }
}