package com.example.contactapp.contact

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.contact.Constants.ITEM_OBJECT
import com.example.contactapp.contact.Constants.convertToUri
import com.example.contactapp.contact.ContactModelDB.dataList
import com.example.contactapp.databinding.FragmentContactListBinding
import com.example.contactapp.detail.DetailFragment

class ContactListFragment : Fragment() {
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private val recyclerViewAdapter by lazy {
        ContactRecyclerViewAdapter(requireContext(), dataList)
    }

    private val contactRealList by lazy {
        getContacts()
    }

    private val recyclerViewRealAdapter by lazy {
        ContactRecyclerViewAdapter(requireContext(), contactRealList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        recyclerViewContact.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter.isGridLayout(false)
        recyclerViewContact.adapter = recyclerViewAdapter

        val itemHelperCallback = ContactListItemHelper(requireContext(), this@ContactListFragment)
        val itemTouchHelper = ItemTouchHelper(itemHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewContact)

        recyclerViewAdapter.itemClick = object : ContactRecyclerViewAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                bundleToDetailFragment(dataList[position])
            }
        }

        recyclerViewRealAdapter.itemClick = object : ContactRecyclerViewAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                bundleToDetailFragment(contactRealList[position])
            }
        }
    }

    fun addContactList(contact: ContactModel) {
        recyclerViewAdapter.addItem(contact)
    }

    fun updateSwipeItem(viewHolder: RecyclerView.ViewHolder) {
//        recyclerViewAdapter.notifyItemChanged(viewHolder.adapterPosition)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun bundleToDetailFragment(contact: ContactModel) {
        val detailFragment = DetailFragment()
        val bundle = bundleOf(ITEM_OBJECT to contact)

        detailFragment.arguments = bundle
//                detailFragment.arguments = Bundle().apply {
//                    bundleOf(ITEM_OBJECT to dataList[position])
//                }
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.mainConstrainLayout, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    fun showGridView() = with(binding) {
        recyclerViewContact.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerViewAdapter.isGridLayout(true)
        recyclerViewContact.adapter = recyclerViewAdapter

    }

    fun showRecyclerView(num: Int) = with(binding) {
        recyclerViewContact.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter.isGridLayout(false)
        when(num) {
            0 -> recyclerViewContact.adapter = recyclerViewAdapter
            1 -> recyclerViewContact.adapter = recyclerViewRealAdapter
        }
    }

    private fun getContacts(): MutableList<ContactModel> {
        val contactList = mutableListOf<ContactModel>()
        val cursor = requireContext().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf<String>(
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            null
        )
        if(cursor != null) {
//            var count = 0
//            while(cursor.moveToNext() && count < 15) {
//                count++ //임의로 15개까지만 출력
            while(cursor.moveToNext()) {
                contactList.add(ContactModel(convertToUri(R.drawable.ic_empty_user), cursor.getString(1), "", cursor.getString(2), "", ""))
            }
        }
        return contactList
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null //구글 권장 메모리 누수 방지
    }
}