package com.example.isocial

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_feed.*


class FeedActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()

    /*var user : User = User("ae1z212", "florentricciardi@gmail.com","Ricciardi","Florent", "06/03/1997","04/02/2020","01/02/2020")
    var user2 : User = User("dsq121", "cedriccosson@gmail.com", "Cosson","Cédric", "13/11/1997", "04/02/2020","02/01/2020")
    var user3 : User = User("dsqd455", "luciegaire@gmail.com", "Gaire", "Lucie","18/05/1999", "04/02/2020","18/01/2020")

    var post : Post = Post(user, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")
    var post2 : Post = Post(user2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")
    var post3 : Post = Post(user3, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")
*/
    //val tabPosts = arrayListOf(post,post2,post3)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        auth = FirebaseAuth.getInstance()
        val dbPosts = database.getReference("posts")

        //recyclerViewFeed.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        //recyclerViewFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        //recyclerViewFeed.adapter = PostAdapter(tabPosts,  { postItem : Post -> postItemClicked(postItem) }, { postItem : Post -> postClicked(postItem) } )


    }
/*
    private fun postItemClicked(postItem : Post) {
        val intent = Intent(this, UserActivity::class.java)
        intent.putExtra("user", postItem.user)
        startActivity(intent)
        Toast.makeText(this, "Clicked: ${postItem.user?.firstName} ${postItem.user?.email} ", Toast.LENGTH_LONG).show()
    }

    private fun postClicked(postItem : Post) {
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra("user", postItem.user)
        //intent.putExtra("post", post)
        startActivity(intent)
        Toast.makeText(this, "Clicked: ${postItem.user?.firstName} ${postItem.textContent} ", Toast.LENGTH_LONG).show()
    }*/
}
