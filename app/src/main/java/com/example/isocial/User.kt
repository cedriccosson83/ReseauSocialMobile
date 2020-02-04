package com.example.isocial

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*
import kotlin.collections.ArrayList

@IgnoreExtraProperties
data class User (
    var userid: String = "",
    var email: String? = "",
    var firstname: String? = "",
    var lastname: String? = "",
    var birthdate: String? = "",
    var lastConn: String? = null,
    var dateCreate: String? = Date().toString(),
    var posts: ArrayList<Post> = ArrayList()
)