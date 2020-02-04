package com.example.isocial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        private lateinit var auth: FirebaseAuth
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }
}
