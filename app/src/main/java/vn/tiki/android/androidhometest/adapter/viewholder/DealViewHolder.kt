package vn.tiki.android.androidhometest.adapter.viewholder

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.layout_deal_item.view.*
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.util.RemoveCallback
import java.util.*

class DealViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val ivThumbnail = view.iv_thumbnail
    private val tvName = view.tv_name
    private val tvPrice = view.tv_price
    private val tvCountDown = view.tv_countdown

    private var itemIndex : Int? = null
    private var removeCallback : RemoveCallback? = null

    fun setData (data: Deal, context: Context) {
        setName(data.productName)
        setPrice(data.productPrice)
        setImage(data.productThumbnail, context)

        handleCountDown(data.endDate)
    }

    fun setRequestRemoveCallback(position: Int, removeCallback: RemoveCallback) {
        this.itemIndex = position
        this.removeCallback = removeCallback
    }

    private fun setName(name: String) {
        tvName.text = name
    }

    private fun setPrice(price: Double) {
        val priceString = price.toString();
        tvPrice.text = "$$priceString"
    }

    private fun setImage(url: String, context: Context) {
        val drawable = Drawable.createFromStream(context.assets.open(url), null)
        ivThumbnail.setImageDrawable(drawable)
    }

    private fun handleCountDown(endDate: Date) {
        val now = Date()
        val timeLeft = endDate.time - now.time

        if (timeLeft < 1000) {
            removeCallback?.removeItem(itemIndex)
        }
        else {
            setCountDown(timeLeft)
        }
    }

    private fun setCountDown(timeLeft: Long) {
        tvCountDown.text = getDownTime(timeLeft / 1000)
    }

    private fun getDownTime(second: Long): String {
        val days = second / 60 / 60 / 24
        val hours = second / 60 / 60
        val minutes = second / 60
        val seconds = second % 60

        return String.format("%d days %02d:%02d:%02d", days, hours, minutes, seconds)
    }
}