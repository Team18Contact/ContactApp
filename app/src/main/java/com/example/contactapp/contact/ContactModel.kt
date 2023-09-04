package com.example.contactapp.contact

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactModel(
    val profile: Int,
    val name: String,
    val locale: String,
    val phoneNum: String,
    val email: String,
    val ability: String,
) : Parcelable

