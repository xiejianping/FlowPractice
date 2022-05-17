package com.example.flowpractice.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowpractice.data.GankRep
import kotlinx.coroutines.launch

class GirlViewModel : ViewModel() {

    fun getGirl() {
        viewModelScope.launch {
            GankRep.getGirl()
        }
    }
}