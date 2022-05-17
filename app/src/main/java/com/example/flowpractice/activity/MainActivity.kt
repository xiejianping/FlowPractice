package com.example.flowpractice.activity

import android.os.*
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService
import com.blankj.utilcode.util.BarUtils
import com.example.flowpractice.databinding.ActivityMainBinding
import com.example.flowpractice.extension.activityBind
import java.io.FileDescriptor

class MainActivity : AppCompatActivity() {

    private val mBinding by activityBind(ActivityMainBinding::inflate)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        BarUtils.setStatusBarLightMode(this, true)
    }
}


