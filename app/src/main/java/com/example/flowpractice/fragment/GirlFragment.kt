package com.example.flowpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.flowpractice.databinding.FragmentGirlBinding
import com.example.flowpractice.extension.fragmentBind
import com.example.flowpractice.vm.GirlViewModel

class GirlFragment : Fragment() {

    private val mBinding by fragmentBind(FragmentGirlBinding::inflate)

    private val girlViewModel: GirlViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        girlViewModel.getGirl()
    }
}