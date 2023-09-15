package com.example.apiintegrationv2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var fm: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fm = supportFragmentManager

        getImageUrl("https://api.thecatapi.com/v1/images/search", "array")

        buttons()
    }

    private fun buttons() {
        findViewById<Button>(R.id.catImageBtn).setOnClickListener {
            getImageUrl("https://api.thecatapi.com/v1/images/search", "array")
            pop()
        }

        findViewById<Button>(R.id.waifuBtnSFW).setOnClickListener {
            getImageUrl("https://api.waifu.pics/sfw/neko", "object")
            pop()
        }

        findViewById<Button>(R.id.waifuBtnNSFW).setOnClickListener {
            fm.beginTransaction()
                .replace(R.id.frameLayout, Warning1())
                .addToBackStack("warning1")
                .commit()
        }
    }

    private fun getImageUrl(requestUrl: String, jsonType: String) {
        val rq: RequestQueue = Volley.newRequestQueue(this)

        if (jsonType == "array") {
            val jsonObjectRequest = JsonArrayRequest(
                Request.Method.GET,
                requestUrl,
                null,
                { response ->
                    val url = response.getJSONObject(0).optString("url")
                    showImage(url)
                }, { error ->
                    Log.d("Error", error.toString())
                }
            )
            rq.add(jsonObjectRequest)
        } else {
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                requestUrl,
                null,
                { response ->
                    val url = response.optString("url")
                    showImage(url)
                }, { error ->
                    Log.d("Error", error.toString())
                }
            )
            rq.add(jsonObjectRequest)
        }
    }

    private fun showImage(url: String) {
        //Log.d("showImage", url)

        Glide.with(this).load(url).into(findViewById(R.id.imageView))

        if (url.takeLast(3) == "gif") notification()
    }

    private fun pop() {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        // MUST BE IN THIS ORDER
        pop()
    }

    private fun notification() {
        val channelId = "API_V2_ID"
        val notificationId = 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "API_V2_Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channelId, channelName, importance)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("API V2")
            .setContentText("Rare item unlocked! You got a gif instead of a image.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(pendingIntent)

        val notificationManager = this.getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}