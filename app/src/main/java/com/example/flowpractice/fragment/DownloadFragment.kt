package com.example.flowpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flowpractice.databinding.FragmentDownloadBinding
import com.example.flowpractice.extension.fragmentBind

class DownloadFragment : Fragment() {

    private val mBinding by fragmentBind(FragmentDownloadBinding::inflate)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }
}