package com.example.isocial

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.text.SimpleDateFormat
import java.util.*

class SignInActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(saved: Bundle?) {
        super.onCreate(saved)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        signinbutton.setOnClickListener{
            if (emaileditText.text.toString().isNotEmpty()) {
                signin()
            }
            else {
                Toast.makeText(this, getString(R.string.mail_mdp_incorr), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signin() {
        auth.signInWithEmailAndPassword(emaileditText.text.toString(), passwordeditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }
    //Change UI according to user data.
    private fun updateUI(user: FirebaseUser?) {
        if (user != null ) {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val date = sdf.format(Date())
            database.getReference("users")
                .child(user.uid)
                .child("lastConn")
                .setValue(date)
            Toast.makeText(this, getString(R.string.signed_in), Toast.LENGTH_LONG).show()
            startActivity(Intent(this, FeedActivity::class.java))
        } else {
            Toast.makeText(this, getString(R.string.mail_mdp_incorr), Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        noaccountbutton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }


}