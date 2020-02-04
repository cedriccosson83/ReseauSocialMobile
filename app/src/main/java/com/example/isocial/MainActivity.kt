package com.example.isocial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var index = 0
        while(index++ < 10) {
            val myRef = database.getReference("users/"+index+"/nom")
            myRef.setValue("Nom de l'utilisateur " + index)
        }

        val myRef = database.getReference("users/5/nom")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                MainTV.text = value
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("CONSOLE: ", "Failed to read value.", error.toException())
            }
        })

        myRef.setValue("Cédric COSSON")

    }

}
