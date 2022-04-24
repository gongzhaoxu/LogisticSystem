package com.example.logisticsystem.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.logisticsystem.MyDatabaseHelper
import com.example.logisticsystem.User

class MineViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is mine Fragment"
    }

    val text: LiveData<String> = _text
}