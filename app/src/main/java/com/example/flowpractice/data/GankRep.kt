package com.example.flowpractice.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object GankRep {

    private val gankApi by lazy { GankService.gankApi }
    fun getGirl() = flow<String> {
        gankApi.getGirl()
    }.flowOn(Dispatchers.IO)
        .catch {
            it.printStackTrace()
        }
}