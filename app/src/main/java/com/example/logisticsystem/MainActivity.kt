package com.example.logisticsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.logisticsystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.login.setOnClickListener {
            val intent = Intent(this, Info::class.java)
            intent.putExtra("user_login", binding.userlogin.text)
            intent.putExtra("user_passwd", binding.userpasswd.text)
            startActivity(intent)
        }
    }
}