package com.mobile.phonelogs.domain.implementations

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import android.provider.ContactsContract.PhoneLookup
import android.util.LongSparseArray
import com.mobile.phonelogs.base.repositories.ContactsRepository
import com.mobile.phonelogs.data.mappers.ContactDataMapper
import com.mobile.phonelogs.domain.entities.Contact
import com.mobile.phonelogs.domain.entities.Message
import com.mobile.phonelogs.domain.entities.PhoneCallLog
import com.mobile.phonelogs.domain.entities.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ContactRepositoryImpl(private var resolver: ContentResolver) : ContactsRepository() {

    private val projections: Array<String> = arrayOf(
        ContactsContract.Data.CONTACT_ID,
        ContactsContract.Data.DISPLAY_NAME_PRIMARY,
        ContactsContract.Data.PHOTO_URI,
        ContactsContract.Data.MIMETYPE,
        ContactsContract.Data.DATA1
    )

    var logsProjection = arrayOf(
        CallLog.Calls.CACHED_NAME,
        CallLog.Calls.NUMBER,
        CallLog.Calls.TYPE,
        CallLog.Calls.DATE
    )

    var messageProjection = arrayOf("address", "body", "date")

    private fun createCursorForContacts(resolver: ContentResolver): Cursor? {
        return resolver.query(
            ContactsContract.Data.CONTENT_URI,
            projections,
            null,
            null,
            "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + ") ASC"
        )
    }

    private fun createCursorForCallLogs(resolver: ContentResolver): Cursor? {
        return resolver.query(
            CallLog.Calls.CONTENT_URI,
            logsProjection,
            null,
            null,
            CallLog.Calls.DATE + " DESC"
        )
    }

    private fun createCursorForMessages(resolver: ContentResolver): Cursor? {
        return resolver.query(
            Uri.parse("content://sms/"),
            messageProjection,
            null,
            null,
            "date DESC"
        )
    }

    override fun fetchContactsList(): Flow<ResultData<ArrayList<Contact>>> {
        return flow {
            val contacts: LongSparseArray<Contact> = LongSparseArray()
            val contactsList = ArrayList<Contact>()
            createCursorForContacts(resolver)?.apply {
                this.moveToFirst()
                val idxId: Int = getColumnIndex(ContactsContract.Data.CONTACT_ID)
                val idxDisplayNamePrimary: Int = getColumnIndex(ContactsContract.Data.DISPLAY_NAME_PRIMARY)
                val idxPhoto: Int = getColumnIndex(ContactsContract.Data.PHOTO_URI)
                val idxMimeType: Int = getColumnIndex(ContactsContract.Data.MIMETYPE)
                val idxData1: Int = getColumnIndex(ContactsContract.Data.DATA1)

                if (isAfterLast) {
                    emit(ResultData.success(arrayListOf()))
                } else {
                    while (!isAfterLast) {
                        val id: Long = getLong(idxId)
                        var contact: Contact? = contacts.get(id, null)
                        if (contact == null) {
                            contact = Contact()
                            contact.id = id
                            ContactDataMapper.mapDisplayName(this, contact, idxDisplayNamePrimary)
                            ContactDataMapper.mapPhoto(this, contact, idxPhoto)
                            contacts.put(id, contact)
                            contactsList.add(contact)
                        }
                        val mimetype: String = getString(idxMimeType)
                        when (mimetype) {
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                                ContactDataMapper.mapPhoneNumber(this, contact, idxData1)
                            }
                        }
                        moveToNext()
                    }
                }
                emit(ResultData.success(contactsList))
                close()
            }
        }
    }

    override fun fetchCallLogs(): Flow<ResultData<ArrayList<PhoneCallLog>>> {
        return flow {
            val sb = StringBuffer()
            val managedCursor: Cursor? = createCursorForCallLogs(resolver)
            val list: ArrayList<PhoneCallLog> = arrayListOf()
            managedCursor?.apply {

                val name: Int = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
                val number: Int = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
                val type: Int = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
                val date: Int = managedCursor.getColumnIndex(CallLog.Calls.DATE)

                if (managedCursor.isAfterLast) {
                    emit(ResultData.success(arrayListOf<PhoneCallLog>().apply { arrayListOf<PhoneCallLog>() }))
                } else {
                    while (managedCursor.moveToNext()) {
                        val name: String = managedCursor.getString(name) ?: ""
                        val phNumber: String = managedCursor.getString(number)
                        val callType: String = managedCursor.getString(type)
                        val callTimeStamp: Long = java.lang.Long.valueOf(managedCursor.getString(date))
                        var viewType = 0
                        var dir: String? = null
                        when (callType.toInt()) {
                            CallLog.Calls.INCOMING_TYPE -> {
                                viewType = 0
                                dir = "INCOMING"
                            }
                            CallLog.Calls.OUTGOING_TYPE -> {
                                viewType = 1
                                dir = "OUTGOING"
                            }
                            CallLog.Calls.MISSED_TYPE -> {
                                viewType = 2
                                dir = "MISSED"
                            }
                        }
                        list.add(PhoneCallLog(name, viewType, 0, dir, phNumber, callTimeStamp, dir))
                        emit(ResultData.success(arrayListOf<PhoneCallLog>().apply { addAll(list) }))
                    }
                }
                close()
            }
        }
    }

    override fun fetchSmsInboxConversions(): Flow<ResultData<ArrayList<Message>>> {
        return flow {
            val lstSms: ArrayList<Message> = ArrayList()
            var objSms = Message()
            val cursor = createCursorForMessages(resolver)
            val totalSMS = cursor!!.count
            if (cursor.isAfterLast) {
                emit(ResultData.success(arrayListOf()))
            } else {
                if (cursor.moveToFirst()) {
                    for (i in 0 until totalSMS) {
                        objSms = Message()
                        objSms.address = (cursor.getString(cursor.getColumnIndexOrThrow("address")))
                        if (objSms.address?.matches(Regex("\\+?1?\\s*\\(?-*\\.*(\\d{3})\\)?\\.*-*\\s*(\\d{3})\\.*-*\\s*(\\d{4})\$")) == true) {
                            objSms.readState = getContactName(objSms.address)
                        } else {
                            objSms.readState = objSms.address
                        }
                        objSms.messsage = (cursor.getString(cursor.getColumnIndexOrThrow("body")))
                        objSms.time = (cursor.getString(cursor.getColumnIndexOrThrow("date")))
                        lstSms.add(objSms)
                        cursor.moveToNext()
                        emit(ResultData.success(lstSms))
                    }
                }
            }
            cursor.close()
        }
    }

    fun getContactName(phoneNumber: String?): String? {
        val cr: ContentResolver = resolver
        val uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber))
        val cursor = cr.query(uri, arrayOf(PhoneLookup.DISPLAY_NAME), null, null, null) ?: return null
        var contactName: String? = null
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME) ?: 0)
        }
        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }
        return contactName
    }

}