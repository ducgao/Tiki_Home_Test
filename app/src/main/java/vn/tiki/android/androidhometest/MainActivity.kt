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
import vn.tiki.android.androidhometest.util.DownloadDealsCallback

class MainActivity : AppCompatActivity() {

  private val apiServices by inject<ApiServices>()
  private var getDealsWork : GetDealsWork? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initDependencies(this)

    setContentView(R.layout.activity_main)

    rv_deals.layoutManager = GridLayoutManager(this, 2)
    getDeals()
  }

  override fun onDestroy() {
    super.onDestroy()
    this.getDealsWork?.setCallback(null)
    releaseDependencies()
  }

  private fun getDeals() {
    this.getDealsWork = GetDealsWork(apiServices)
    this.getDealsWork?.setCallback(object : DownloadDealsCallback {
      override fun onDeals(deals: ArrayList<Deal>) {
        rv_deals.adapter = DealAdapter(deals, this@MainActivity)
      }
    })

    this.getDealsWork?.execute();
  }

  class GetDealsWork(private val apiServices: ApiServices) : AsyncTask<Unit, Unit, ArrayList<Deal>>() {

    private var dealsCallback : DownloadDealsCallback? = null;

    fun setCallback(dealsCallback: DownloadDealsCallback?) {
      this.dealsCallback = dealsCallback;
    }

    override fun doInBackground(vararg params: Unit?): ArrayList<Deal> {
      return apiServices.getDeals()
    }

    override fun onPostExecute(result: ArrayList<Deal>) {
      super.onPostExecute(result)
      dealsCallback?.onDeals(result)
    }
  }
}

