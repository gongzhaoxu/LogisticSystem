package com.example.logisticsystem.ui.mine

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.logisticsystem.MyDatabaseHelper
import com.example.logisticsystem.R
import com.example.logisticsystem.User
import com.example.logisticsystem.databinding.FragmentMineBinding
import com.example.logisticsystem.ui.SharedViewModel


class MineFragment : Fragment() {

    private var _binding: FragmentMineBinding? = null
    private val binding get() = _binding!!

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
                "InputFragment传过来到MineFragment的数据 ",
                item.user_login
            )
            if (dbHelper != null) {
                if (db != null) {
                    if (item.user_login != "") {
                        //插入当前用户
                        dbHelper.insertCurrentUser(db, item.user_login)
                        //获取当前用户
                        currentUser = dbHelper.getCurrentUser(db)
                        var userList = ArrayList<User>()
                        //获取当前用户信息
                        userList = dbHelper.getUser(db, currentUser)
                        //设置主页信息
                        binding.userLogin.text = userList[0].user_login
                        binding.userName.text= userList[0].user_name
                        binding.userDepartment.text= userList[0].user_department
                        binding.userTel.text= userList[0].user_tel
                        if(userList[0].user_login=="20194711")
                        {
                            binding.avatar.setBackgroundResource(R.drawable.avatar)
                        }else if (userList[0].user_login=="20190205"){
                            binding.avatar.setBackgroundResource(R.drawable.avatar1)
                        }else{
                            //头像随机分配设置
                            var id=userList[0].user_id.toInt()
                            var avatar_index=id%12
                            when(avatar_index){
                                0->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_boy_1)
                                }
                                1->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_girl_1)
                                }
                                2->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_boy_2)
                                }
                                3->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_girl_2)
                                }
                                4->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_boy_3)
                                }
                                5->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_girl_3)
                                }
                                6->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_boy_4)
                                }
                                7->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_girl_4)
                                }
                                8->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_boy_5)
                                }
                                9->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_girl_5)
                                }
                                10->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_boy_6)
                                }
                                11->{
                                    binding.avatar.setBackgroundResource(R.drawable.avatar_girl_6)
                                }

                            }
                        }



                        Log.e("获取到的用户", userList.toString())
                    }
                }
            }
        }



        return root
    }


    @SuppressLint("LongLogTag")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var viewModel = ViewModelProvider(
            requireActivity(),
            NewInstanceFactory()
        ).get(SharedViewModel::class.java)

        viewModel.getCurrentUser().observe(viewLifecycleOwner) { item ->
            Log.e(
                "onSaveInstanceState保存数据为 ",
                item.user_login

            )
            outState.putString("user_login", item.user_login)
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onPause() {
        super.onPause()
        val mineViewModel =
            ViewModelProvider(this).get(MineViewModel::class.java)
        Log.e("MineFragment销毁中", "MineFragment销毁中")
    }

    override fun onDestroyView() {
        super.onDestroyView()


        _binding = null
    }
}