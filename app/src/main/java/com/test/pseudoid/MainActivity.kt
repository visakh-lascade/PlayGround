package com.test.pseudoid

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.turf.TurfJoins
import com.test.pseudoid.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        handleEvents()
    }

    private fun handleEvents() {
        binding?.imageView?.setOnClickListener {
            val data = getCountry(
                lon = 114.177216,
                lat = 22.302711,
                applicationContext = applicationContext,
                country_list = arrayListOf()
            )
            Timber.tag("TestFlow").d(" $data")
        }
    }


    private fun getCountry(lon: Double, lat: Double, applicationContext: Context, country_list: ArrayList<String>): ArrayList<String> {

        val jsonData = loadGeoJsonFromAssets(applicationContext, "countries_orig.json")
        val json = JSONObject(jsonData)

        val features = json.getJSONArray("features")
        for (i in 0 until features.length()) {
            val feature = features.getJSONObject(i)
            val geometry = feature.getJSONObject("geometry")
            val type = geometry.getString("type")
            val coordinatesArray = geometry.getJSONArray("coordinates")

            when (type) {
                "Polygon" -> handlePolygon(coordinatesArray, lon, lat, feature.getString("id"), country_list)
                "MultiPolygon" -> handleMultiPolygon(coordinatesArray, lon, lat, feature.getString("id"), country_list)
            }
        }
        return country_list
    }

    private fun handlePolygon(coordinatesArray: JSONArray, lon: Double, lat: Double, countryCode: String, country_list: ArrayList<String>) {
        val borderCoordinates = mutableListOf<List<Point>>()
        for (i in 0 until coordinatesArray.length()) {
            val innerArray = coordinatesArray.getJSONArray(i)
            val innerCoordinates = mutableListOf<Point>()

            for (j in 0 until innerArray.length()) {
                val coordinate = innerArray.getJSONArray(j)
                val latitude = coordinate.getDouble(1)
                val longitude = coordinate.getDouble(0)
                innerCoordinates.add(Point.fromLngLat(longitude, latitude))
            }
            borderCoordinates.add(innerCoordinates)
        }

        val polygon = Polygon.fromLngLats(borderCoordinates)
        val pointToCheck = Point.fromLngLat(lon, lat)
        if (TurfJoins.inside(pointToCheck, polygon)) {
            country_list.add(countryCode)
        }
    }

    private fun handleMultiPolygon(coordinatesArray: JSONArray, lon: Double, lat: Double, countryCode: String, country_list: ArrayList<String>) {
        for (i in 0 until coordinatesArray.length()) {
            val polygonArray = coordinatesArray.getJSONArray(i)
            handlePolygon(polygonArray, lon, lat, countryCode, country_list)
        }
    }

}