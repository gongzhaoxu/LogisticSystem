package com.example.logisticsystem.ui.query

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.logisticsystem.LogisticView
import com.example.logisticsystem.MyDatabaseHelper
import com.example.logisticsystem.databinding.FragmentQueryBinding
import com.example.logisticsystem.ui.SharedViewModel

class QueryFragment : Fragment() {

    private var _binding: FragmentQueryBinding? = null
    private val binding get() = _binding!!

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
            getActivity()?.let { MyDatabaseHelper(it.getApplicationContext(), "LogisticSystem.db", 1) }

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
        binding.local.setOnClickListener{
            val intent=Intent(activity, LogisticView::class.java)
            startActivity(intent)
        }
        /**
         * 清空本地运单
         */
        binding.delete.setOnClickListener{
            if (dbHelper != null) {
                if (db != null) {
                    dbHelper.delete(db)
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}