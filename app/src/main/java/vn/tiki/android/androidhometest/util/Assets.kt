package vn.tiki.android.androidhometest.util

import android.content.res.AssetManager
import vn.tiki.android.androidhometest.data.api.response.Deal
import java.nio.charset.Charset

fun AssetManager.readFile(file: String): String {
  return open(file).bufferedReader(Charset.forName("utf-8")).use { it.readText() }
}