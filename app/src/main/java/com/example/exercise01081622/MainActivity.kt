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
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    // Parse data
                    val jsonItems = JSONArray(response.body!!.string())
                    var itemArray: Array<Item> = emptyArray()
                    for (i in 0 until jsonItems.length()) {
                        val jsonItem = jsonItems.getJSONObject(i)
                        itemArray += Item(
                            jsonItem.getInt("id"),
                            jsonItem.getInt("listId"),
                            if (!jsonItem.isNull("name")) jsonItem.getString("name") else null
                        )
                    }

                    // Winnow data
                    itemArray = winnowItems(itemArray)

                    // Update view
                    runOnUiThread(Runnable {
                        (recyclerView.adapter as ItemAdapter).setItems(itemArray)
                        recyclerView.adapter?.notifyDataSetChanged()
                    })
                }
            }
        })
    }

    private fun winnowItems(itemArray: Array<Item>): Array<Item> {
        // Remove items with null or blank names
        var winnowedItems = itemArray.filter { !it.name.isNullOrBlank() }
        // Group items by listId then sort by name
        winnowedItems = winnowedItems.sortedWith(compareBy({ it.listId }, { it.name }))
        // Return winnowed array
        return winnowedItems.toTypedArray()
    }
}

