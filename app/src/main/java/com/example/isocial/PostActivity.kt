package com.example.isocial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_post.textViewName
import kotlinx.android.synthetic.main.activity_user.*

class PostActivity : AppCompatActivity() {
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        val intent = intent

        if (intent != null) {
            val user: User = intent.getParcelableExtra("user")
            if (user != null) { // tu peux manipuler user !
                textViewName.text= "${user?.firstName} ${user?.lastName}"
            }

        }
    }

    fun showPost(post : Post){
        textViewName.text= "${post.user?.firstName} ${post.user?.lastName}"
        textViewContent.text = "${post.textContent}"
    }*/
}
