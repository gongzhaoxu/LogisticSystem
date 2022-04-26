package com.example.logisticsystem

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.logisticsystem.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //数据库
        val dbHelper=MyDatabaseHelper(
            this,
            "LogisticSystem.db",
            1)

        var db = dbHelper.writableDatabase
        /**
         * 注册事件
         */
        binding.register.setOnClickListener {
            dbHelper.insertUser(db,"请补充您的专业班级","请补充您的姓名",
                binding.userlogin.text.toString(),binding.userpasswd.text.toString(),"请补充您的手机号")

            androidx.appcompat.app.AlertDialog.Builder(this).apply {
                setTitle("通知")
                setMessage("账号注册完成，请牢记您的账号密码！")
                setCancelable(false)
                //点击back无法返回，默认是true
                setPositiveButton("确认") { dialog, which ->
                    val intent = Intent(this.context, MainActivity::class.java)
                    intent.putExtra("user_login", binding.userlogin.text)
                    intent.putExtra("user_passwd", binding.userpasswd.text)
                    startActivity(intent)
                }
                setNegativeButton("返回") { dialog, which ->

                }
                show()
            }
        }

        /**
         * 返回到登录界面
         */
        binding.ret.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}