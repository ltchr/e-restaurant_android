package com.example.frisencerroneandroiderestaurant

import android.os.Bundle
import com.example.frisencerroneandroiderestaurant.databinding.HomeMainBinding
import android.content.Intent
import android.util.Log

class HomeActivity : BaseActivity() {
    private lateinit var binding: HomeMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.home_main)
        setContentView(binding.root)

        binding.buttonFirst.setOnClickListener {
            manageIntent(ItemType.STARTER)
            // Toast.makeText(baseContext, "Back to menu", Toast.LENGTH_SHORT).show()
        }
        binding.buttonFirst2.setOnClickListener {
            manageIntent(ItemType.MAIN)
        }
        binding.buttonFirst3.setOnClickListener {
            manageIntent(ItemType.DESSERT)
        }
    }

    private fun manageIntent(categoryKey: ItemType) {
        val myIntent = Intent(this, NextActivity::class.java)
        myIntent.putExtra(CATEGORY_KEY, categoryKey)
        Log.i("Info Debug", "End Home activity")

        this.startActivity(myIntent)
    }

    companion object {
        const val CATEGORY_KEY = "category_key"
    }
}