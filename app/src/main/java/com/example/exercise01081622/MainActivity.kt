package com.example.exercise01081622

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

const val SOURCE_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter()

        getItems()
    }

    private fun getItems() {
        val request = Request.Builder().url(SOURCE_URL).build()

        // Queue request
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) = e.printStackTrace()

            // Process request
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) throw IOException("Unexpected code $it")
                    // Parse data
                    var items = parseItems(it.body!!.string())
                    // Winnow data
                    items = winnowItems(items)
                    // Update view
                    runOnUiThread {
                        (recyclerView.adapter as ItemAdapter).setItems(items)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}

