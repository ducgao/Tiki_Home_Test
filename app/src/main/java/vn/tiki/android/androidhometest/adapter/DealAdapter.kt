package vn.tiki.android.androidhometest.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.tiki.android.androidhometest.R
import vn.tiki.android.androidhometest.adapter.viewholder.DealViewHolder
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.util.RemoveCallback

class DealAdapter(private val items: ArrayList<Deal>, private val context: Context) : RecyclerView.Adapter<DealViewHolder>(), RemoveCallback {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        return DealViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_deal_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        holder.setData(items[position], context)
        holder.setRequestRemoveCallback(this)
    }

    override fun removeItem(item: Deal) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val indexOfItem = items.indexOf(item)
            items.remove(item)
            notifyItemRemoved(indexOfItem)
        }
    }
}