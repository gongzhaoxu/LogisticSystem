package com.example.logisticsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.example.logisticsystem.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        数据库
        val dbHelper = MyDatabaseHelper(
            this,
            "LogisticSystem.db",
            1
        )

        var db = dbHelper.writableDatabase
        dbHelper.insertUser(db, "计算机1904班", "巩钊旭", "20194711", "123456", "13993449346")
        dbHelper.insertUser(db, "计算机1904班", "测试者", "20190001", "123456", "13993449346")
        dbHelper.insertUser(db, "软件1901班", "无名氏", "20190000", "123456", "15524462588")
        dbHelper.insertUser(db, "小彩蛋", "属于我们", "20190205", "20190205", "5201314")

        dbHelper.completeUserInfo(db,"1001","测试部门","管理员","18888888888")

        dbHelper.deleteItem(db,"1")
        /**
         * 账号实时显示正确与否
         */
        binding.userlogin.addTextChangedListener {
            var userList = ArrayList<User>()
            var user = binding.userlogin.text.toString()
            //获取当前用户信息
            userList = dbHelper.getUser(db, user)
            //未找到该用户
            if (userList.size == 0) {
                binding.userloginStatus.setImageResource(R.drawable.ic_wrong)
            } else {
                userList = dbHelper.getUser(db, user)
                //正确
                if (userList[userList.size - 1].user_login == user) {
                    binding.userloginStatus.setImageResource(R.drawable.ic_right)
                    var userPasswd = binding.userpasswd.text.toString()
                    //若密码也对则对
                    if (userList[userList.size - 1].user_passwd == userPasswd) {
                        binding.userpasswdStatus.setImageResource(R.drawable.ic_right)
                    }
                } else {
                    binding.userloginStatus.setImageResource(R.drawable.ic_wrong)
                    var userPasswd = binding.userpasswd.text.toString()
                    if (userPasswd != "") {
                        binding.userpasswdStatus.setImageResource(R.drawable.ic_wrong)
                    }
                }
            }
            //内容为空就不显示了
            if (user == "") {
                binding.userloginStatus.setImageResource(R.drawable.editview_border)
            }
        }


        /**
         * 密码实时显示正确与否
         */
        binding.userpasswd.addTextChangedListener {
            var userList = ArrayList<User>()
            //获取用户输入的正确账号
            var user = binding.userlogin.text.toString()
            //获取用户输入的密码
            var userPasswd = binding.userpasswd.text.toString()
            //获取当前用户信息
            userList = dbHelper.getUser(db, user)

            //未找到该用户
            if (userList.size == 0) {
                binding.userpasswdStatus.setImageResource(R.drawable.ic_wrong)
            } else {
                //如果密码正确
                if (userList[userList.size - 1].user_passwd == userPasswd) {
                    binding.userpasswdStatus.setImageResource(R.drawable.ic_right)
                }//回退
                else {
                    binding.userpasswdStatus.setImageResource(R.drawable.ic_wrong)
                }
            }
            //内容为空就不显示了
            if (userPasswd == "") {
                binding.userpasswdStatus.setImageResource(R.drawable.editview_border)
            }
        }


        /**
         * 登录事件绑定
         */
        binding.login.setOnClickListener {

            var userLogin = binding.userlogin.text.toString()
            var userPasswd = binding.userpasswd.text.toString()

            var userList = ArrayList<User>()

            //获取当前用户信息
            userList = dbHelper.getUser(db, userLogin)
            if (userList.size != 0) {
                if ((userLogin == userList[userList.size - 1].user_login) && (userPasswd == userList[userList.size - 1].user_passwd)) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("user_login", binding.userlogin.text)
                    intent.putExtra("user_passwd", binding.userpasswd.text)
                    startActivity(intent)
                } else {
                    androidx.appcompat.app.AlertDialog.Builder(this).apply {
                        setTitle("通知")
                        setMessage("账号或密码错误，请重新输入正确的账号密码！")
                        setCancelable(false)
                        //点击back无法返回，默认是true
                        setPositiveButton("确认") { dialog, which ->
                        }
                        setNegativeButton("返回") { dialog, which ->

                        }
                        show()
                    }
                }
            } else {
                androidx.appcompat.app.AlertDialog.Builder(this).apply {
                    setTitle("通知")
                    setMessage("账号或密码错误，请重新输入正确的账号密码！")
                    setCancelable(false)
                    //点击back无法返回，默认是true
                    setPositiveButton("确认") { dialog, which ->
                    }
                    setNegativeButton("返回") { dialog, which ->

                    }
                    show()
                }
            }
        }
        /**
         * 注册事件绑定
         */
        binding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}