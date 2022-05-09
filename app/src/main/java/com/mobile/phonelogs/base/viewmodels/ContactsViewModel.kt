package com.mobile.phonelogs.base.viewmodels

import android.app.Application
import android.content.Context
import android.util.LongSparseArray
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobile.phonelogs.domain.entities.PhoneCallLog
import com.mobile.phonelogs.domain.entities.Contact
import com.mobile.phonelogs.domain.entities.Message
import com.mobile.phonelogs.domain.implementations.ContactRepositoryImpl
import com.mobile.phonelogs.domain.implementations.ContactsUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context get() = getApplication<Application>().applicationContext

    val contactsList: MutableLiveData<ArrayList<Contact>> by lazy {
        MutableLiveData<ArrayList<Contact>>()
    }


    val callLogsList = MutableLiveData<ArrayList<PhoneCallLog>>().apply {
        MutableLiveData<ArrayList<PhoneCallLog>>()
    }

    val messages = MutableLiveData<ArrayList<Message>>().apply {
    }


    fun fetchUserContacts() {
        viewModelScope.launch(Dispatchers.Default) {
            ContactsUseCaseImpl(ContactRepositoryImpl(context.contentResolver)).fetchContactsList().collect {
                it.data?.apply {
                    contactsList.postValue(this)
                }
            }
        }
    }

    fun fetchMessages() {
        viewModelScope.launch(Dispatchers.Default) {
            ContactsUseCaseImpl(ContactRepositoryImpl(context.contentResolver)).fetchMessages().collect {
                it.data?.apply {
                    messages.postValue(this)
                }
            }
        }
    }

    fun fetchCallLogs() {
        viewModelScope.launch(Dispatchers.Default) {
            ContactsUseCaseImpl(ContactRepositoryImpl(context.contentResolver)).fetchCallLogs().collect {
                it.data?.apply {
                    callLogsList.postValue(this)
                }
            }
        }
    }
}
