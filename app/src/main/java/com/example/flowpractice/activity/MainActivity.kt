package com.example.flowpractice.activity

import android.os.*
import android.os.Binder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.example.flowpractice.databinding.ActivityMainBinding
import com.example.flowpractice.extension.activityBind
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.yield
import java.io.FileDescriptor
import java.lang.ref.PhantomReference
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

class MainActivity : AppCompatActivity() {

    private val mBinding by activityBind(ActivityMainBinding::inflate)

    private val TAG = "MainActivity"
    var obj: Any? = Any()
    val referenceQueue = ReferenceQueue<Any?>()
    val weakReference = WeakReference(obj, referenceQueue)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        this::mBinding.isAccessible=true
//        LogUtils.d(TAG, "before ${(this::mBinding.getDelegate() as Lazy<*>).isInitialized()}")

        setContentView(mBinding.root)
//        LogUtils.d(TAG, "after ${(this::mBinding.getDelegate() as Lazy<*>).isInitialized()}")
        BarUtils.setStatusBarLightMode(this, true)

        window.decorView.postDelayed({
            mBinding.marqueeView.startWithList(
                mutableListOf(
                    "萨达萨达萨达萨达",
                    "可怜见看来发热的身份和人吧",
                    "赛哦微软覅的结果那就下次u和"
                )
            )
        }, 3000)
        mBinding.marqueeView.setOnItemClickListener { position, textView ->
//            if (mBinding.marqueeView.isFlipping) {
            if (mBinding.marqueeView.isPause() == true) {
                mBinding.marqueeView.resume()
            } else {
                mBinding.marqueeView.pause()
            }
//            } else {
//                mBinding.marqueeView.startFlipping()
//            }
        }
        printLog()

    }

    private fun printLog(): Unit {
        lifecycleScope.launchWhenCreated {
            delay(11)
            val sequence = sequence {

                yield(1)

                yield(2)
                yieldAll(listOf(3, 4, 5, 6))
            }
            for (i in sequence) {
                Log.d(TAG, "sequence :$i")
            }
        }
//        val testIterator = testIterator {
//
//        }
//        for (i in testIterator) {
//
//        }


    }
}


suspend fun test() {

}

fun testIterator(iterator: () -> Iterator<String>) = object : TestIterator {
    override fun iterator(): Iterator<String> {
        return iterator()
    }

}


interface TestIterator {
    operator fun iterator(): Iterator<String>
}
