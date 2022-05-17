package com.example.flowpractice.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DbViewModel(application: Application) : AndroidViewModel(application) {
    private val db by lazy { AppDatabase.getInstance(getApplication<Application?>().applicationContext) }
    fun insert(user: User) {
        viewModelScope.launch {
            db.userDao().insert(user)
        }
    }

    fun findAll() = db.userDao().getAll().catch { it.printStackTrace() }.flowOn(Dispatchers.IO)

}