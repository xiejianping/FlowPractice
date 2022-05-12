package com.example.flowpractice.download

import com.example.flowpractice.extension.listenerCopyTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

object DownloadManage {


    fun download(url: String, file: File?) = flow {
        val okHttpClient = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val execute = okHttpClient.newCall(request).execute()
        if (execute.isSuccessful) {
            val body = execute.body()
            val totalLength = body!!.contentLength()
            val byteStream = body.byteStream()
            val outputStream = file!!.outputStream()
            outputStream.use { out ->
                byteStream.listenerCopyTo(out) {
                    delay(100)
                    val progress = (it / totalLength.toFloat()) * 100
                    emit(DownloadStatus.Progress(progress.toInt()))
                }
            }
            emit(DownloadStatus.Done(file))
        }
    }.catch {
        file?.delete()
        it.printStackTrace()
        emit(DownloadStatus.Error(it))
    }.flowOn(Dispatchers.IO)
}

