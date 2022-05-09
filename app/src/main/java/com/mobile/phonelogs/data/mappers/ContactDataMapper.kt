package com.mobile.phonelogs.data.mappers

import android.database.Cursor
import android.net.Uri
import com.mobile.phonelogs.domain.entities.Contact

object ContactDataMapper {

    fun mapDisplayName(cursor: Cursor, contact: Contact, columnIndex: Int) {
        val displayName = cursor.getString(columnIndex)
        if (displayName != null && displayName.isNotEmpty()) {
            contact.name = displayName
        }
    }

    fun mapPhoneNumber(cursor: Cursor, contact: Contact, columnIndex: Int) {
        val phoneNumber = cursor.getString(columnIndex)
        contact.mobileNumber.add(phoneNumber)
    }

    fun mapPhoto(cursor: Cursor, contact: Contact, columnIndex: Int) {
        cursor.getString(columnIndex)?.let { uri->
            if (uri.isNotEmpty()) {
                contact.photoURI = Uri.parse(uri)
            }
        }
    }
}
