package com.example.contactapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.example.contactapp.contact.Constants
import com.example.contactapp.contact.Constants.ITEM_OBJECT
import com.example.contactapp.contact.Constants.convertToBitmap
import com.example.contactapp.contact.ContactModel
import com.example.contactapp.contact.ContactModelDB
import com.example.contactapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val data: ContactModel? by lazy {
        arguments?.getParcelable<ContactModel>(ITEM_OBJECT)
    }
    lateinit var receiveData: ContactModel

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
        val receiveData = data ?: receiveData
        imgPhoto.setImageBitmap(receiveData.let {convertToBitmap(requireContext(), it.profile) })
        tvName.text = receiveData.name
        tvPhoneNumber.text = receiveData.phoneNum
        tvEmail.text = receiveData.email
        tvLocale.text = receiveData.locale
        tvAbility.text = receiveData.ability
    }

    fun setData(contact: ContactModel) {
        receiveData = contact
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null //구글 권장 메모리 누수 방지
    }
}