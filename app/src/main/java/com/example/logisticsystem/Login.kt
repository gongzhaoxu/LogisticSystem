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
//        数据库
        val dbHelper=MyDatabaseHelper(
                    this,
                    "LogisticSystem.db",
                    1)

        var db = dbHelper.writableDatabase
        dbHelper.insertUser(db,"计算机1904班","巩钊旭","20194711","123456","13993449346")
        dbHelper.insertUser(db,"计算机1904班","测试者","20190001","123456","13993449346")
        dbHelper.insertUser(db,"软件1901班","无名氏","20190000","123456","15524462588")
        dbHelper.insertUser(db,"小彩蛋","属于我们","20190205","20190205","5201314")
        binding.login.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user_login", binding.userlogin.text)
            intent.putExtra("user_passwd", binding.userpasswd.text)
            startActivity(intent)
        }
    }
}