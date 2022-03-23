package com.example.frisencerroneandroiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.frisencerroneandroiderestaurant.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button: Button = findViewById(R.id.next)
        button.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this, HomeActivity::class.java
                )
            )
        })



    }
}