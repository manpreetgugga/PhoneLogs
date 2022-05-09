package com.mobile.phonelogs.base.repositories

import com.mobile.phonelogs.domain.entities.PhoneCallLog
import com.mobile.phonelogs.domain.entities.Contact
import com.mobile.phonelogs.domain.entities.Message
import com.mobile.phonelogs.domain.entities.ResultData
import kotlinx.coroutines.flow.Flow

abstract class ContactsRepository {

    abstract fun fetchContactsList() : Flow<ResultData<ArrayList<Contact>>>

    abstract fun fetchCallLogs(): Flow<ResultData<ArrayList<PhoneCallLog>>>

    abstract fun fetchSmsInboxConversions(): Flow<ResultData<ArrayList<Message>>>

}