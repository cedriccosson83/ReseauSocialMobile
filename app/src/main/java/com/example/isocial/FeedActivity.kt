package com.example.isocial

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_feed.*


class FeedActivity : AppCompatActivity() {

    var user : User = User("Ricciardi", "Florent", 22)
    var user2 : User = User("Cosson", "Cédric", 22)
    var user3 : User = User("Gaire", "Lucie", 20)

    var post : Post = Post(user, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")
    var post2 : Post = Post(user2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")
    var post3 : Post = Post(user3, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")

    val tabPosts = arrayListOf(post,post2,post3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        recyclerViewFeed.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerViewFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerViewFeed.adapter = PostAdapter(tabPosts,  { postItem : Post -> partItemClicked(postItem) })


    }

    private fun partItemClicked(postItem : Post) {
        val intent = Intent(this, UserActivity::class.java)
        intent.putExtra("user", postItem.user)
        startActivity(intent)
        Toast.makeText(this, "Clicked: ${postItem.user?.firstName} ${postItem.user?.age} ", Toast.LENGTH_LONG).show()
    }
}
