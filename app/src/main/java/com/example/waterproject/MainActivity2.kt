package com.example.waterproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.waterproject.Adapter.FragmentBodyAdapter
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity2 : AppCompatActivity() {
    private lateinit var navBar: ChipNavigationBar
    private lateinit var viewPager : ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        navBar=findViewById(R.id.navBar)
        navBar.setMenuResource(R.menu.navmenu)
        //tabLayout : TabLayout= tabLayout
        //viewPager: ViewPager = viewPager
        viewPager = findViewById(R.id.viewPage)


        val adapter = FragmentBodyAdapter(supportFragmentManager,lifecycle)
        viewPager.adapter = adapter

//        val homeFragment = HomeFragment()
//        val issues = IssueFragment()
//        val solutins = ReachOut()
//        val settings = settings()
        viewPager.isUserInputEnabled=false
        navBar.setOnItemSelectedListener { id ->
            // 0 -> viewPager.currentItem =
                when (id) {
                    R.id.home -> viewPager.currentItem=0
                    R.id.issues -> viewPager.currentItem=1
                    R.id.solutions -> viewPager.currentItem=2
                    R.id.settings -> viewPager.currentItem=3
                    else -> viewPager.currentItem=2
                }
        }
        // else -> getItem(position)

    }

}
