package com.example.contactapp.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.contactapp.R
import com.example.contactapp.contact.ContactListFragment
import com.example.contactapp.detail.DetailFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private var fragments: ArrayList<MainTabs> = ArrayList()

    init {
        fragments.add(MainTabs(ContactListFragment(), R.string.main_tab_title_contact))
        fragments.add(MainTabs(DetailFragment(), R.string.main_tab_title_mypage))
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position].fragment
    }

    fun getTitle(position: Int): Int {
        return fragments[position].title
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position].fragment
    }
}