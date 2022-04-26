package com.example.logisticsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogisticAdapter(val logisticList: List<LogisticView.LogisticItem>) :
    RecyclerView.Adapter<LogisticAdapter.ViewHolder>() {
    //自定义嵌套内部类 ViewHolder 来减少 findViewById() 的使用， 继承RecyclerView的ViewHolder
    //通过属性名称的id获取对应的视图，以便后续操作
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.id )
        val src: TextView = view.findViewById(R.id.src )
        val dest: TextView = view.findViewById(R.id.dest )
        val senderName: TextView = view.findViewById(R.id.senderName )

        val senderTel : TextView = view.findViewById(R.id.senderTel  )
        val accepterName : TextView = view.findViewById(R.id.accepterName  )
        val accepterTel : TextView = view.findViewById(R.id.accepterTel  )
        val itemName : TextView = view.findViewById(R.id.itemName  )

        val itemNum : TextView = view.findViewById(R.id.itemNum  )
        val payAlready : TextView = view.findViewById(R.id.payAlready  )
        val payDest: TextView = view.findViewById(R.id.payDest )

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //第一个参数为单个书对应的布局文件，第二个参数为RecyclerView要显示的位置
        //第三个参数设置为false效果为你在xml中设置为什么具体显示就为什么
        val view = LayoutInflater.from(parent.context).inflate(R.layout.logistic_view_item, parent, false)
        return ViewHolder(view)
    }       //加载布局

    //设置初次加载、滑动时的布局
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val logisticItem = logisticList[position]
        holder.id.text = "No:"+logisticItem.id
        holder.src.text ="发站:"+ logisticItem.src
        holder.dest.text = "到站:"+logisticItem.dest
        holder.senderName.text = "发货人:"+logisticItem.senderName

        holder.senderTel.text = "发货电话:"+logisticItem.senderTel
        holder.accepterName.text = "收货人:"+logisticItem.accepterName
        holder.accepterTel.text ="收货电话:"+ logisticItem.accepterTel
        holder.itemName.text = "货物名称:"+logisticItem.itemName

        holder.itemNum.text = "货物数量:"+logisticItem.itemNum+"件"
        holder.payAlready.text = "已付运费:"+logisticItem.payAlready+"元"
        holder.payDest.text ="到付运费:"+ logisticItem.payDest+"元"
    }

    //获取列表中的项目个数，将其定义为书本数组的个数
    override fun getItemCount() = logisticList.size
}