package com.example.logisticsystem.ui.query

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QueryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is query Fragment"
    }
    val text: LiveData<String> = _text
}