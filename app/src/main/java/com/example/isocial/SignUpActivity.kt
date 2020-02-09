package com.example.isocial

import com.google.firebase.auth.FirebaseUser
import android.widget.Toast
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.content.Intent
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.password
import java.util.*

class SignUpActivity : AppCompatActivity() {

    //private var listener: OnFragmentInteractionListener? = null
    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()
    lateinit var currUser: User

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
                val user = auth.currentUser
                registerNewUser(user,
                    firstnameeditText.text.toString(),
                    lastnameeditText.text.toString(),
                    birthdayeditText.text.toString())
                updateUI(user)
            } else {
                Toast.makeText(baseContext, getString(R.string.failed_authentication),Toast.LENGTH_SHORT).show()
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

    private fun registerNewUser(user: FirebaseUser?, fname:String, lname:String, birthdate:String) {
        if (user?.uid != null) {
            currUser = User(user.uid, user.email, fname, lname, birthdate, null, Date().toString())
            val root = database.getReference("users")
            root.child(currUser.userid).setValue(currUser)

        } else
            Toast.makeText(this, getString(R.string.signup_fail), Toast.LENGTH_LONG).show()
    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, getString(R.string.signed_in) + user.uid, Toast.LENGTH_LONG).show()
            startActivity(Intent(this, FeedActivity::class.java))
        } else {
            Toast.makeText(this, getString(R.string.existing_acc), Toast.LENGTH_LONG).show()
        }
    }
}
