package com.example.logisticsystem.ui.query

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.logisticsystem.LogisticView
import com.example.logisticsystem.MyDatabaseHelper
import com.example.logisticsystem.databinding.FragmentQueryBinding
import com.example.logisticsystem.ui.SharedViewModel
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QueryFragment : Fragment() {

    private var _binding: FragmentQueryBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val queryViewModel =
            ViewModelProvider(this).get(QueryViewModel::class.java)

        _binding = FragmentQueryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.e("QueryFragment加载中", "QueryFragment加载中")

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


        var viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        ).get(SharedViewModel::class.java)


        var currentUser: String = ""

        viewModel.getCurrentUser().observe(viewLifecycleOwner) { item ->
            Log.e(
                "InputFragment传过来到QueryFragment的数据 ",
                item.user_login
            )
            if (dbHelper != null) {
                if (db != null) {
                    if (item.user_login != "") {
                        dbHelper.insertCurrentUser(db, item.user_login)
                    }
                }
            }
        }


        /**
         * 本地运单
         */
        binding.local.setOnClickListener {
            val intent = Intent(activity, LogisticView::class.java)
            intent.putExtra("mode", "local")
            startActivity(intent)
        }
        /**
         * xml运单
         */
        Log.e("visibility", "visibility改变成INVISIBLE")


        binding.remoteXml.setOnClickListener {

            //让progressBar可见
            binding.progress.visibility = View.VISIBLE

            val intent = Intent(activity, LogisticView::class.java)
            intent.putExtra("mode", "xml")
            startActivity(intent)
        }
        /**
         * json运单
         */
        binding.remoteJson.setOnClickListener {
            //让progressBar可见
            binding.progress.visibility = View.VISIBLE
            val intent = Intent(activity, LogisticView::class.java)
            intent.putExtra("mode", "json")
            startActivity(intent)
        }


        /**
         * 清空本地运单
         */
        binding.delete.setOnClickListener {
            AlertDialog.Builder(getActivity()).apply {
                setTitle("通知")
                setMessage("您确认要清空本地运单吗？")
                setCancelable(false)
                //点击back无法返回，默认是true
                setPositiveButton("确认") { dialog, which ->
                    if (dbHelper != null) {
                        if (db != null) {
                            dbHelper.delete(db)
                        }
                    }
                    Toast.makeText(activity, "清空成功", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("返回") { dialog, which ->
                    Toast.makeText(activity, "操作取消", Toast.LENGTH_SHORT).show()
                }
                show()
            }

        }
        return root
    }

    /**
     * onStart时让progressbar不可见
     */
    override fun onStart() {
        super.onStart()
        binding.progress.visibility = View.INVISIBLE
        Log.e("queryFragment被onStart","queryFragment被onStart")

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}