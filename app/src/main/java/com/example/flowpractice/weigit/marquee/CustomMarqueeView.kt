package com.example.flowpractice.weigit.marquee

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnRepeat
import com.blankj.utilcode.util.LogUtils

class CustomMarqueeView : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {


        addView(showChild)

        addView(lastChild)

        addView(lastChild1)
    }

    private val showChild by lazy {
        TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }
    private val lastChild by lazy {
        TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }
 private val lastChild1 by lazy {
        TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }

    private var mWidth = 0f
    private var mHeight = 0f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mWidth > 0) return
        mWidth = w.takeIf { it > 0 }?.toFloat() ?: return

    }

    private var txts: List<String> = listOf()
    fun startWithList(messages: List<String>) {
//        removeAllViews()
        if (messages.isNullOrEmpty()) {
            return
        }
        txts = messages
//        LogUtils.e("sa ${layoutParams.width} ${layoutParams.height}")
//        val ss =(mWidth * childCount).toInt()
//        val linearLayout = LinearLayout(context)
//
//        messages.forEachIndexed { index: Int, s: String ->
//            val textView = TextView(context)
//            textView.layoutParams =
//                LayoutParams(mWidth.toInt(), LayoutParams.WRAP_CONTENT)
//            textView.text = s
//
//            textView.setOnClickListener {
//                onItemClickListener?.invoke(index, textView)
//            }
//            linearLayout.addView(textView)
//        }
//        linearLayout.translationX = mWidth
////        handler.postDelayed({startAni()},1000)
//        addView(linearLayout,ss,200)
        startAni()
    }

    private var ani: ObjectAnimator? = null
    private val singDuration = 6000L

    val animatorSet = AnimatorSet()
    private var currentIndex = 0
    private fun startAni() {
        showChild.text = txts[currentIndex % txts.size]
        lastChild.text = txts[(currentIndex + 1) % txts.size]

        val currentAniIn = ObjectAnimator.ofFloat( mWidth, -mWidth)
        currentAniIn.repeatCount = -1
        currentAniIn.duration = 2 * singDuration
        currentAniIn.interpolator = LinearInterpolator()

        currentAniIn.addUpdateListener {
            val animatorValue = it.animatedValue as Float
            showChild.translationX = animatorValue
            lastChild.translationX = animatorValue + mWidth
        }
        currentAniIn.start()

//        val currentAniOut = ObjectAnimator.ofFloat(showChild, "translationX", 0f, -mWidth)
//        currentAniOut.repeatCount = -1
//        currentAniOut.duration = singDuration
//        currentAniOut.interpolator = LinearInterpolator()


//        val ani1 = ObjectAnimator.ofFloat(lastChild, "translationX", mWidth, -mWidth)
//        ani1.repeatCount = -1
//        ani1.duration = 2 * singDuration
//        ani1.interpolator = LinearInterpolator()
//
//        animatorSet.play(ani1).after(singDuration).after(currentAniIn)
//
//
//        animatorSet.start()
    }

    fun pause() {
        ani?.pause()
    }

    fun isPause(): Boolean? {
        return ani?.isPaused
    }

    fun resume() {
        ani?.resume()
    }

    private var onItemClickListener: ((Int, TextView) -> Unit)? = null
    fun setOnItemClickListener(onItemClickListener: ((Int, TextView) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }
}