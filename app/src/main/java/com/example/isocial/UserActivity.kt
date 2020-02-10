package com.example.isocial

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user.*


class UserActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        auth = FirebaseAuth.getInstance()
        val intent = intent
        if (intent != null) {
            val userid :String = intent.getStringExtra("user")
            if (userid != null) { // tu peux manipuler user !
                showUser(userid)
                val currentUserID = auth.currentUser?.uid
                if(currentUserID == userid){
                    Log.d("user", " profil de l'utilisateur")
                }else{
                    imageViewUserEdit.visibility = View.INVISIBLE
                }
            }
        }

    }

    fun showUser(userId : String) {

        val myRef = database.getReference("users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                var user: User
                for(value in dataSnapshot.children ) {
                    user = User(value.child("userid").value.toString(), value.child("email").value.toString(), value.child("firstname").value.toString(), value.child("lastname").value.toString(),value.child("birthdate").value.toString(),null,null,null)
                    if(user.userid == userId){
                        textViewNamePost.text = "${user.firstname} ${user.lastname}"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.w("post", "Failed to read value.", error.toException())
            }
        })


    }
}
