package com.example.exercise01081622

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter() : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var itemArray: Array<Item> = emptyArray()

    // Describes an item view and its place within the RecyclerView
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemIdView: TextView = itemView.findViewById(R.id.ItemId)
        private val itemListIdView: TextView = itemView.findViewById(R.id.ItemListId)
        private val itemNameView: TextView = itemView.findViewById(R.id.ItemName)

        // Binds item data to textview
        fun bind(item: Item) {
            itemIdView.text = item.id.toString()
            itemListIdView.text = item.listId.toString()
            itemNameView.text = item.name
        }
    }

    fun setItems(items: Array<Item>) {
        itemArray = items
    }

    // Returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    // Returns size of data list
    override fun getItemCount() = itemArray.size

    // Displays data at a certain position
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(itemArray[position])

}