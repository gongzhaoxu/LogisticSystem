package com.example.logisticsystem.ui

//import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.logisticsystem.User

class SharedViewModel :ViewModel(){
    private val currentUser = MutableLiveData<User>()
    public fun setCurrentUser(user:User){
        currentUser.setValue(user)
    }
//    val text: LiveData<String> = _text
    public fun getCurrentUser():LiveData<User>{
        return currentUser
    }
}