package com.example.isocial


import android.app.Activity
import android.content.Intent
import com.squareup.picasso.Picasso
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.changeProfilImage
import kotlinx.android.synthetic.main.activity_profile.dateProfile
import kotlinx.android.synthetic.main.activity_profile.nameProfile
import java.util.HashMap
import android.provider.MediaStore.Images.Media.getBitmap


class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()
    private lateinit var filePath: Uri
    private var firebaseStore: FirebaseStorage? = null
    private lateinit var storageReference: StorageReference
    private val codePicture = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        val intent = intent

        if (intent != null) {
            val userId: String? = intent.getStringExtra("userId")
            if (userId != null) {
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


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK ){

            if (requestCode == codePicture && resultCode == Activity.RESULT_OK) {

                if (data?.data != null) { // from gallery
                    filePath = data.data ?: Uri.EMPTY
                    val bitmap = getBitmap(contentResolver, filePath)
                    changeProfilImage.setImageBitmap(bitmap)
                    savePictureFireStore()
                }
            }
        }
    }

    private fun savePictureFireStore() {
        val userid = auth.currentUser?.uid ?: ""
        if(userid != ""){
            val riversRef = storageReference.child("users/$userid/profile")

            riversRef.putFile(filePath)
                .addOnSuccessListener { taskSnapshot ->

                    val downloadUrl = taskSnapshot.metadata?.reference?.downloadUrl.toString()
                    if(downloadUrl.isNotEmpty()) {

                        val db = FirebaseFirestore.getInstance()

                        val data = HashMap<String, Any>()
                        data["imageUrl"] = downloadUrl

                        db.collection("posts")
                            .add(data)
                            .addOnSuccessListener { documentReference ->
                                Toast.makeText(this, "Image enregistrée", Toast.LENGTH_LONG).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Erreur lors de la sauvegarde", Toast.LENGTH_LONG).show()
                            }
                        Toast.makeText(this, downloadUrl, Toast.LENGTH_LONG).show()
                    }

                }
                .addOnFailureListener {
                    Toast.makeText(this,"Échec de l'upload de l'image", Toast.LENGTH_LONG).show()
                }
        }
        else{
            Toast.makeText(this, "Veuillez choisir une image", Toast.LENGTH_SHORT).show()
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
            /*
        val storageReference = FirebaseStorage.getInstance().getReference("users").child(userId).child("profile")
        val imageView = findViewById<ImageView>(R.id.changeProfilImage)
        val path = storageReference.path
        Picasso.get().load(path).into(imageView)*/

        //Glide.with(this).load(storageReference).into(imageView)*/
    }

    fun changepicture(){
        val imagefromgalleryIntent = Intent(Intent.ACTION_PICK)
        imagefromgalleryIntent.setType("image/png")
        val chooseIntent = Intent.createChooser(imagefromgalleryIntent, "Gallery")
        startActivityForResult(chooseIntent, codePicture)


    }
}
