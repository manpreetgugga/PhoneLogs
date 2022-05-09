package com.mobile.phonelogs.base.usecases

import android.util.LongSparseArray
import com.mobile.phonelogs.domain.entities.PhoneCallLog
import com.mobile.phonelogs.domain.entities.Contact
import com.mobile.phonelogs.domain.entities.Message
import com.mobile.phonelogs.domain.entities.ResultData
import kotlinx.coroutines.flow.Flow

abstract class ContactsUseCase {

    abstract fun fetchContactsList() : Flow<ResultData<ArrayList<Contact>>>

    abstract fun fetchCallLogs() : Flow<ResultData<ArrayList<PhoneCallLog>>>

    abstract fun fetchMessages(): Flow<ResultData<ArrayList<Message>>>

}