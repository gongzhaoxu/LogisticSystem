package com.example.logisticsystem

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.logisticsystem.databinding.ActivityCompleteUserBinding

class CompleteUser: AppCompatActivity() {
    private lateinit var binding: ActivityCompleteUserBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //        数据库
        val dbHelper = MyDatabaseHelper(
            this,
            "LogisticSystem.db",
            1
        )

        var db = dbHelper.writableDatabase

        val intent = getIntent()
        val bundle = intent.extras
        val user_login = bundle?.get("user_login").toString()
        /**
         * 提交表单
         */
        binding.submit.setOnClickListener {

            var user_department = binding.userDepartment.text.toString()
            var user_name = binding.userName.text.toString()
            var user_tel = binding.userTel.text.toString()

            if (dbHelper != null) {
                if (db != null) {
                   dbHelper.completeUserInfo(db,user_login,user_department,user_name,user_tel)
                }
            }
            AlertDialog.Builder(this).apply {
                setTitle("通知")
                setMessage("完善资料成功")
                setCancelable(false)
                //点击back无法返回，默认是true
                setPositiveButton("确认") { dialog, which ->
                    val intent = Intent(this@CompleteUser, MainActivity::class.java)
                    startActivity(intent)
                }
                setNegativeButton("返回") { dialog, which ->
                    val intent = Intent(this@CompleteUser, MainActivity::class.java)
                    startActivity(intent)
                }
                show()
            }

        }
    }
}