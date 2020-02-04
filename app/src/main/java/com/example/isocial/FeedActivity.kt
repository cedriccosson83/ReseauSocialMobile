package com.example.isocial

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_feed.*


class FeedActivity : AppCompatActivity() {

    var post : Post = Post("Florent Ricciardi", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")
    var post2 : Post = Post("Cédric Cosson", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")
    var post3 : Post = Post("Lucie Gaire", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut dictum volutpat interdum. Donec sed diam nec risus tincidunt aliquet. Curabitur sed.")

    val tabPosts = arrayListOf(post,post2,post3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        recyclerViewFeed.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerViewFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerViewFeed.adapter = PostAdapter(tabPosts,  { postItem : Post -> partItemClicked(postItem) })


    }

    private fun partItemClicked(postItem : Post) {
        Toast.makeText(this, "Clicked: ${postItem.nameUser}", Toast.LENGTH_LONG).show()
    }
}
