package com.mobile.phonelogs.ui.user.calllogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.mobile.phonelogs.base.fragment.BaseFragment
import com.mobile.phonelogs.base.viewmodels.ContactsViewModel
import com.mobile.phonelogs.data.Constants
import com.mobile.phonelogs.data.Constants.CALLLOGS
import com.mobile.phonelogs.data.ContactSharedPreference
import com.mobile.phonelogs.databinding.FragmentCallLogsBinding
import com.mobile.phonelogs.ui.adapter.CallLogsListAdapter

class MissCallLogsFragment : BaseFragment() {

    private lateinit var binding: FragmentCallLogsBinding
    private lateinit var viewModel: ContactsViewModel
    lateinit var adapter: CallLogsListAdapter

    override fun initialize() {
        binding.apply {
            adapter = CallLogsListAdapter(requireActivity(), arrayListOf())
            contactLogsRecycler.layoutManager = LinearLayoutManager(requireActivity())
            contactLogsRecycler.adapter = adapter
            swipeContainer.setOnRefreshListener {
                viewModel.fetchCallLogs()
            }
            viewModel.callLogsList.observe(viewLifecycleOwner) { logs ->
                swipeContainer.isRefreshing = false
                if (logs.isNotEmpty()) {
                    binding.apply {
                        adapter.clear()
                        adapter.addAll(logs.filter { it ->
                            it.viewType == 2
                        })
                    }
                }
            }
        }
    }

    override fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentCallLogsBinding.inflate(inflater, container, false)
    }

    override fun initializeViewModel() {
        viewModel = ViewModelProvider(requireActivity())[ContactsViewModel::class.java]
    }

    override fun fetchLayout(): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        ContactSharedPreference.updateLastSeenScreen(requireActivity(), CALLLOGS)
    }
}