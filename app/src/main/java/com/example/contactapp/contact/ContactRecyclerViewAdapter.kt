package com.example.contactapp.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.contact.Constants.convertToBitmap
import com.example.contactapp.databinding.ContactRecyclerviewItemBinding

class ContactRecyclerViewAdapter (private val context: Context, private val contactList: MutableList<ContactModel>) : RecyclerView.Adapter<ContactRecyclerViewAdapter.Holder>(){
    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {//
        val binding = ContactRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(context, binding) // holder return
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(contactList[position])

        if(position % 2 == 0) {
            holder.linearLayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
        } else {
            holder.linearLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
    }

    override fun getItemCount(): Int = contactList.size

    inner class Holder(private val context: Context, private val binding: ContactRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var linearLayout: LinearLayout
        lateinit var contactNumber: String
        fun bind(contact: ContactModel) = with(binding) {
            linearLayout = contactLinearLayout
            contactNumber = contact.phoneNum

            itemView.setOnClickListener {
                itemClick?.onClick(it, adapterPosition)
            }

            imgProfile.setImageBitmap(convertToBitmap(context, contact.profile))
            val localeTxt = if(contact.locale.isNotEmpty()) " (${contact.locale}) " else contact.locale
            val abilityTxt = if(contact.ability.isNotEmpty()) "- ${contact.ability}" else contact.ability
            txtInfo.text = "${contact.name}$localeTxt$abilityTxt"
        }
    }

    fun addItem(contact: ContactModel) {
        contactList.add(contact)
        notifyItemChanged(contactList.size - 1)
    }
}