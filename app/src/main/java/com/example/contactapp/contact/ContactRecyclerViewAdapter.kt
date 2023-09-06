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

        if(position % 2 == 0) {
            holder.itemView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        } else {
            holder.itemView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
    }

    override fun getItemCount(): Int = contactList.size

    inner class Holder(private val binding: ContactRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: ContactModel) = with(binding) {
            itemView.setOnClickListener {
                itemClick?.onClick(it, adapterPosition)
            }

            imgProfile.setImageResource(contact.profile)
            val localeTxt = if(contact.locale.isNotEmpty()) " (${contact.locale}) " else contact.locale
            val abilityTxt = if(contact.ability.isNotEmpty()) "- ${contact.ability}" else contact.ability
            txtInfo.text = "${contact.name}$localeTxt$abilityTxt"
        }
    }
}