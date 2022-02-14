package com.example.frisencerroneandroiderestaurant

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PhotoAdapter(activity: AppCompatActivity, private val items: List<String>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return items.count()
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoFragment.newInstance(items[position])
    }
}