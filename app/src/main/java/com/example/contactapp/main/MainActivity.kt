package com.example.contactapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.contactapp.contact.ContactListFragment
import com.example.contactapp.databinding.ActivityMainBinding
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