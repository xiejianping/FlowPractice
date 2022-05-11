package com.example.flowpractice.extension

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.example.flowpractice.BuildConfig
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified V : ViewBinding> activityBind(crossinline provider: (LayoutInflater) -> V): ActivityViewBind<Activity, V> {
    val v = ActivityViewBind { activity: Activity ->
        provider.invoke(activity.layoutInflater)
    }
    return v
}


class ActivityViewBind<A : Activity, V : ViewBinding>(private val viewBind: (A) -> V) :
    ReadOnlyProperty<A, V> {
    private var mBinding: V? = null
    override fun getValue(thisRef: A, property: KProperty<*>): V {
        mBinding?.let { return it }
        mBinding = viewBind.invoke(thisRef)
        return mBinding!!
    }

}

inline fun <reified V : ViewBinding> fragmentBind(crossinline provider: (LayoutInflater) -> V): FragmentViewBind<Fragment, V> {
    return FragmentViewBind { f ->
        provider.invoke(f.layoutInflater)
    }
}

class FragmentViewBind<F : Fragment, V : ViewBinding>(private val viewBind: (F) -> V) :
    ReadOnlyProperty<F, V> {
    private val TAG = "FragmentViewBind"
    private var mBinding: V? = null
    override fun getValue(thisRef: F, property: KProperty<*>): V {

        mBinding?.let { return it }

        if (thisRef.lifecycle.currentState == Lifecycle.State.DESTROYED) {
//            throw IllegalStateException("don't access viewBinding after destroyed ")
            if (BuildConfig.DEBUG) {
                Log.e(
                    TAG,
                    "" +
                            "---------------------------------------------------------------------------------\n" +
                            "access viewBinding after destroyed ,the ${thisRef.javaClass.simpleName}may be leaked\n" +
                            "--------------------------------------------------------------------------------- "
                )
            }
        }
        thisRef.lifecycle.addObserver(observer)
        return viewBind.invoke(thisRef).also {
            if (BuildConfig.DEBUG) {
                Log.d(
                    TAG,
                    "${thisRef.javaClass.simpleName} -> ${it.javaClass.simpleName}  initialization"
                )
            }
            mBinding = it
        }

    }

    private val observer = object : DefaultLifecycleObserver {
//        private val handler = Handler(Looper.getMainLooper())

        @MainThread
        override fun onDestroy(owner: LifecycleOwner) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "${owner.javaClass.simpleName} is exe onDestroy")
            }
            owner.lifecycle.removeObserver(this)
//            handler.post {
            mBinding = null
//            }
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "${owner.javaClass.simpleName} mBinding is release")
            }
        }
    }
}