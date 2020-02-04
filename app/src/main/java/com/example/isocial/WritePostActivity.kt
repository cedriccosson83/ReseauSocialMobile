package com.example.isocial

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class WritePostActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        //newPost("CedricId123", "04/02/2020", "Bonjour je suis un message écrit par" +
        //        "Cédric cosson et je fais une grande quantité de caractères")
    }

    private fun newPost(userId: String, date: String, content: String) {

        val dbPosts = database.getReference("posts")
        val newId = dbPosts.push().key
        if (newId == null) {
            Log.w("ERROR", "Couldn't get push key for posts")
            return
        }

        val post = Post(userId, newId, date, content, ArrayList(), ArrayList())
        dbPosts.child(newId).setValue(post)
    }
}