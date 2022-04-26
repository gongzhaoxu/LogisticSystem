package com.example.logisticsystem.ui.mine

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.logisticsystem.*
import com.example.logisticsystem.databinding.FragmentMineBinding
import com.example.logisticsystem.ui.SharedViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MineFragment : Fragment() {

    private var _binding: FragmentMineBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("LongLogTag", "ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mineViewModel =
            ViewModelProvider(this).get(MineViewModel::class.java)

        _binding = FragmentMineBinding.inflate(inflater, container, false)

        val root: View = binding.root
        Log.e("MineFragment加载中", "MineFragment加载中")


        //数据库
        val dbHelper =
            getActivity()?.let {
                MyDatabaseHelper(
                    it.getApplicationContext(),
                    "LogisticSystem.db",
                    1
                )
            }

        var db = dbHelper?.writableDatabase

//        清空
        /*if (db != null) {
            if (dbHelper != null) {
                dbHelper.deleteCurrentUser(db)
            }
        }*/

        var viewModel = ViewModelProvider(
            requireActivity(),
            NewInstanceFactory()
        ).get(SharedViewModel::class.java)

        var currentUser: String = ""

        viewModel.getCurrentUser().observe(viewLifecycleOwner) { item ->
            Log.e(
                "item.user_login ",
                item.user_login
            )
            if (dbHelper != null) {
                if (db != null) {
                    //若识别到item.user_login内容不为空，则在当前碎片渲染信息，否则则代表已经渲染过，不用处理
//                    if (item.user_login != "") {
                    if (item.user_login == "null") {
                        //获取当前用户
                        currentUser = dbHelper.getCurrentUser(db)
                        var userList = ArrayList<User>()
                        //获取当前用户信息
                        userList = dbHelper.getUser(db, currentUser)
                        //若数据库中查到非空则渲染，否则则代表已经渲染过，不用处理
                        if (userList.size != 0) {
                            //设置主页信息
                            binding.userLogin.text = userList[0].user_login
                            binding.userName.text = userList[0].user_name
                            binding.userDepartment.text = userList[0].user_department
                            binding.userTel.text = userList[0].user_tel
                            if (userList[0].user_login == "20194711") {
                                binding.avatar.setBackgroundResource(R.drawable.avatar)
                            } else if (userList[0].user_login == "20190205") {
                                binding.avatar.setBackgroundResource(R.drawable.avatar1)
                            } else {
                                //头像随机分配设置
                                var id = userList[0].user_id.toInt()
                                var avatar_index = id % 12
                                when (avatar_index) {
                                    0 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_1)
                                    }
                                    1 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_1)
                                    }
                                    2 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_2)
                                    }
                                    3 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_2)
                                    }
                                    4 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_3)
                                    }
                                    5 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_3)
                                    }
                                    6 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_4)
                                    }
                                    7 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_4)
                                    }
                                    8 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_5)
                                    }
                                    9 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_5)
                                    }
                                    10 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_6)
                                    }
                                    11 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_6)
                                    }

                                }
                            }
                            initThread()
                            Log.e("获取到的用户", userList.toString())
                        } else {
                            Log.e("userList.size == 0", "userList.size == 0")
                        }
                    } else if (item.user_login == "") {
                        //插入当前用户
                        dbHelper.insertCurrentUser(db, item.user_login)
                        //获取当前用户
                        currentUser = dbHelper.getCurrentUser(db)
                        var userList = ArrayList<User>()
                        //获取当前用户信息
                        userList = dbHelper.getUser(db, currentUser)
                        //若数据库中查到非空则渲染，否则则代表已经渲染过，不用处理
                        if (userList.size != 0) {
                            //设置主页信息
                            binding.userLogin.text = userList[0].user_login
                            binding.userName.text = userList[0].user_name
                            binding.userDepartment.text = userList[0].user_department
                            binding.userTel.text = userList[0].user_tel
                            if (userList[0].user_login == "20194711") {
                                binding.avatar.setBackgroundResource(R.drawable.avatar)
                            } else if (userList[0].user_login == "20190205") {
                                binding.avatar.setBackgroundResource(R.drawable.avatar1)
                            } else {
                                //头像随机分配设置
                                var id = userList[0].user_id.toInt()
                                var avatar_index = id % 12
                                when (avatar_index) {
                                    0 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_1)
                                    }
                                    1 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_1)
                                    }
                                    2 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_2)
                                    }
                                    3 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_2)
                                    }
                                    4 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_3)
                                    }
                                    5 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_3)
                                    }
                                    6 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_4)
                                    }
                                    7 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_4)
                                    }
                                    8 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_5)
                                    }
                                    9 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_5)
                                    }
                                    10 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_6)
                                    }
                                    11 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_6)
                                    }

                                }
                            }
                            initThread()
                            Log.e("获取到的用户", userList.toString())
                        } else {
                            Log.e("userList.size == 0", "userList.size == 0")
                        }

                    }else{
                        //插入当前用户
                        dbHelper.insertCurrentUser(db, item.user_login)
                        //获取当前用户
                        currentUser = dbHelper.getCurrentUser(db)
                        var userList = ArrayList<User>()
                        //获取当前用户信息
                        userList = dbHelper.getUser(db, currentUser)
                        //若数据库中查到非空则渲染，否则则代表已经渲染过，不用处理
                        if (userList.size != 0) {
                            //设置主页信息
                            binding.userLogin.text = userList[0].user_login
                            binding.userName.text = userList[0].user_name
                            binding.userDepartment.text = userList[0].user_department
                            binding.userTel.text = userList[0].user_tel
                            if (userList[0].user_login == "20194711") {
                                binding.avatar.setBackgroundResource(R.drawable.avatar)
                            } else if (userList[0].user_login == "20190205") {
                                binding.avatar.setBackgroundResource(R.drawable.avatar1)
                            } else {
                                //头像随机分配设置
                                var id = userList[0].user_id.toInt()
                                var avatar_index = id % 12
                                when (avatar_index) {
                                    0 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_1)
                                    }
                                    1 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_1)
                                    }
                                    2 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_2)
                                    }
                                    3 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_2)
                                    }
                                    4 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_3)
                                    }
                                    5 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_3)
                                    }
                                    6 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_4)
                                    }
                                    7 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_4)
                                    }
                                    8 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_5)
                                    }
                                    9 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_5)
                                    }
                                    10 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_boy_6)
                                    }
                                    11 -> {
                                        binding.avatar.setBackgroundResource(R.drawable.avatar_girl_6)
                                    }

                                }
                            }
                            initThread()
                            Log.e("获取到的用户", userList.toString())
                        } else {
                            Log.e("userList.size == 0", "userList.size == 0")
                        }
                    }
                }
            }
        }
        /**
         * 跳转完善资料
         */
        binding.completeInfo.setOnClickListener {
            val intent = Intent(activity, CompleteUser::class.java)
            intent.putExtra("user_login",binding.userLogin.text.toString())
            startActivity(intent)
        }
        /**
         * 跳转我的网站反馈界面
         */
        binding.webview.setOnClickListener {
            val intent = Intent(activity, WebViewActivity::class.java)
            startActivity(intent)
        }
        /**
         * 切换用户
         */
        binding.switchuser.setOnClickListener {
            val intent = Intent(activity, Login::class.java)
            startActivity(intent)
        }
        return root
    }

    /**
     * 获取当前时间
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = current.format(formatter)
        binding.time.setText(formatted.toString())
        return formatted.toString()
    }

    /**
     * 启动线程，每一秒更改一次时间
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initThread() {
        Thread(Runnable {
            try {
                val currentTime = "default"
                while (true) {
                    activity?.runOnUiThread {
                        getCurrentTime()
                    }
                    Thread.sleep(1000)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }).start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}