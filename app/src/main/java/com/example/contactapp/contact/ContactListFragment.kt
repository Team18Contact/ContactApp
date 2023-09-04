package com.example.contactapp.contact

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.R
import com.example.contactapp.contact.Constants.ITEM_INDEX
import com.example.contactapp.contact.Constants.ITEM_OBJECT
import com.example.contactapp.contact.ContactModelDB.dataList
import com.example.contactapp.databinding.FragmentContactListBinding
import com.example.contactapp.detail.DetailFragment
//import com.google.android.gms.common.internal.Constants

class ContactListFragment : Fragment() {

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!


    //더미데이터


//    val dataList: MutableList<ContactModel> = mutableListOf(
//        ContactModel(R.drawable.img_kds, "김두식", "문산", "010-1111-1111", "kds@gmail.com", "비행"),
//        ContactModel(R.drawable.img_jjw, "장주원", "구룡포", "010-2222-2222", "jjw@gmail.com", "무한 재생"),
//        ContactModel(R.drawable.img_lmh, "이미현", "-", "010-3333-3333", "lmh@gmail.com", "초인적 오감"),
//        ContactModel(R.drawable.img_jgd, "전계도", "-", "010-4444-4444", "jgd@gmail.com", "전기"),
//        ContactModel(
//            R.drawable.img_frank,
//            "프랭크",
//            "아이오와",
//            "010-5555-5555",
//            "frank@gmail.com",
//            "무한재생"
//        ),
//        ContactModel(R.drawable.img_ljm, "이재만", "-", "010-6666-6666", "ljm@gmail.com", "괴력, 스피드정"),
//        ContactModel(R.drawable.img_jsj, "정상진", "진천", "010-7777-7777", "jsj@gmail.com", "괴력"),
//        ContactModel(R.drawable.img_jys, "전영석", "봉평", "010-8888-8888", "jys@gmail.com", "전기"),
//        ContactModel(R.drawable.img_hsh, "홍성화", "나주", "010-9999-9999", "hsh@gmail.com", "투시")
//    )


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


        val adapter = ContactAdapter(dataList)

        recyclerViewContact.adapter = adapter

        adapter.itemClick = object : ContactAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val detailFragment = DetailFragment()
                val bundle = bundleOf(ITEM_OBJECT to dataList[position])

                detailFragment.arguments = bundle
//                detailFragment.arguments = Bundle().apply {
//                    bundleOf(ITEM_OBJECT to dataList[position])
//                }
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.mainConstrainLayout, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null //구글 권장 메모리 누수 방지
    }
}