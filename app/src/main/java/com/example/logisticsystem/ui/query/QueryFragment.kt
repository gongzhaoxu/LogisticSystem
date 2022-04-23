package com.example.logisticsystem.ui.query

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.logisticsystem.LogisticView
import com.example.logisticsystem.MyDatabaseHelper
import com.example.logisticsystem.databinding.FragmentQueryBinding

class QueryFragment : Fragment() {

    private var _binding: FragmentQueryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val queryViewModel =
            ViewModelProvider(this).get(QueryViewModel::class.java)

        _binding = FragmentQueryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //数据库
        val dbHelper =
            getActivity()?.let { MyDatabaseHelper(it.getApplicationContext(), "LogisticSystem.db", 1) }

        var db = dbHelper?.writableDatabase


        binding.local.setOnClickListener{
            val intent=Intent(activity, LogisticView::class.java)
            startActivity(intent)
        }

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