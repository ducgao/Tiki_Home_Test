package vn.tiki.android.androidhometest.adapter.viewholder

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.layout_deal_item.view.*
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.util.CountDownEndCallback
import vn.tiki.android.androidhometest.util.RemoveCallback

class DealViewHolder (view: View) : RecyclerView.ViewHolder(view), CountDownEndCallback {
    private val ivThumbnail = view.iv_thumbnail
    private val tvName = view.tv_name
    private val tvPrice = view.tv_price
    private val tvCountDown = view.tv_countdown
    private val context = view.context

    private var data : Deal? = null
    private var removeCallback : RemoveCallback? = null

    fun setData (data: Deal) {
        this.data = data

        setName(data.productName)
        setPrice(data.productPrice)
        setImage(data.productThumbnail)

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

    private fun setImage(url: String) {
        val drawable = Drawable.createFromStream(context.assets.open(url), null)
        ivThumbnail.setImageDrawable(drawable)
    }

    override fun onCountDownEnd() {
        removeCallback?.removeItem(data!!)
    }
}