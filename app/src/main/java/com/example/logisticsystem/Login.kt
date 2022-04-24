package com.example.logisticsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.logisticsystem.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //数据库
//        val dbHelper=MyDatabaseHelper(
//                    this,
//                    "LogisticSystem.db",
//                    1)
//
//        var db = dbHelper.writableDatabase
//        dbHelper.insertUser(db,"计算机1904班","巩钊旭","20194711","123456","13993449346")

        binding.login.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user_login", binding.userlogin.text)
            intent.putExtra("user_passwd", binding.userpasswd.text)
            startActivity(intent)
        }
    }
}