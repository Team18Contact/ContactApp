package com.example.contactapp.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.contact.Constants.convertToBitmap
import com.example.contactapp.databinding.ContactGridviewItemBinding
import com.example.contactapp.databinding.ContactRecyclerviewItemBinding

class ContactRecyclerViewAdapter (private val context: Context, private val contactList: MutableList<ContactModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object {
        const val ITEM_VIEW_TYPE_GRID = 0
        const val ITEM_VIEW_TYPE_LTR = 1
        const val ITEM_VIEW_TYPE_RTL = 2
    }

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null
    var itemLikeClick: ItemClick? = null

    private var isGridLayout: Boolean = false
    fun isGridLayout(bool: Boolean) {
        this.isGridLayout = bool
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_GRID -> {
                val binding = ContactGridviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridHolder(binding)
            }
            else -> {
                val binding = ContactRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RecyclerHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is GridHolder -> holder.bind(contactList[position])
            is RecyclerHolder -> {
                holder.bind(contactList[position])
                if(position % 2 == 0) {
                    holder.linearLayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
                } else {
                    holder.linearLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
                }
            }
        }
    }

    override fun getItemCount(): Int = contactList.size

    override fun getItemViewType(position: Int): Int {
        return if(isGridLayout) {
            ITEM_VIEW_TYPE_GRID
        } else {
            if(position % 2 == 0) {
                ITEM_VIEW_TYPE_LTR
            } else {
                ITEM_VIEW_TYPE_RTL
            }
        }
    }

    inner class GridHolder(private val binding: ContactGridviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: ContactModel) = with(binding) {
            itemView.setOnClickListener {
                itemClick?.onClick(it, adapterPosition)
            }
            imgProfile.setImageBitmap(convertToBitmap(context, contact.profile))
            txtName.text = contact.name
            txtAbility.text = contact.ability
        }
    }

    inner class RecyclerHolder(private val binding: ContactRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var linearLayout: LinearLayout
        lateinit var contactNumber: String
        fun bind(contact: ContactModel) = with(binding) {
            linearLayout = contactLinearLayout
            contactNumber = contact.phoneNum

            itemView.setOnClickListener {
                itemClick?.onClick(it, adapterPosition)
            }

            imgLike.setOnClickListener {
                itemLikeClick?.onClick(it, adapterPosition)
            }

            imgProfile.setImageBitmap(convertToBitmap(context, contact.profile))
            val localeTxt = if(contact.locale.isNotEmpty()) " (${contact.locale}) " else contact.locale
            val abilityTxt = if(contact.ability.isNotEmpty()) "- ${contact.ability}" else contact.ability
            txtInfo.text = "${contact.name}$localeTxt$abilityTxt"
            imgLike.setImageResource(if(contact.isHeart == 1) R.drawable.ic_full_heart else R.drawable.ic_empty_heart)
        }
    }

    fun addItem(contact: ContactModel) {
        contactList.add(contact)
        notifyItemChanged(contactList.size - 1)
    }

    fun updateItem(contact: ContactModel, position: Int) {
        contactList[position] = if(contact.isHeart == 1) contact.copy(isHeart = 0) else contact.copy(isHeart = 1)
        contactList.sortWith(compareBy ({-it.isHeart}, {it.name}))
//        notifyItemChanged(position)
        notifyDataSetChanged()
    }
}