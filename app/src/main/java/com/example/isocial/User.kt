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
/*import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList

class User(
    var userID: String?,
    var email: String?,
    var lastName: String?,
    var firstName: String?,
    var birthDate: String?,
    var lastConn: String?,
    var dateCreate: String? = Date().toString()

) : Parcelable {

    override fun writeToParcel(p0: Parcel?, p1: Int) {

        p0?.writeString(userID)
        p0?.writeString(email)
        p0?.writeString(lastName)
        p0?.writeString(firstName)
        p0?.writeString(birthDate)
        p0?.writeString(lastConn)
        p0?.writeString(dateCreate)

    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(
                userID = parcel.readString(),
                email = parcel.readString(),
                lastName = parcel.readString(),
                firstName = parcel.readString(),
                birthDate = parcel.readString(),
                lastConn = parcel.readString(),
                dateCreate = parcel.readString()
            )
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
*/
}
