package vn.tiki.android.androidhometest.adapter.viewholder

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.layout_deal_item.view.*
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.util.CountDownTextView
import vn.tiki.android.androidhometest.util.OnCountDownEnd
import vn.tiki.android.androidhometest.util.RemoveCallback
import java.util.*

class DealViewHolder (view: View) : RecyclerView.ViewHolder(view), OnCountDownEnd {
    private val ivThumbnail = view.iv_thumbnail
    private val tvName = view.tv_name
    private val tvPrice = view.tv_price
    private val tvCountDown = view.tv_countdown

    private var data : Deal? = null
    private var removeCallback : RemoveCallback? = null

    fun setData (data: Deal, context: Context) {
        this.data = data

        setName(data.productName)
        setPrice(data.productPrice)
        setImage(data.productThumbnail, context)

        tvCountDown.setRefTime(data.endDate.time, this)
    }

    fun setRequestRemoveCallback(removeCallback: RemoveCallback) {
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

    override fun onCountDownEnd() {
        removeCallback?.removeItem(data!!)
    }
}