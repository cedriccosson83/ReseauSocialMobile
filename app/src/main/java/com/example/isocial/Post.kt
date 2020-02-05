package com.example.isocial

import com.google.firebase.database.*


@IgnoreExtraProperties
data class Post (
    var userid: String = "",
    var postid: String = "",
    var date: String? = "",
    var content: String? = "",
    var likes: ArrayList<User>? = ArrayList(),
    var comments: ArrayList<Comment>? = ArrayList()
) {

    val database = FirebaseDatabase.getInstance()

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

    fun getUser(): User {
        lateinit var user : User

        val userRef = database.getReference("users")
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                user = dataSnapshot.child(userid).getValue(User::class.java)!! //This is a1
            }

            override fun onCancelled(dbe: DatabaseError) {
                throw dbe.toException()
            }

        })

        return user
    }
}