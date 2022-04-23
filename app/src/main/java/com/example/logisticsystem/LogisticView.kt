package com.example.logisticsystem


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
//import kotlinx.android.parcel.Parcelize
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logisticsystem.databinding.ActivityLogisticViewBinding

class LogisticView : AppCompatActivity() {
    private lateinit var binding: ActivityLogisticViewBinding

    //运单数据类
//    @SuppressLint("ParcelCreator")
//    @Parcelize
    data class LogisticItem(
        val id: String, val src: String, val dest: String, val senderName: String,
        val senderTel: String, val accepterName: String, val accepterTel: String,
        val itemName: String, val itemNum: String, val payAlready: String, val payDest: String
    )

    //书籍列表
    lateinit var logisticList: ArrayList<LogisticItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogisticViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dbHelper = MyDatabaseHelper(this, "LogisticSystem.db", 1)
        var db = dbHelper.writableDatabase
        logisticList = dbHelper.selectData(db)


        val layoutManager = LinearLayoutManager(this) //线性布局布局管理器
        binding.recycleView.layoutManager = layoutManager
        val adapter = LogisticAdapter(logisticList)
        binding.recycleView.adapter = adapter

    }
}