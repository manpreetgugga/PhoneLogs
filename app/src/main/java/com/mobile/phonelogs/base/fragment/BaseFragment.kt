package com.mobile.phonelogs.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initializeBinding(inflater, container)
        initializeViewModel()
        return fetchLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    fun showDialog(dialog: DialogFragment) {
        val ft = childFragmentManager.beginTransaction()
        val prev = childFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialog.show(ft, "dialog")
    }


    abstract fun initialize()
    abstract fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?)
    abstract fun initializeViewModel()
    abstract fun fetchLayout(): View
}