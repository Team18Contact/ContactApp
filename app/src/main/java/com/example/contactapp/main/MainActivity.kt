package com.example.contactapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.contactapp.R
import com.example.contactapp.contact.ContactListFragment
import com.example.contactapp.contact.ContactModel
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.detail.DetailFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewPager2Adapter by lazy {
        MainViewPagerAdapter(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {

        val checkName = intent.getStringExtra("userName") ?: "name"
        val checkTel = intent.getStringExtra("userTel") ?: "tel"
        val checkPosition = intent.getStringExtra("userPosition") ?: "position"
        val checkImage = intent.getIntExtra("userImage", 0)

        val detailFragment = viewPager2Adapter.getFragment(1) as? DetailFragment
        detailFragment?.setData(ContactModel(R.drawable.img_kds, checkName, checkTel, "01012345678", "asd@naver.com", checkPosition))

        viewPager2.adapter = viewPager2Adapter

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(viewPager2Adapter.getFragment(position) is ContactListFragment) {
                    imgGridView.visibility = View.VISIBLE
                    imgListView.visibility = View.VISIBLE
                } else {
                    imgGridView.visibility = View.INVISIBLE
                    imgListView.visibility = View.INVISIBLE
                }
            }
        })

        TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
            tab.setText(viewPager2Adapter.getTitle(pos))
        }.attach()

        val contactListFragment = viewPager2Adapter.getFragment(0) as? ContactListFragment
        imgGridView.setOnClickListener {
            contactListFragment?.showGridView()
        }

        imgListView.setOnClickListener {
            contactListFragment?.showRecyclerView()
        }

        btnFab.setOnClickListener { //fab 클릭 리스너
            //add contact dialog
        }
    }
}