package com.mobile.phonelogs.domain.implementations

import android.util.LongSparseArray
import com.mobile.phonelogs.base.repositories.ContactsRepository
import com.mobile.phonelogs.base.usecases.ContactsUseCase
import com.mobile.phonelogs.domain.entities.PhoneCallLog
import com.mobile.phonelogs.domain.entities.Contact
import com.mobile.phonelogs.domain.entities.Message
import com.mobile.phonelogs.domain.entities.ResultData
import kotlinx.coroutines.flow.Flow

// Contacts UseCases Implementation Version 1
class ContactsUseCaseImpl(private var contactsRepository: ContactsRepository) : ContactsUseCase() {

    override fun fetchContactsList(): Flow<ResultData<ArrayList<Contact>>> = contactsRepository.fetchContactsList()

    override fun fetchCallLogs(): Flow<ResultData<ArrayList<PhoneCallLog>>> = contactsRepository.fetchCallLogs()

    override fun fetchMessages(): Flow<ResultData<ArrayList<Message>>> = contactsRepository.fetchSmsInboxConversions()
}