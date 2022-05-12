package com.example.flowpractice.download

import java.io.File

sealed class DownloadStatus {
    object Non : DownloadStatus()
    data class Progress(val progress: Int) : DownloadStatus()
    data class Error(val excption: Exception) : DownloadStatus()
    data class Done(val file: File) : DownloadStatus()
}