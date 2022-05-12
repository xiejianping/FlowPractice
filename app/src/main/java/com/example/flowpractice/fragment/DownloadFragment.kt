package com.example.flowpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.PathUtils
import com.example.flowpractice.databinding.FragmentDownloadBinding
import com.example.flowpractice.download.DownloadManage
import com.example.flowpractice.download.DownloadStatus
import com.example.flowpractice.extension.fragmentBind
import java.io.File

class DownloadFragment : Fragment() {

    private val mBinding by fragmentBind(FragmentDownloadBinding::inflate)

    private val URL = "https://image.mew.fun/tos-cn-i-c226mjqywu/8c089749365e4518bc46a827262ccad1"
    private val file by lazy { File(PathUtils.getFilesPathExternalFirst() + "/download.jpg") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            DownloadManage.download(URL, file).collect {
                when (it) {
                    is DownloadStatus.Progress -> {
                        mBinding.progressBar.progress = it.progress
                        mBinding.tvProgress.text = "${it.progress}%"
                    }
                    is DownloadStatus.Done -> {
                        mBinding.progressBar.progress = 100
                        mBinding.tvProgress.text = "下载完成"
                    }
                    is DownloadStatus.Error -> {
                        mBinding.tvProgress.text = "下载失败"
                    }
                }
            }
        }
    }
}