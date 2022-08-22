package com.example.exercise01081622

import org.json.JSONArray

fun parseItems(responseItems: String): Array<Item> {
    val jsonItems = JSONArray(responseItems)
    var items = emptyArray<Item>()
    for (i in 0 until jsonItems.length()) {
        val jsonItem = jsonItems.getJSONObject(i)
        items += Item(
            jsonItem.getInt("id"),
            jsonItem.getInt("listId"),
            if (!jsonItem.isNull("name")) jsonItem.getString("name") else null
        )
    }
    return items
}

fun winnowItems(items: Array<Item>): Array<Item> {
    // Remove items with null or blank names
    var winnowedItems = items.filter { !it.name.isNullOrBlank() }
    // Group items by listId then sort by name
    winnowedItems = winnowedItems.sortedWith(compareBy({ it.listId }, { it.name }))
    // Return winnowed array
    return winnowedItems.toTypedArray()
}