package com.example.waterproject.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.waterproject.HomeFragment
import com.example.waterproject.IssueFragment
import com.example.waterproject.ReachOut
import com.example.waterproject.settings

class FragmentBodyAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {

        return when(position) {
            0-> HomeFragment()
            1-> IssueFragment()
            2-> ReachOut()
            3-> settings()

            else -> HomeFragment()
        }
    }
}