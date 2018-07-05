package vn.tiki.android.androidhometest.util

import vn.tiki.android.androidhometest.data.api.response.Deal

interface RemoveCallback {
    fun removeItem(itemIndex: Deal)
}

interface CountDownEndCallback {
    fun onCountDownEnd()
}

interface DownloadDealsCallback {
    fun onDeals(deals: ArrayList<Deal>)
}