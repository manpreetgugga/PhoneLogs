package com.mobile.phonelogs.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.phonelogs.R
import com.mobile.phonelogs.base.fragment.BaseFragment
import com.mobile.phonelogs.databinding.ItemLayoutContactBinding
import com.mobile.phonelogs.domain.entities.Contact
import com.mobile.phonelogs.ui.dialog.CallDialogFragment

class ContactListAdapter(var context: BaseFragment, var contacts: ArrayList<Contact>) : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    class ContactViewHolder(var binding: ItemLayoutContactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemLayoutContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.binding.apply {
            contacts[position].apply {
                Glide.with(context)
                    .load(photoURI ?: R.drawable.ic_baseline_person_pin_24)
                    .into(contactLogo);
                contactName.text = name ?: ""
                holder.itemView.setOnClickListener {
                    context.showDialog(CallDialogFragment(this))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun clear() {
        contacts.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(contacts: List<Contact>) {
        this.contacts.addAll(contacts)
        notifyDataSetChanged()
    }
}