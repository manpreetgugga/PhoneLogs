package com.mobile.phonelogs.ui.user.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.phonelogs.base.fragment.BaseFragment
import com.mobile.phonelogs.base.viewmodels.ContactsViewModel
import com.mobile.phonelogs.data.Constants.CONTACTS
import com.mobile.phonelogs.data.ContactSharedPreference
import com.mobile.phonelogs.databinding.FragmentContactListBinding
import com.mobile.phonelogs.ui.adapter.ContactListAdapter

class ContactsListFragment : BaseFragment() {

    private lateinit var binding: FragmentContactListBinding
    private lateinit var viewModel: ContactsViewModel
    lateinit var adapter: ContactListAdapter

    override fun initialize() {
        binding.apply {
            adapter = ContactListAdapter(this@ContactsListFragment, arrayListOf())
            contactsRecycler.layoutManager = LinearLayoutManager(requireActivity())
            contactsRecycler.adapter = adapter
            swipeContainer.setOnRefreshListener {
                viewModel.fetchUserContacts()
            }
            viewModel.contactsList.observe(viewLifecycleOwner) { contacts ->
                swipeContainer.isRefreshing = false
                if (contacts.size != 0) {
                    adapter.clear()
                    adapter.addAll(contacts)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ContactSharedPreference.updateLastSeenScreen(requireActivity(), CONTACTS)
        viewModel.fetchUserContacts()
    }

    override fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
    }

    override fun initializeViewModel() {
        viewModel = ViewModelProvider(requireActivity())[ContactsViewModel::class.java]
    }

    override fun fetchLayout(): View {
        return binding.root
    }
}