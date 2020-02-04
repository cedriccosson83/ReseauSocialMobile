package com.example.isocial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user.*


class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val intent = intent
        if (intent != null) {
            val user: User = intent.getParcelableExtra("user")
            if (user != null) { // tu peux manipuler user !
                textViewName.text = "${user.firstName} ${user.lastName}"
            }
        }
    }
}
