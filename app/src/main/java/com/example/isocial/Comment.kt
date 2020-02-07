package com.example.isocial

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Comment (
    var userid: String = "",
    var postId: String = "",
    var date: String? = "",
    var content: String? = "",
    var likedBy: ArrayList<User>? = ArrayList()
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userid" to userid,
            "postId" to postId,
            "date" to date,
            "content" to content,
            "likedBy" to likedBy
        )
    }
}