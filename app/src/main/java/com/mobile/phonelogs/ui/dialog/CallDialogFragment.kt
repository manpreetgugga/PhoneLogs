package com.mobile.phonelogs.ui.dialog

import android.R.string.no
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.mobile.phonelogs.databinding.DialogActionBinding
import com.mobile.phonelogs.domain.entities.Contact


class CallDialogFragment(var contact: Contact) : DialogFragment() {

    lateinit var binding: DialogActionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogActionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.message.text = "Call or Message ${contact.name} ?"
        binding.messageContact.setOnClickListener {
            val intent = Intent(context, contact.javaClass)
            val pi = PendingIntent.getActivity(context, 0, intent, 0)
            val sms: SmsManager = SmsManager.getDefault()
            sms.sendTextMessage(contact.mobileNumber[0], null, "Dummy Message For Now", pi, null)
            Toast.makeText(context, "Dummy Message Sent Successfully.", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
        binding.callContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + contact.mobileNumber[0])
            startActivity(intent)
            dialog?.dismiss()
        }
    }
}