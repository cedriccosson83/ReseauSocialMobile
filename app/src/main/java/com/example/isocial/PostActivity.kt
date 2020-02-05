package com.example.isocial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        val intent = intent

        if (intent != null) {
            val postId: String = intent.getStringExtra("post")
            if (postId != null) { // tu peux manipuler user !
                showPost(postId)
            }

        }


    }

    //This function allows to show the name of the user
    fun showPost(postId : String) {

        val myRef = database.getReference("posts")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                var post: Post
                for(value in dataSnapshot.children ) {
                    post = Post(value.child("userid").value.toString(), value.child("postid").value.toString(), "date def", value.child("content").value.toString(),null,null)
                    if(post.postid == postId){
                        textViewName.text = "${post.userid}"
                        textViewContent.text = "${post.content}"
                        showUserName(post.userid)
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.w("post", "Failed to read value.", error.toException())
            }
        })


    }

    //This function allows to show the name of the user
    fun showUserName(userId : String) {

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

    /*fun showPost(post : Post){
        textViewName.text= "${post.user?.firstName} ${post.user?.lastName}"
        textViewContent.text = "${post.textContent}"
    }*/
}
