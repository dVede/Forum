package com.example.forum.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.forum.adapter.LoginAdapter
import com.example.forum.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val types = arrayOf(
        "Login",
        "Register"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val tabLayout: TabLayout = binding.tabLayout
        val viewPager: ViewPager2 = binding.pager

        types.forEach { type ->
            tabLayout.addTab(tabLayout.newTab().setText(type))
        }

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val loginAdapter = LoginAdapter(this)
        viewPager.adapter = loginAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = types[position]
        }.attach()
    }
}