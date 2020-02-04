package com.example.isocial

import android.os.Parcel
import android.os.Parcelable

class User(var lastName: String?, var firstName: String?, var age: Int?) : Parcelable {

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(lastName)
        p0?.writeString(firstName)
       p0?.writeInt(age ?: 0)
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(
                lastName = parcel.readString(),
                firstName = parcel.readString(),
                age = parcel.readInt()
            )
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}