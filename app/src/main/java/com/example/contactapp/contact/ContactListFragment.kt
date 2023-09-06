package com.example.contactapp.contact

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.contactapp.R
import com.example.contactapp.contact.Constants.ITEM_OBJECT
import com.example.contactapp.contact.ContactModelDB.dataList
import com.example.contactapp.databinding.FragmentContactListBinding
import com.example.contactapp.detail.DetailFragment

class ContactListFragment : Fragment() {

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private val recyclerViewAdapter by lazy {
        ContactRecyclerViewAdapter(dataList)
    }

    private val gridViewAdapter by lazy {
        ContactGridViewAdapter(dataList)
    }

    private val contactRealList by lazy {
        getContacts()
    }

    private val recyclerViewRealAdapter by lazy {
        ContactRecyclerViewAdapter(contactRealList)
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
        recyclerViewContact.adapter = recyclerViewAdapter

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

        gridViewAdapter.onItemClickListener = object: ContactGridViewAdapter.OnItemClickListener {
            override fun onItemClick(contact: ContactModel) {
                bundleToDetailFragment(contact)
            }
        }
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
        gridViewContact.adapter = gridViewAdapter
        recyclerViewContact.visibility = View.GONE
        gridViewContact.visibility = View.VISIBLE
    }

    fun showRecyclerView(num: Int) = with(binding) {
        when(num) {
            0 -> recyclerViewContact.adapter = recyclerViewAdapter
            1 -> recyclerViewContact.adapter = recyclerViewRealAdapter
        }
        recyclerViewContact.visibility = View.VISIBLE
        gridViewContact.visibility = View.GONE
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
            var count = 0
            while(cursor.moveToNext() && count < 15) {
                count++ //임의로 15개까지만 출력
                contactList.add(ContactModel(R.drawable.ic_empty_user, cursor.getString(1), "", cursor.getString(2), "", ""))
            }
        }
        return contactList
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null //구글 권장 메모리 누수 방지
    }
}