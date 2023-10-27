package com.test.pseudoid

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.test.pseudoid.databinding.ActivityMainBinding
import timber.log.Timber
import java.io.File


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

        }
    }
}