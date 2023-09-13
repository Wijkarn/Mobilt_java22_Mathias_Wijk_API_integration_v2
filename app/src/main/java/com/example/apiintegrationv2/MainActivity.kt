package com.example.apiintegrationv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    lateinit var fm: FragmentManager

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
        Log.d("showImage", url)
        val imageView = findViewById<ImageView>(R.id.imageView)
        Glide.with(this).load(url).into(imageView)
    }

    private fun pop() {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // MUST BE IN THIS ORDER
        pop()
    }
}