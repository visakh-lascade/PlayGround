package com.test.pseudoid

import android.content.Context


fun loadGeoJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}