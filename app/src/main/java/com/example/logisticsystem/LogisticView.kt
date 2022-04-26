package com.example.logisticsystem


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.TextView
//import kotlinx.android.parcel.Parcelize
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logisticsystem.databinding.ActivityLogisticViewBinding
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.lang.Thread.sleep
import kotlin.concurrent.thread

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


    //运单列表
    var logisticList = ArrayList<LogisticItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogisticViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val layoutManager = LinearLayoutManager(this) //线性布局布局管理器
        binding.recycleView.layoutManager = layoutManager

        /**
         * 数据库
         */
        val dbHelper = MyDatabaseHelper(this, "LogisticSystem.db", 1)
        var db = dbHelper.writableDatabase

        /**
         * 获取显示哪个的信息
         */
        val intent = getIntent()
        val bundle = intent.extras
        val mode = bundle?.get("mode").toString()

        Log.e("mode==", mode)
        when (mode) {
            "local" -> {
                logisticList = dbHelper.selectData(db)

                val adapter = LogisticAdapter(logisticList)
                binding.recycleView.adapter = adapter
            }
            "xml" -> {
                sendRequestWithOkHttpXML()
                sleep(1000)
                val adapter = LogisticAdapter(logisticList)
                binding.recycleView.adapter = adapter
                Log.e("布局加载", "布局加载")
            }
            "json" -> {
                sendRequestWithOkHttp()
                sleep(1000)
                val adapter = LogisticAdapter(logisticList)
                binding.recycleView.adapter = adapter
            }
        }


        /**
         * 点击返回回到主页
         */
        binding.ret.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    /**
     * xml请求
     */
    @SuppressLint("LongLogTag")
    private fun sendRequestWithOkHttpXML() {
        var myThread = object : Thread() {
            override fun run() {
                try {
                    Log.e("sendRequestWithOkHttpXML", "被调用")
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("http://60.12.122.142:6080/simulated-Waybills-db.xml")
                        .build()
                    val response = client.newCall(request).execute()
                    Log.e(
                        "LogisticView",
                        response.protocol()
                            .toString() + " " + response.code() + " " + response.message()
                    )
                    val responseData = response.body().string()
                    parseXML(responseData)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    /**
     * json请求
     */
    private fun sendRequestWithOkHttp() {
        thread {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://60.12.122.142:6080/simulated-Waybills-db.json")
                .build()
            val response = client.newCall(request).execute()
            val responseData = response.body()?.string()

            if (responseData != null) {
                parseJSON(responseData)
            }

        }
    }

    /**
     * 解析XML
     */
    private fun parseXML(responseData: String) {
        try {

            Log.e("parseXML","被调用")

            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser = factory.newPullParser()
            xmlPullParser.setInput(StringReader(responseData))
            var eventType = xmlPullParser.eventType


            var waybillNo = ""
            var consignor = ""
            var consignorPhoneNumber = ""
            var consignee = ""
            var consigneePhoneNumber = ""
            var transportationDepartureStation = ""
            var transportationArrivalStation = ""
            var goodsDistributionAddress = ""
            var goodsName = ""
            var numberOfPackages = ""
            var freightPaidByTheReceivingParty = ""
            var freightPaidByConsignor = ""

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val CurrentType = xmlPullParser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (CurrentType) {
                            "waybillNo" -> waybillNo = xmlPullParser.nextText()
                            "consignor" -> consignor = xmlPullParser.nextText()
                            "consignorPhoneNumber" -> consignorPhoneNumber =
                                xmlPullParser.nextText()
                            "consignee" -> consignee = xmlPullParser.nextText()
                            "consigneePhoneNumber" -> consigneePhoneNumber =
                                xmlPullParser.nextText()
                            "transportationDepartureStation" -> transportationDepartureStation =
                                xmlPullParser.nextText()
                            "transportationArrivalStation" -> transportationArrivalStation =
                                xmlPullParser.nextText()
                            "goodsDistributionAddress" -> goodsDistributionAddress =
                                xmlPullParser.nextText()
                            "goodsName" -> goodsName = xmlPullParser.nextText()
                            "numberOfPackages" -> numberOfPackages = xmlPullParser.nextText()
                            "freightPaidByTheReceivingParty" -> freightPaidByTheReceivingParty =
                                xmlPullParser.nextText()
                            "freightPaidByConsignor" -> freightPaidByConsignor =
                                xmlPullParser.nextText()
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if ("waybillRecord" == CurrentType) {
                            //创建当前订单
                            var logisticItem = LogisticItem(
                                waybillNo,
                                transportationDepartureStation,
                                transportationArrivalStation,
                                consignor,
                                consignorPhoneNumber,
                                consignee,
                                consigneePhoneNumber,
                                goodsName,
                                numberOfPackages,
                                freightPaidByConsignor,
                                freightPaidByTheReceivingParty
                            )
                            //存入logisticList
                            logisticList.add(logisticItem)
                        }
                    }
                }
                eventType = xmlPullParser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 解析JSON
     */
    private fun parseJSON(responseData: String) {
        try {
            val jsonarray = JSONObject(responseData)
            val json_data_list = jsonarray.getJSONArray("waybillRecord")
            for (i in 0 until json_data_list.length()) {

                val jsonObject = json_data_list.getJSONObject(i)

                val waybillNo_ = jsonObject.getString("waybillNo")

                val consignor_ = jsonObject.getString("consignor")

                val consignorPhoneNumber_ = jsonObject.getString("consignorPhoneNumber")

                val consignee_ = jsonObject.getString("consignee")

                val consigneePhoneNumber_ = jsonObject.getString("consigneePhoneNumber")

                val transportationDepartureStation_ =
                    jsonObject.getString("transportationDepartureStation")

                val transportationArrivalStation_ =
                    jsonObject.getString("transportationArrivalStation")

                val goodsDistributionAddress_ = jsonObject.getString("goodsDistributionAddress")

                val goodsName_ = jsonObject.getString("goodsName")

                val numberOfPackages_ = jsonObject.getString("numberOfPackages")

                val freightPaidByTheReceivingParty_ =
                    jsonObject.getString("freightPaidByTheReceivingParty")

                val freightPaidByConsignor_ = jsonObject.getString("freightPaidByConsignor")

                //创建当前订单
                var logisticItem = LogisticItem(
                    waybillNo_,
                    transportationDepartureStation_,
                    transportationArrivalStation_,
                    consignor_,
                    consignorPhoneNumber_,
                    consignee_,
                    consigneePhoneNumber_,
                    goodsName_,
                    numberOfPackages_,
                    freightPaidByConsignor_,
                    freightPaidByTheReceivingParty_
                )
                //存入logisticList
                logisticList.add(logisticItem)

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}




