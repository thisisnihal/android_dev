package com.example.navtutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateTestViewModel : ViewModel() {

    private val _name = MutableLiveData<String> ()

    val name : LiveData<String> = _name // public final LiveData

    fun onNameChange(newName : String) {
        _name.value = newName
    }
}