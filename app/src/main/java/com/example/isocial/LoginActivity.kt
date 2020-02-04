package com.example.isocial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        signinbutton.setOnClickListener {
            signin()
        }

        subscribebutton.setOnClickListener {
            signup()
        }
    }
    fun signup() {
        auth.createUserWithEmailAndPassword(emaileditText.text.toString(),
            passwordeditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    registerNewUser(user, "cedric", "cosson", "13/11/1997")
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, getString(R.string.auth_fail),Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun registerNewUser(user: FirebaseUser?, fname:String, lname:String, birthdate:String) {
        if (user?.uid != null) {
            currUser = User(user.uid, user.email, fname, lname, birthdate, null, Date().toString())
            val root = database.getReference("users")
            root.child(currUser.userid).setValue(currUser)

        } else
            Toast.makeText(this, getString(R.string.signup_fail), Toast.LENGTH_LONG).show()
    }

    fun signin() {
        auth.signInWithEmailAndPassword(emaileditText.text.toString(), passwordeditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                    updateUI(auth.currentUser)
                else
                    Toast.makeText(this, getString(R.string.log_fail), Toast.LENGTH_LONG).show()
        }
    }
}
