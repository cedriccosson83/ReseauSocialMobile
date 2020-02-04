package com.example.isocial

import com.google.firebase.auth.FirebaseUser
import android.widget.Toast
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.content.Intent
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.emaileditText
import kotlinx.android.synthetic.main.activity_sign_up.passwordeditText

class SignUpActivity : AppCompatActivity() {

    //private var listener: OnFragmentInteractionListener? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(saved: Bundle?) {
        super.onCreate(saved)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        subscribebutton.setOnClickListener {
            if (emaileditText.text.toString().isNotEmpty()) {
                signup()
            }
            else {
                Toast.makeText(this, getString(R.string.mail_mdp_incorr), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signup() {
        auth.createUserWithEmailAndPassword(
            emaileditText.text.toString(),
            passwordeditText.text.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign up success, update UI with the signed-in user's information
                val user = auth.currentUser
                updateUI(user)
            } else {
                // If sign up fails, display a message to the user.
                Toast.makeText(baseContext, getString(R.string.failed_authentication),
                    Toast.LENGTH_SHORT
                ).show()
                updateUI(null)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        accountexistbutton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    fun updateUI(account: FirebaseUser?) {
        if (account != null ) {
            Toast.makeText(this, getString(R.string.signed_in) + account?.uid, Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this, getString(R.string.existing_account), Toast.LENGTH_LONG).show()
        }
    }
}
