package com.example.contactapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.example.contactapp.contact.Constants
import com.example.contactapp.contact.Constants.ITEM_OBJECT
import com.example.contactapp.contact.ContactModel
import com.example.contactapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val data: ContactModel? by lazy {
        arguments?.getParcelable<ContactModel>(ITEM_OBJECT)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()


    }

    private fun initView() = with(binding) {


        imgPhoto.setImageDrawable(data?.let {
            ResourcesCompat.getDrawable(
                resources,
                it.profile,
                null
            )
        })
        tvName.text = data?.name
        tvPhoneNumber.text = data?.phoneNum
        tvEmail.text = data?.email


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null //구글 권장 메모리 누수 방지
    }
}