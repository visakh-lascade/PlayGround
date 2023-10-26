package com.test.pseudoid

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.test.pseudoid.databinding.ActivityMainBinding
import java.io.File
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private var colorList = mutableListOf<Bitmap>()
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        appInstanceID()

        val bitmapToVideoEncoder = BitmapToVideoEncoder {

            Log.d("BitmapToVideoEncoder", "onEncodingComplete: ${it.path}    size = ${it.length()}")
//do the next line in main thread
            runOnUiThread {

//            play in video view
                val videoView = findViewById<VideoView>(R.id.videoView)
                videoView.setVideoPath(it.path)
                videoView.start()
                //share this video
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "video/*"
                shareIntent.putExtra(Intent.EXTRA_STREAM, it)
                startActivity(Intent.createChooser(shareIntent, "Share Video"))

            }
        }

        bitmapToVideoEncoder.startEncoding(
            480,
            640,
            File(externalCacheDir, "test${System.currentTimeMillis()}.mp4")
        )

        for (i in 0 until 60) {
            val bitmapDrawable = createColorBitmap(getRandomColor(), 100, 100)
            bitmapToVideoEncoder.queueFrame(bitmapDrawable)
        }
        bitmapToVideoEncoder.stopEncoding()
    }

    private fun logD(m: Any, tag: String = "MainActivity") {
        Log.d(tag, "logD: $m")
    }

    private fun getRandomColor(): Int {
        val rnd = Random
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    private fun createColorBitmap(color: Int, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(color)
        return bitmap
    }

    private fun createLayerDrawable(drawables: List<Drawable>): Drawable {
        val layers = arrayOfNulls<Drawable>(drawables.size)
        for (i in drawables.indices) {
            layers[i] = drawables[i]
        }
        return LayerDrawable(layers)
    }
}