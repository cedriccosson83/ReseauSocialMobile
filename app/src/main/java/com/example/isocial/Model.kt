package com.example.isocial

import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import java.text.SimpleDateFormat
import java.util.*

fun showDate(date : String?, textview: TextView){

    var dateSplit = date?.split(" ")
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val currentDate: String = sdf.format(Date())
    if(currentDate == dateSplit?.get(1)){
        textview.text = "${dateSplit?.get(0)}"
    }else{
        textview.text = "${dateSplit?.get(1)}"
    }
}

fun redirectToUserActivity(context: Context, userID : String){
    val intent = Intent(context, UserActivity::class.java)
    var id : String = userID
    intent.putExtra("user", id)
    context.startActivity(intent)
    Toast.makeText(context, "Clicked: ${id}", Toast.LENGTH_LONG).show()
}