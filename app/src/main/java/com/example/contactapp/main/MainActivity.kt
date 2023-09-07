package com.example.contactapp.main

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        checkPermission()
    }

    private fun initView() = with(binding) {
        val checkName = intent.getStringExtra("userName") ?: "name"
        val checkEmailAddress = intent.getStringExtra("userEmailAddress") ?: "emailaddress"
        val checkTel = intent.getStringExtra("userTel") ?: "tel"
        val checkLocale = intent.getStringExtra("userLocale") ?: "locale"
        val checkAbility = intent.getStringExtra("userAbility") ?: "ability"

        val detailFragment = viewPager2Adapter.getFragment(1) as? DetailFragment
        detailFragment?.setData(ContactModel(R.drawable.ic_empty_user, checkName, checkLocale, checkTel, checkEmailAddress, checkAbility))

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
            contactListFragment?.showRecyclerView(0)
        }

        imgListView.setOnLongClickListener {
            contactListFragment?.showRecyclerView(1)
            true
        }

        btnFab.setOnClickListener { //fab 클릭 리스너
            //add contact dialog
        }
    }

    private fun checkPermission() {
        if(ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf<String>("android.permission.READ_CONTACTS", "android.permission.CALL_PHONE"), 100)
        } else {
            initView()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show()
            initView()
        } else {
            Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
