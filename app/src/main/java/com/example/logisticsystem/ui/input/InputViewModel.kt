package com.example.logisticsystem.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is input Fragment"
    }
    val text: LiveData<String> = _text
}