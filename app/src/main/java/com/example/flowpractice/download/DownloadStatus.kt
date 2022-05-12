package com.example.flowpractice.download

import java.io.File

sealed class DownloadStatus {

    data class Progress(val progress: Int) : DownloadStatus()
    data class Error(val throwable: Throwable) : DownloadStatus()
    data class Done(val file: File) : DownloadStatus()
}