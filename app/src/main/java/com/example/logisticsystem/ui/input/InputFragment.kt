package com.example.logisticsystem.ui.input

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.logisticsystem.MyDatabaseHelper
import com.example.logisticsystem.User
import com.example.logisticsystem.databinding.FragmentInputBinding
import com.example.logisticsystem.ui.SharedViewModel
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.citywheel.CityConfig
import com.lljjcoder.style.citylist.Toast.ToastUtils
import com.lljjcoder.style.citypickerview.CityPickerView


class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null

    private val binding get() = _binding!!

    /**
     * 城市选择器
     */
    var mPicker: CityPickerView = CityPickerView();


    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inputViewModel =
            ViewModelProvider(this).get(InputViewModel::class.java)

        _binding = FragmentInputBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.e("InputFragment加载中", "InputFragment加载中")
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

        /**
         * 提交表单
         */
        binding.submit.setOnClickListener {
            var src = binding.src.text.toString()
            var dest = binding.dest.text.toString()
            var senderName = binding.senderName.text.toString()

            var senderTel = binding.senderTel.text.toString()
            var accepterName = binding.accepterName.text.toString()
            var accepterTel = binding.accepterTel.text.toString()

            var itemName = binding.itemName.text.toString()
            var itemNum = binding.itemNum.text.toString()
            var payAlready = binding.payAlready.text.toString()
            var payDest = binding.payDest.text.toString()

            if (dbHelper != null) {
                if (db != null) {
                    dbHelper.insertData(
                        db, src, dest, senderName, senderTel, accepterName,
                        accepterTel, itemName, itemNum, payAlready, payDest
                    )
                }
            }
            val dbHelper =
                getActivity()?.let {
                    MyDatabaseHelper(
                        it.getApplicationContext(),
                        "LogisticSystem.db",
                        1
                    )
                }
            AlertDialog.Builder(getActivity()).apply {
                setTitle("通知")
                setMessage("录入运单成功")
                setCancelable(false)
                //点击back无法返回，默认是true
                setPositiveButton("确认") { dialog, which ->

                }
                setNegativeButton("返回") { dialog, which ->

                }
                show()
            }

        }


        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(activity);

        val cityConfig = CityConfig.Builder().build()
        mPicker.setConfig(cityConfig)


        /**
         * 收货地址点击监听
         */
        binding.dest.setOnClickListener {
            //监听选择点击事件及返回结果
            mPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
                override fun onSelected(
                    province: ProvinceBean?,
                    city: CityBean?,
                    district: DistrictBean?
                ) {
                    binding.dest.setText(province.toString() + city.toString() + district.toString())
                    binding.dest.setTextColor(Color.rgb(0, 0, 0))

                }

                override fun onCancel() {
                    ToastUtils.showLongToast(activity, "已取消")
                }
            })
            //显示
            mPicker.showCityPicker()
        }
        /**
         * 发货地址点击监听
         */
        binding.src.setOnClickListener {
            //监听选择点击事件及返回结果
            mPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
                override fun onSelected(
                    province: ProvinceBean?,
                    city: CityBean?,
                    district: DistrictBean?
                ) {
                    binding.src.setText(province.toString() + city.toString() + district.toString())
                    binding.src.setTextColor(
                        Color.rgb(
                            0, 0, 0
                        )
                    )
                }

                override fun onCancel() {
                    ToastUtils.showLongToast(activity, "已取消")
                }
            })
            //显示
            mPicker.showCityPicker()
        }

        /**
         * 获取mainActivity传来的登录信息
         */

        //用户账号密码
        var user_login_g: String = ""
        var currentUser_g: String = ""
        val bundle = arguments

        if (bundle != null) {
            var user_login = bundle.getString("user_login").toString()
            var user_passwd = bundle.getString("user_passwd").toString()
            var currentUser = bundle.getString("currentUser").toString()
            user_login_g = user_login
            currentUser_g = currentUser
            Log.e("InputFragment获取到的账号密码", "$user_login---$user_passwd")
            Log.e("InputFragment获取到的currentUser", "$currentUser")
        }
        /**
         * 设置用户user_login给其他Fragment，以此为凭证
         */
        var viewModel = ViewModelProvider(
            requireActivity(),
            NewInstanceFactory()
        ).get(SharedViewModel::class.java)

        val currentUser = User("", "", "", "", "", "")
        if (user_login_g != "") {
            currentUser.user_login = user_login_g
            //传递参数
            viewModel.setCurrentUser(currentUser)
        } else {
            currentUser.user_login = currentUser_g
            //传递参数
            viewModel.setCurrentUser(currentUser)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}