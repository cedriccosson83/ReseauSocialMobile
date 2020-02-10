package com.example.isocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_post.textViewName2
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PostActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        auth = FirebaseAuth.getInstance()

        val intent = intent
        if (intent != null) {
            showPost(intent)

        }
        recyclerViewComments.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerViewComments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val postId: String = intent.getStringExtra("post")
        showComments(postId)

        buttonPublishComment.setOnClickListener {
            var content:String = editTextComment.text.toString()
            newComment(postId,content)

        }
    }

    //This function allows to show the name of the user
    fun showPost(intent : Intent) {

        val myRef = database.getReference("posts")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                var post: Post
                for(value in dataSnapshot.children ) {
                    post = Post(value.child("userid").value.toString(), value.child("postid").value.toString(), value.child("date").value.toString(), value.child("content").value.toString(),null,null)
                    val postId: String = intent.getStringExtra("post")
                    if(post.postid == postId){

                        textViewContent2.text = "${post.content}"
                        showUser(post.userid)
                        showDate(post.date, textViewDatePost)
                        break
                    }


                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("post", "Failed to read value.", error.toException())
            }
        })
    }


    fun showComments(postId: String) {

        val myRef = database.getReference("comments")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                val comments : ArrayList<Comment> = ArrayList<Comment>()
                for(value in dataSnapshot.children ) {


                    var comment : Comment = Comment(value.child("userid").value.toString(), value.child("postId").value.toString(), value.child("date").value.toString(), value.child("content").value.toString(),null)
                    if(comment.postId == postId){
                        comments.add(comment)
                    }

                }
                //comments.reverse()
                recyclerViewComments.adapter = CommentAdapter(comments)
                Log.d("comment", comments.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("comment", "Failed to read value.", error.toException())
            }
        })


    }

    fun showUser(userId : String) {

        val myRef = database.getReference("users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                var user: User
                for(value in dataSnapshot.children ) {
                    user = User(value.child("userid").value.toString(), value.child("email").value.toString(), value.child("firstname").value.toString(), value.child("lastname").value.toString(),value.child("birthdate").value.toString(),null,null,null)
                    if(user.userid == userId){
                        textViewName2.text = "${user.firstname} ${user.lastname}"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.w("post", "Failed to read value.", error.toException())
            }
        })


    }
    private fun newComment(postId: String,content: String){

        val dbComments = database.getReference("comments")
        val newId = dbComments.push().key
        val currentUserID = auth.currentUser?.uid

        if (newId == null) {
            Log.w("ERROR", "Couldn't get push key for comments")
            return
        }
        val sdf = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault())
        val currentDateandTime: String = sdf.format(Date())

        val comment = currentUserID?.let { Comment(it, postId, currentDateandTime, content, null) }
        dbComments.child(newId).setValue(comment)



    }

}
