package com.example.contactapp.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.example.contactapp.contact.Constants.ITEM_OBJECT
import com.example.contactapp.contact.Constants.convertToBitmap
import com.example.contactapp.contact.ContactModel
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
        imgPhoto.setImageBitmap(convertToBitmap(requireContext(), receiveData.profile))
        tvName.text = receiveData.name
        tvPhoneNumber.text = receiveData.phoneNum
        tvEmail.text = receiveData.email
        tvLocale.text = receiveData.locale
        tvAbility.text = receiveData.ability

        /**
         *  data 값이 null이면 receiveData를 받아오니까, 이 경우가 마이페이지 데이터가 보이는 경우이므로
         *  메세지, 콜 버튼을 사라지게 했습니다!
         */
        if (data == null) {
            btnMessageButton.visibility = View.GONE
            btnCallButton.visibility = View.GONE
        } // 마이페이지 일 때, 메세지 및 콜 버튼 사라지는 기능 완료

        /**
         *  디테일 페이지에서 버튼을 누르면 전화와 문자를 보내는 기능 추가입니다.
         */
        btnCallButton.setOnClickListener {
            val phoneNumber = tvPhoneNumber.text
            val callUriSwipedPerson = Uri.parse("tel:${phoneNumber}")
            startActivity(Intent(Intent.ACTION_CALL, callUriSwipedPerson))
        }

        btnMessageButton.setOnClickListener {
            val phoneNumber = tvPhoneNumber.text
            val sendUriSwipedPerson = Uri.parse("smsto:${phoneNumber}")
            startActivity(Intent(Intent.ACTION_SENDTO, sendUriSwipedPerson))
        } // 디테일 페이지 버튼 기능 완료
    }

    fun setData(contact: ContactModel) {
        receiveData = contact
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null //구글 권장 메모리 누수 방지
    }
}