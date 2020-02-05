package com.example.isocial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_user.textViewName


class FeedActivity : AppCompatActivity() {


    val postsList : ArrayList<Post> = ArrayList()

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        showPosts()
        recyclerViewFeed.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerViewFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        buttonPublish.setOnClickListener {
            val intent = Intent(this, WritePostActivity::class.java)
            startActivity(intent)
        }
    }

    //This function get the posts on the database and show them on the feed
    fun showPosts() {

        val myRef = database.getReference("posts")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                val posts : ArrayList<Post> = ArrayList<Post>()
                for(value in dataSnapshot.children ) {
                    var post : Post = Post(value.child("userid").value.toString(), value.child("postid").value.toString(), "date def", value.child("content").value.toString(),null,null)
                    posts.add(post)
                    //showUserName(post.userid)
                }
                posts.reverse()
                recyclerViewFeed.adapter = PostAdapter(posts,  { postItem : Post -> postItemClicked(postItem) }, { postItem : Post -> postClicked(postItem) } )
                Log.d("post", posts.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("post", "Failed to read value.", error.toException())
            }
        })

        getPosts()
    }

    private fun getPosts() {
        val dbPosts = database.getReference("users")
        dbPosts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val posts : ArrayList<User> = ArrayList()
                Log.d("GETPOST3", "OK")
                for (productSnapshot in dataSnapshot.children) {
                    val post = productSnapshot.getValue(User::class.java)!!
                    /*post?.let {
                        posts.add(it)
                        Log.d("CONTENU : ", it.content)
                    }*/
                    posts.add(post)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("ERROR : ", "CANCELLED ERROR")
                throw databaseError.toException()
            }
        })
        //return postsList
    }

    //allows to redirect on the user activity
    private fun postItemClicked(postItem : Post) {
        val intent = Intent(this, UserActivity::class.java)

        //intent.putExtra("user", postItem.userid)
        //val author = postItem.getUser()
        //startActivity(intent)
        //Toast.makeText(this, "Clicked: ${author.firstname} ${author.email} ", Toast.LENGTH_LONG).show()

        var id : String = postItem.userid
        intent.putExtra("user", id)
        startActivity(intent)
        Toast.makeText(this, "Clicked: ${postItem.userid}", Toast.LENGTH_LONG).show()
    }

    //allow to redirect on the post activity
    private fun postClicked(postItem : Post) {
        val intent = Intent(this, PostActivity::class.java)
        var id : String = postItem.postid
        intent.putExtra("post", id)
        //intent.putExtra("post", post)

        val author = postItem.getUser()
        startActivity(intent)
       // Toast.makeText(this, "Clicked: ${author.firstname} ${postItem.content} ", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "Clicked: ${postItem.postid}", Toast.LENGTH_LONG).show()
    }
}
