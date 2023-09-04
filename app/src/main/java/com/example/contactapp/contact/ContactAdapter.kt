package com.example.contactapp.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.databinding.ContactItemBinding
import com.google.android.engage.common.datamodel.Image


class ContactAdapter (val models: MutableList<ContactModel>) : RecyclerView.Adapter<ContactAdapter.Holder>(){

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {//
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding) // holder return

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.profileImg.setBackgroundResource(models[position].profile)
        holder.name.text = models[position].name


        //holder.like.setImageResource(R.drawable.ic_heart)

    }


    override fun getItemCount(): Int {
        return models.size
    }


    inner class Holder(val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) { //
        val profileImg = binding.icItem
        val name = binding.tvItemName
        val like = binding.ivLike

    }

}