package com.mobile.phonelogs.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PhoneCallLog(
    var name: String = "",
    var viewType: Int = 0,
    var callcode: Int? = 0,
    var callType: String? = "",
    var phNum: String? = "",
    var callDate: Long = 0L,
    var callTypeCode: String? = "",
    var strcallDate: String? = "",
    var callDuration: String? = "",
    var currElement: String? = "",
    var ring: Boolean? = false,
    var callReceived: Boolean? = false
) : Parcelable