package com.mobile.phonelogs.ui.user.calllogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.mobile.phonelogs.base.viewmodels.ContactsViewModel
import com.mobile.phonelogs.databinding.FragmentCallLoggerBinding

class CallLoggerFragment : Fragment() {

    lateinit var binding: FragmentCallLoggerBinding

    private val NUM_PAGES = 3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCallLoggerBinding.inflate(inflater)
        ViewModelProvider(requireActivity())[ContactsViewModel::class.java].fetchCallLogs()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getCount(): Int {
                return NUM_PAGES
            }

            override fun getPageTitle(position: Int): CharSequence {
                return when (position) {
                    0 -> "Incoming"
                    1 -> "Outgoing"
                    2 -> "Missed"
                    else -> "Incoming"
                }
            }

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> IncomingCallLogsFragment()
                    1 -> OutGoingCallLogsFragment()
                    2 -> MissCallLogsFragment()
                    else -> IncomingCallLogsFragment()
                }
            }
        }
        binding.pager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.pager)
    }


}