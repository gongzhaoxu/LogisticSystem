package com.example.logisticsystem.ui.mine

import android.R
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.logisticsystem.MyDatabaseHelper
import com.example.logisticsystem.User
import com.example.logisticsystem.databinding.FragmentMineBinding
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlin.concurrent.thread


class MineFragment : Fragment() {

    private var _binding: FragmentMineBinding? = null
    private val binding get() = _binding!!

    private var num = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mineViewModel =
            ViewModelProvider(this).get(MineViewModel::class.java)

        _binding = FragmentMineBinding.inflate(inflater, container, false)

        val root: View = binding.root
        /**
         * 获取mainActivity传来的登录信息
         */
        //用户账号密码
        var user_login_g: String = ""
        val bundle = arguments

        if (bundle != null) {
            var user_login = bundle.getString("user_login").toString()
            var user_passwd = bundle.getString("user_passwd").toString()
            user_login_g = user_login
            Log.e("MineFragment获取到的账号密码", "$user_login---$user_passwd")
        }
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
        var userList = ArrayList<User>()
        if (dbHelper != null) {
            if (db != null) {
                userList = dbHelper.getUser(db, user_login_g)
            }
        }
        Log.e("获取到的用户", userList.toString())
//        binding.userLogin.text = userList.toString()
        binding.userLogin.text = "1123"
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {

        super.onViewStateRestored(savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}