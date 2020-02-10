package com.example.isocial


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.changeProfilImage
import kotlinx.android.synthetic.main.activity_profile.dateProfile
import kotlinx.android.synthetic.main.activity_profile.nameProfile



class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        val intent = intent

        if (intent != null) {
            val userId: String? = intent.getStringExtra("userId")
            if (userId != null) { // tu peux manipuler user !
                showUser(userId)

                val currentUserID = auth.currentUser?.uid
                if (currentUserID == userId) {
                    changeProfilImage.setOnClickListener {
                        changepicture()
                    }
                    nameProfile.setOnClickListener {

                    }
                }
                 else {
                    imageViewUserEdit.visibility = View.INVISIBLE
                }

            }
        }


        accessNewBTN.setOnClickListener{
            startActivity(Intent(this, WritePostActivity::class.java))
        }

        accessFeedBTN.setOnClickListener{
            startActivity(Intent(this, FeedActivity::class.java))
        }

        logoutbutton.setOnClickListener {
            //editor.clear().commit()
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK ){
            if(data?.data == null){
                val bitmap = data?.extras?.get("data") as? Bitmap
                bitmap?.let{
                    changeProfilImage.setImageBitmap(it)
                }
            }else{
                changeProfilImage.setImageURI(data?.data)

            }

        }
    }


    fun showUser(userId: String) {

        val myRef = database.getReference("users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user: User
                for (value in dataSnapshot.children) {
                    user = User(
                        value.child("userid").value.toString(),
                        value.child("email").value.toString(),
                        value.child("firstname").value.toString(),
                        value.child("lastname").value.toString(),
                        value.child("birthdate").value.toString(),
                        value.child("lastConn").value.toString(),
                        null,
                        null
                    )
                    if (user.userid == userId) {
                    nameProfile.text = "${user.firstname} ${user.lastname}"
                    dateProfile.text = "${user.birthdate.toString()}"
                    mailProfile.text = "${user.email}"
                    connexionProfile.text = "${user.lastConn.toString()}"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.w("post", "Failed to read value.", error.toException())
            }
        })


    }

    fun changepicture(){
        val imagefromgalleryIntent = Intent(Intent.ACTION_PICK)
        imagefromgalleryIntent.setType("image/png")

        val imagefromcameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val chooseIntent = Intent.createChooser(imagefromgalleryIntent, "Gallery")
        chooseIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            arrayOf(imagefromcameraIntent)
        )
        startActivityForResult(chooseIntent, 11)
    }
}
