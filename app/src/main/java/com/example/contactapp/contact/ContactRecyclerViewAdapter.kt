package com.example.contactapp.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.databinding.ContactRecyclerviewItemBinding

class ContactRecyclerViewAdapter (val models: MutableList<ContactModel>) : RecyclerView.Adapter<ContactRecyclerViewAdapter.Holder>(){

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {//
        val binding = ContactRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding) // holder return

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        holder.profileImg.setBackgroundResource(models[position].profile)
        holder.name.text = models[position].name
    }

    override fun getItemCount(): Int {
        return models.size
    }

    inner class Holder(val binding: ContactRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) { //
        val profileImg = binding.icItem
        val name = binding.tvItemName
        val like = binding.ivLike
    }
}