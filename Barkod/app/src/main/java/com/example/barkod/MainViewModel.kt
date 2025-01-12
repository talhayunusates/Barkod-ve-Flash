package com.example.barkod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    fun setResult(value: String) {
        _result.value = value
    }
}