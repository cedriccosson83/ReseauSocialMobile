package com.example.isocial

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        readPosts()


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

    fun readPosts(): ArrayList<Post> {

        var posts : ArrayList<Post> = ArrayList<Post>()
        val myRef = database.getReference("posts")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(value in dataSnapshot.children ) {
                   var post : Post = Post(value.child("userid").value.toString(), value.child("postid").value.toString(), "date def", value.child("content").value.toString(),null,null)
                    Log.d("post", "Value is: ${value.child("content").value}")
                    posts.add(post)
                }


            }
            override fun onCancelled(error: DatabaseError) {

                Log.w("post", "Failed to read value.", error.toException())
            }
        })

        return posts

    }
}