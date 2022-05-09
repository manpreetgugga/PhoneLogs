package com.mobile.phonelogs.domain.entities

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var id: Long? = 0L,
    var name: String? = "",
    var mobileNumber: ArrayList<String> = arrayListOf(),
    var photoURI: Uri? = null
) : Parcelable