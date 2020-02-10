package com.example.isocial

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write.accessFeedBTN
import kotlinx.android.synthetic.main.activity_write.accessProfileBTN
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class WritePostActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        auth = FirebaseAuth.getInstance()
        postImage.setOnClickListener {
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
        publishBTN.setOnClickListener{
            val userid = auth.currentUser?.uid

            if (userid != null){
                if(publish_field.text.toString() != ""){
                    newPost(userid, publish_field.text.toString())
                    publish_field.setText("")
                    Toast.makeText(this, "Post publié!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, FeedActivity::class.java)
                    startActivity(intent)

                }
                else{
                    Log.d("erreur", "vide")
                    Toast.makeText(this, "Publication vide", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Utilisateur non trouvé", Toast.LENGTH_LONG).show()
            }
        }

        accessFeedBTN.setOnClickListener{
            startActivity(Intent(this, FeedActivity::class.java))
        }

        accessProfileBTN.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            val id = auth.currentUser?.uid
            intent.putExtra("userId", id)
            startActivity(intent)
        }
        //readPosts()
    }

    private fun newPost(userId: String, content: String) {

        val dbPosts = database.getReference("posts")
        val newId = dbPosts.push().key
        if (newId == null) {
            Log.w("ERROR", "Couldn't get push key for posts")
            return
        }

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val currentDateandTime: String = sdf.format(Date())
        Log.d("heure", currentDateandTime)
        val post = Post(userId, newId, currentDateandTime, content, ArrayList(), ArrayList())
        dbPosts.child(newId).setValue(post)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK ){
            if(data?.data == null){
                val bitmap = data?.extras?.get("data") as? Bitmap
                bitmap?.let{
                    postImage.setImageBitmap(it)
                }
            }else{
                postImage.setImageURI(data?.data)

            }

        }
    }


}