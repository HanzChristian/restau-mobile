package com.example.restau.View.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.restau.Controller.MenuTypeFragment
import com.example.restau.Model.Struk.Struk

class MenuPagerAdapter(fa: FragmentActivity, private val menuTypeIds: List<Long>, private val struk: Struk) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = menuTypeIds.size

    override fun createFragment(position: Int): Fragment {
        return MenuTypeFragment.newInstance(menuTypeIds[position], struk)
    }
}