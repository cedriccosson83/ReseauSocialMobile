package com.example.isocial

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_write.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class WritePostActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        auth = FirebaseAuth.getInstance()
        publishBTN.setOnClickListener{
            val userid = auth.currentUser?.uid
            if (userid != null){
                newPost(userid, Date().toString(), publish_field.text.toString())
            } else {
                Toast.makeText(this, "Utilisateur non trouvé", Toast.LENGTH_LONG)
            }
        }
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