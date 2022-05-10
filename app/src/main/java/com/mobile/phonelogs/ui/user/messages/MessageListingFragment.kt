package com.mobile.phonelogs.ui.user.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.phonelogs.base.fragment.BaseFragment
import com.mobile.phonelogs.base.viewmodels.ContactsViewModel
import com.mobile.phonelogs.data.Constants.MESSAGES
import com.mobile.phonelogs.data.ContactSharedPreference
import com.mobile.phonelogs.databinding.FragmentMessagesBinding
import com.mobile.phonelogs.ui.adapter.ContactListAdapter
import com.mobile.phonelogs.ui.adapter.MessagesListAdapter

class MessageListingFragment : BaseFragment() {

    private lateinit var binding: FragmentMessagesBinding
    private lateinit var viewModel: ContactsViewModel
    lateinit var adapter: MessagesListAdapter

    override fun initialize() {
        binding.apply {
            viewModel.fetchMessages()
            adapter = MessagesListAdapter(requireContext(), arrayListOf())
            messagesRecycler.layoutManager = LinearLayoutManager(requireActivity())
            messagesRecycler.adapter = adapter
            swipeContainer.isRefreshing = true
            swipeContainer.setOnRefreshListener {
                viewModel.fetchMessages()
            }
            viewModel.messages.observe(viewLifecycleOwner) { messages ->
                swipeContainer.isRefreshing = false
                if (messages.size != 0) {
                    adapter.clear()
                    adapter.addAll(messages)
                    noDataFound.visibility = View.GONE
                    messagesRecycler.visibility = View.VISIBLE
                } else {
                    noDataFound.visibility = View.VISIBLE
                    messagesRecycler.visibility = View.GONE
                }
            }
        }
    }

    override fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentMessagesBinding.inflate(inflater, container, false)
    }

    override fun initializeViewModel() {
        viewModel = ViewModelProvider(requireActivity())[ContactsViewModel::class.java]
    }

    override fun fetchLayout(): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        ContactSharedPreference.updateLastSeenScreen(requireActivity(), MESSAGES)
    }
}