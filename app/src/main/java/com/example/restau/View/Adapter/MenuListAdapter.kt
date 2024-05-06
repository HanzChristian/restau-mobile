package com.example.restau

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restau.Model.MenuItemQuantity

class MenuListAdapter(private val items: List<MenuItemQuantity>) : RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodName: TextView = view.findViewById(R.id.food_name)
        val foodPrice: TextView = view.findViewById(R.id.food_price)
        val foodDesc: TextView = view.findViewById(R.id.food_desc)
        val selectedCount: TextView = view.findViewById(R.id.selected_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food_proceed, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.foodName.text = item.menu.namaMenu
        holder.foodPrice.text = "Rp. ${item.menu.hargaMenu}"
        holder.foodDesc.text = item.menu.deskripsiMenu
        holder.selectedCount.text = "Selected: ${item.quantity}"
    }

    override fun getItemCount() = items.size
}

