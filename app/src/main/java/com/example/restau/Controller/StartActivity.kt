// StartActivity.kt
package com.example.restau.Controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.restau.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Submit button
        binding.buttonSubmit.setOnClickListener {

            //Intent the name to the MainActivity
            val name = binding.editTextName.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("namaKustomer", name)
            startActivity(intent)
        }
    }
}