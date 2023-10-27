package com.test.pseudoid

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import timber.log.Timber

class MyAccessibilityService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())
    private val scrollRunnable = object : Runnable {
        override fun run() {
            performScrollUp()
            handler.postDelayed(this, 5000)  // 5 seconds
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Timber.d("onAccessibilityEvent ${event?.eventType}")
    }

    override fun onInterrupt() {
        Timber.d("onInterrupt")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        handler.post(scrollRunnable)
        Timber.d("onServiceConnected")
    }

    private fun performScrollUp() {
        val path = Path()
        path.moveTo(500f, 1500f)
        path.lineTo(500f, 500f)

        val builder = GestureDescription.Builder()
        builder.addStroke(GestureDescription.StrokeDescription(path, 0, 100))
        val gesture = builder.build()

        dispatchGesture(gesture, null, null)
        Timber.d("performScrollUp")
    }
}
