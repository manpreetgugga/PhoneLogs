package com.mobile.phonelogs.ui.adapter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.phonelogs.R
import com.mobile.phonelogs.databinding.ItemLayoutCallLogBinding
import com.mobile.phonelogs.domain.entities.PhoneCallLog


class CallLogsListAdapter(var context: Activity, var phoneLogs: ArrayList<PhoneCallLog>) : RecyclerView.Adapter<CallLogsListAdapter.CallLogViewHolder>() {

    class CallLogViewHolder(var binding: ItemLayoutCallLogBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogViewHolder {
        val binding = ItemLayoutCallLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CallLogViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return phoneLogs[position].viewType
    }

    override fun onBindViewHolder(holder: CallLogViewHolder, position: Int) {
        holder.binding.apply {
            phoneLogs[position].apply {
                phoneLogo.setImageResource(0)
                phoneName.text = if (TextUtils.isEmpty(name)) "Unknown" else name
                when (viewType) {
                    0 -> phoneLogo.setImageResource(R.drawable.ic_baseline_call_received_24)
                    1 -> phoneLogo.setImageResource(R.drawable.ic_baseline_call_outgoing_24)
                    2 -> phoneLogo.setImageResource(R.drawable.ic_baseline_call_missed_24)
                }
                phoneNumber.text = phNum
                val time = DateUtils.formatDateTime(context, callDate, DateUtils.FORMAT_SHOW_TIME)
                val date = DateUtils.formatDateTime(context, callDate, DateUtils.FORMAT_SHOW_DATE)
                phoneTime.text = "$time $date"
                root.setOnClickListener {
                    val dial = Intent()
                    dial.action = "android.intent.action.DIAL"
                    dial.data = Uri.parse("tel:$phNum")
                    context.startActivity(dial)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return phoneLogs.size
    }

    fun clear() {
        phoneLogs.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(phoneLogs: List<PhoneCallLog>) {
        this.phoneLogs.addAll(phoneLogs)
        notifyDataSetChanged()
    }

}