package com.example.flowpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flowpractice.R
import com.example.flowpractice.databinding.FragmentHomeBinding
import com.example.flowpractice.extension.fragmentBind

class HomeFragment : Fragment() {

    private val mBinding by fragmentBind(FragmentHomeBinding::inflate)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            btDownload.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_downloadFragment)
            }
            btRoom.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_roomFragment)
            }
        }
    }

}