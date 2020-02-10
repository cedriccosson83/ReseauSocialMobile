package com.example.isocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_feed.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        accessNewBTN.setOnClickListener{
            startActivity(Intent(this, WritePostActivity::class.java))
        }

        accessFeedBTN.setOnClickListener{
            startActivity(Intent(this, FeedActivity::class.java))
        }

    }

    /*
    *
    * val user = FirebaseAuth.getInstance().currentUser
       user?.let {
    // Name, email address, and profile photo Url
    val name = user.displayName
    val email = user.email
    val photoUrl = user.photoUrl

    // Check if user's email is verified
    val emailVerified = user.isEmailVerified

    // The user's ID, unique to the Firebase project. Do NOT use this value to
    // authenticate with your backend server, if you have one. Use
    // FirebaseUser.getToken() instead.
    val uid = user.uid
}
    *
    * */
}
