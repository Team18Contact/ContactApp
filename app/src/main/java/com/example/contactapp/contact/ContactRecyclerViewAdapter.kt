package com.example.contactapp.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.databinding.ContactRecyclerviewItemBinding

class ContactRecyclerViewAdapter (private val contactList: MutableList<ContactModel>) : RecyclerView.Adapter<ContactRecyclerViewAdapter.Holder>(){

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {//
        val binding = ContactRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding) // holder return

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount(): Int = contactList.size

    inner class Holder(private val binding: ContactRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: ContactModel) = with(binding) {
            itemView.setOnClickListener {
                itemClick?.onClick(it, adapterPosition)
            }

            imgProfile.setImageResource(contact.profile)
            txtInfo.text = "${contact.name} (${contact.locale}) - ${contact.ability}"
        }
    }
}