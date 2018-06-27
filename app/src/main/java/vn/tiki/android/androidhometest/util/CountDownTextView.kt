package vn.tiki.android.androidhometest.util

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.util.AttributeSet
import kotlinx.android.synthetic.main.layout_count_down_text_view.view.*
import vn.tiki.android.androidhometest.R
import java.util.*

class CountDownTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_count_down_text_view, this, true)
    }

    private var onCountDownEnd : OnCountDownEnd? = null
    private var originalTime: Long? = null
    private var isStopRequest = true

    fun setRefTime(millisecond: Long, onCountDownEnd: OnCountDownEnd) {
        this.onCountDownEnd = onCountDownEnd
        this.originalTime = millisecond

        val now = Date()
        val timeLeft = originalTime!! - now.time
        tv_text.text = getDownTime(timeLeft / 1000)

        startCountDown()
    }

    private fun handleCountDown() {
        val now = Date()
        val timeLeft = originalTime!! - now.time

        if (timeLeft < 1000) {
            isStopRequest = true
            onCountDownEnd?.onCountDownEnd();
        }
        else {
            tv_text.text = getDownTime(timeLeft / 1000)
        }
    }

    private fun getDownTime(second: Long): String {
        val days = second / 60 / 60 / 24
        val hours = second / 60 / 60
        val minutes = second / 60
        val seconds = second % 60

        return String.format("%d days %02d:%02d:%02d", days, hours, minutes, seconds)
    }

    private fun startCountDown() {
        if (isStopRequest == false) {
            return
        }
        isStopRequest = false
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (!isStopRequest) {
                    handleCountDown()
                    handler.postDelayed(this, 1000)
                }
            }
        }, 0)
    }

    private fun stopCountDown() {
        isStopRequest = true
    }
}