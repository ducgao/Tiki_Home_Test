package vn.tiki.android.androidhometest

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import vn.tiki.android.androidhometest.adapter.DealAdapter
import vn.tiki.android.androidhometest.data.api.ApiServices
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.di.initDependencies
import vn.tiki.android.androidhometest.di.inject
import vn.tiki.android.androidhometest.di.releaseDependencies

class MainActivity : AppCompatActivity() {

  private var isCountDownNeeded = false
  val apiServices by inject<ApiServices>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initDependencies(this)

    setContentView(R.layout.activity_main)

    rv_deals.layoutManager = GridLayoutManager(this, 2)
  }

  override fun onResume() {
    super.onResume()
    isCountDownNeeded = true;
    getDeals()
  }

  override fun onPause() {
    super.onPause()
    isCountDownNeeded = false;
  }

  override fun onDestroy() {
    super.onDestroy()
    releaseDependencies()
  }

  private fun getDeals() {
    object : AsyncTask<Unit, Unit, ArrayList<Deal>>() {
      override fun doInBackground(vararg params: Unit?): ArrayList<Deal> {
        return apiServices.getDeals()
      }

      override fun onPostExecute(result: ArrayList<Deal>) {
        super.onPostExecute(result)
        rv_deals.adapter = DealAdapter(result, this@MainActivity)
        startCountDown()
      }
    }.execute()
  }

  private fun startCountDown() {
    val handler = Handler()
    handler.postDelayed(object : Runnable {
      override fun run() {
        if (isCountDownNeeded) {
          rv_deals.adapter.notifyDataSetChanged()
          handler.postDelayed(this, 1000)
        }
      }
    }, 1000)
  }
}

