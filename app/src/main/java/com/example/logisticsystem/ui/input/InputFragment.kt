package com.example.logisticsystem.ui.input

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.logisticsystem.MyDatabaseHelper
import com.example.logisticsystem.databinding.FragmentInputBinding
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inputViewModel =
            ViewModelProvider(this).get(InputViewModel::class.java)

        _binding = FragmentInputBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

        fun Context.hideKeyboard(view: View) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            Toast.makeText(activity, "hide调用", Toast.LENGTH_SHORT).show()
        }
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
                    binding.dest.setTextColor(Color.rgb(0,0,0))

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
                    binding.src.setTextColor(Color.rgb(
                        0,0,0
                    ))
                }

                override fun onCancel() {
                    ToastUtils.showLongToast(activity, "已取消")
                }
            })
            //显示
            mPicker.showCityPicker()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}