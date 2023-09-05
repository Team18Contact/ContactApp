package com.example.contactapp.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import com.example.contactapp.databinding.ContactGridviewItemBinding

class ContactGridViewAdapter(private val contactList: MutableList<ContactModel>): BaseAdapter() {
    private lateinit var binding: ContactGridviewItemBinding

    interface OnItemClickListener {
        fun onItemClick(contact: ContactModel)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun getCount(): Int = contactList.size

    override fun getItem(position: Int): ContactModel  = contactList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        if (convertView == null) {
            binding = ContactGridviewItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            holder = ViewHolder(binding)
            binding.root.tag = holder

            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(getItem(position))
            }
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.bind(getItem(position))
        return binding.root
    }

    private class ViewHolder(private val binding: ContactGridviewItemBinding) {
        fun bind(contact: ContactModel) = with(binding) {
            imgProfile.setImageResource(contact.profile)
            txtName.text = contact.name
            txtAbility.text = contact.ability
        }
    }
}