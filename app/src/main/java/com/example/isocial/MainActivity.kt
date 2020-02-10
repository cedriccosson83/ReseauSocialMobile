package com.example.isocial

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    private val PICK_IMAGE_REQUEST = 71
    private lateinit var filePath: Uri
    private var firebaseStore: FirebaseStorage? = null
    private lateinit var storageReference: StorageReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        btn_choose_image.setOnClickListener { launchGallery() }
        btn_upload_image.setOnClickListener { uploadImage() }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data ?: Uri.EMPTY
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                image_preview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun addUploadRecordToDb(uri: String){
        val db = FirebaseFirestore.getInstance()

        val data = HashMap<String, Any>()
        data["imageUrl"] = uri

        db.collection("posts")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Image enregistrée", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erreur lors de la sauvegarde", Toast.LENGTH_LONG).show()
            }
    }

    private fun uploadImage(){
        val userid = auth.currentUser?.uid ?: ""
        if(userid != ""){
            val riversRef = storageReference.child("users/$userid/profile")

            riversRef.putFile(filePath)
                .addOnSuccessListener { taskSnapshot ->

                    val downloadUrl = taskSnapshot.metadata?.reference?.downloadUrl.toString()
                    if(downloadUrl.isNotEmpty()) {
                        addUploadRecordToDb(downloadUrl)
                        Toast.makeText(this, downloadUrl, Toast.LENGTH_LONG).show()
                    }

                }
                .addOnFailureListener {
                    Toast.makeText(this,"Échec de l'upload de l'image", Toast.LENGTH_LONG).show()
                }
        }
        else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }
}
