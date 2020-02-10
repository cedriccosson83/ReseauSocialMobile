package com.example.isocial

import android.widget.TextView
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