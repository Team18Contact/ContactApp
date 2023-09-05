package com.example.contactapp.contact

import android.os.Parcelable
import com.example.contactapp.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactModel(
    val profile: Int,
    val name: String,
    val locale: String,
    val phoneNum: String,
    val email: String,
    val ability: String,
) : Parcelable



object ContactModelDB {
    var dataList = mutableListOf<ContactModel>()
    init {

        dataList.apply{
            add(ContactModel(R.drawable.img_kds, "김두식", "문산", "010-1111-1111", "kds@gmail.com", "비행"))
            add(ContactModel(R.drawable.img_jjw, "장주원", "구룡포", "010-2222-2222", "jjw@gmail.com", "무한 재생"))
            add(ContactModel(R.drawable.img_lmh, "이미현", "-", "010-3333-3333", "lmh@gmail.com", "초인적 오감"))
            add(ContactModel(R.drawable.img_jgd, "전계도", "-", "010-4444-4444", "jgd@gmail.com", "전기"))
            add(ContactModel(R.drawable.img_frank, "프랭크", "아이오와", "010-5555-5555", "frank@gmail.com", "무한재생"))
            add(ContactModel(R.drawable.img_ljm, "이재만", "-", "010-6666-6666", "ljm@gmail.com", "괴력, 스피드정"))
            add(ContactModel(R.drawable.img_jsj, "정상진", "진천", "010-7777-7777", "jsj@gmail.com", "괴력"))
            add(ContactModel(R.drawable.img_jys, "전영석", "봉평", "010-8888-8888", "jys@gmail.com", "전기"))
            add(ContactModel(R.drawable.img_hsh, "홍성화", "나주", "010-9999-9999", "hsh@gmail.com", "투시"))

        }

    }
}

