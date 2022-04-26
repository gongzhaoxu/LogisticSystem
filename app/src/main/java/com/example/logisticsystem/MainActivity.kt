package com.example.logisticsystem

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.logisticsystem.databinding.ActivityMainBinding
import com.example.logisticsystem.ui.input.InputFragment
import com.example.logisticsystem.ui.mine.MineFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("MainActivity加载中", "MainActivity加载中")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
//
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_query, R.id.navigation_input, R.id.navigation_mine
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        getSupportFragmentManager().findFragmentById(R.id.navigation_mine).
        navView.setupWithNavController(navController)
        /**
         * 获取登录界面传来的账号密码
         */
        val intent = getIntent()
        var bundle = intent.extras
        val user_login = bundle?.get("user_login").toString()
        val user_passwd = bundle?.get("user_passwd").toString()

        /**
         * 获取数据库当前用户
         */
        val dbHelper = MyDatabaseHelper(this, "LogisticSystem.db", 1)
        var db = dbHelper.writableDatabase

        val currentUser = dbHelper.getCurrentUser(db)

        Log.e("MainActivity获取到Login页面的账号密码", "$user_login---$user_passwd")
        Log.e("MainActivity获取到数据库的当前账号", "$currentUser")

        /**
         * 主界面传登录信息给mineFragment
         */

        var bundleFragment:Bundle=Bundle()

        bundleFragment.putString("user_login",user_login)
        bundleFragment.putString("user_passwd",user_passwd)

        bundleFragment.putString("currentUser",user_login)
//        val mineFragment = MineFragment()
//        mineFragment.arguments = bundleFragment
//        val manager = supportFragmentManager
//        val transaction  = manager.beginTransaction()
//        //事务的布局为“我的”碎片的布局
//        transaction.replace(R.id.ignore1,mineFragment)
//        transaction.commit()

        val inputFragment = InputFragment()
        inputFragment.arguments = bundleFragment
        val manager = supportFragmentManager
        val transaction  = manager.beginTransaction()
        //事务的布局为“ignore1”碎片的布局
        transaction.replace(R.id.ignore1,inputFragment)
        transaction.commit()



    }
}