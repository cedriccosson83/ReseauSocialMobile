package com.example.isocial

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post (
    var userid: String = "",
    var postid: String = "",
    var date: String? = "",
    var content: String? = "",
    var likes: ArrayList<String> = ArrayList(),
    var comments: ArrayList<Comment>? = ArrayList()
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userid" to userid,
            "date" to date,
            "content" to content,
            "likes" to likes
        )

        /*
        class Post{

    var user : User ?= null
    var textContent : String ?= null

    constructor(user: User?, textContent: String?) {
        this.user = user
        this.textContent = textContent
        */
    }
}