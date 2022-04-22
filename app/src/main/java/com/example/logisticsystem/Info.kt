package com.example.logisticsystem

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.logisticsystem.databinding.ActivityInfoBinding

class Info  : AppCompatActivity(){
    private lateinit var binding: ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = getIntent()
        val bundle = intent.extras
        val user_login = bundle?.get("user_login").toString()
        val user_passwd = bundle?.get("user_passwd").toString()
        Log.e("user_login",user_login)
        Log.e("user_passwd",user_passwd)
        Toast.makeText(this, "user:"+user_login+" passwd:"+user_passwd, Toast.LENGTH_SHORT).show()
    }
}