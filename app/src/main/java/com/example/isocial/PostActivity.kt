package com.example.isocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_post.textViewName
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.recycler_view_post_cell.view.*

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
                    post = Post(value.child("userid").value.toString(), value.child("postid").value.toString(), "date def", value.child("content").value.toString(),null,null)
                    val postId: String = intent.getStringExtra("post")
                    if(post.postid == postId){

                        textViewContent.text = "${post.content}"
                        break
                    }
                    showUser(post.userid)

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


                    var comment : Comment = Comment(value.child("userid").value.toString(), value.child("postId").value.toString(), "date def", value.child("content").value.toString(),null)
                    if(comment.postId == postId){
                        comments.add(comment)
                    }

                }
                comments.reverse()
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
                        textViewName.text = "${user.firstname} ${user.lastname}"
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
        val comment = currentUserID?.let { Comment(it, postId, null, content, null) }
        dbComments.child(newId).setValue(comment)



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
