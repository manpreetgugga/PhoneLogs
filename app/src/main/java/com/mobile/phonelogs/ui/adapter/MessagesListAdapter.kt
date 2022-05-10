package com.mobile.phonelogs.ui.adapter

import android.content.Context
import android.text.Html
import android.text.format.DateUtils
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.phonelogs.databinding.ItemLayoutMessageBinding
import com.mobile.phonelogs.domain.entities.Contact
import com.mobile.phonelogs.domain.entities.Message

class MessagesListAdapter(var context: Context, var messages: ArrayList<Message>) : RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder>() {

    class MessageViewHolder(var binding: ItemLayoutMessageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemLayoutMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.binding.apply {
            messages[position].apply {
                messageName.text = readState ?: "Unknown"
                messageText.text = Html.fromHtml(messsage)
                messageText.movementMethod = LinkMovementMethod.getInstance()
                time?.let {
                    val time = DateUtils.formatDateTime(context, it.toLong(), DateUtils.FORMAT_SHOW_TIME)
                    val date = DateUtils.formatDateTime(context, it.toLong(), DateUtils.FORMAT_SHOW_DATE)
                    messageTime.text = "$time $date"
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun clear() {
        messages.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(messages: List<Message>) {
        this.messages.addAll(messages)
        notifyDataSetChanged()
    }

}