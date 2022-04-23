package com.example.logisticsystem.ui.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        }


        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(activity);

        val cityConfig = CityConfig.Builder().build()
        mPicker.setConfig(cityConfig)


//监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
            override fun onSelected(
                province: ProvinceBean?,
                city: CityBean?,
                district: DistrictBean?
            ) {

                //省份province
                //城市city
                //地区district
            }

            override fun onCancel() {
                ToastUtils.showLongToast(activity, "已取消")
            }
        })

        //显示
        mPicker.showCityPicker()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}