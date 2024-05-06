package com.example.restau.View.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restau.Model.Menu.Menu
import com.example.restau.Model.Struk.Struk
import com.example.restau.Controller.MyBottomSheetDialogFragment
import com.example.restau.R
import com.example.restau.Model.ShoppingCart

class FoodItemAdapter(private val menuItems: List<Menu>,private val struk: Struk) : RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodImage: ImageView = view.findViewById(R.id.food_image)
        val foodName: TextView = view.findViewById(R.id.food_name)
        val foodPrice: TextView = view.findViewById(R.id.food_price)
        val foodDesc: TextView = view.findViewById(R.id.food_desc)
        val selectButton: ImageButton = view.findViewById(R.id.select_button)
        val deselectButton: ImageButton = view.findViewById(R.id.deselect_button)
        val selectedCount: TextView = view.findViewById(R.id.selected_count)
        val overlay: View = view.findViewById(R.id.overlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = menuItems[position]
        val count = ShoppingCart.getQuantity(menuItem)
        holder.foodName.text = menuItem.namaMenu
        holder.foodPrice.text = "Rp. ${menuItem.hargaMenu}"
        holder.foodDesc.text = menuItem.deskripsiMenu
        Glide.with(holder.foodImage.context)
            .load(menuItem.gambar)
            .error(R.drawable.no_image)
            .into(holder.foodImage)

        holder.deselectButton.visibility = View.GONE
        holder.selectedCount.text = count.toString()

        val availabilityText: TextView = holder.itemView.findViewById(R.id.availability_text)
        if (menuItem.available) {
            availabilityText.text = "Menu tersedia"
            availabilityText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_green))
            holder.selectButton.visibility = View.VISIBLE
            holder.selectedCount.visibility = View.VISIBLE
            holder.itemView.isClickable = true
            holder.overlay.visibility = View.GONE
        } else {
            availabilityText.text = "Menu tidak tersedia"
            availabilityText.setTextColor(Color.RED)
            holder.selectButton.visibility = View.INVISIBLE
            holder.selectedCount.visibility = View.INVISIBLE
            holder.itemView.isClickable = false
            holder.overlay.visibility = View.VISIBLE
        }


        holder.selectButton.setOnClickListener {
            // Add the clicked item to the list
            ShoppingCart.addItem(menuItem)
            // Make the deselect button visible
            holder.deselectButton.visibility = View.VISIBLE
            // Update the text of the selected_count TextView
            val newCount = ShoppingCart.getQuantity(menuItem)
            holder.selectedCount.text = newCount.toString()

            println("Item added: ${menuItem.namaMenu}, total: ${ShoppingCart.getTotal()}, items: ${ShoppingCart.getItems()}")

            val bottomSheet = MyBottomSheetDialogFragment.newInstance(menuItem, struk)
            bottomSheet.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, "MyBottomSheetTag")
        }

        holder.deselectButton.setOnClickListener {
            ShoppingCart.removeItem(menuItem)

            val newCount = ShoppingCart.getQuantity(menuItem)
            holder.selectedCount.text = newCount.toString()

            if (newCount == 0) {
                holder.deselectButton.visibility = View.GONE
            }

            println("Item removed: ${menuItem.namaMenu}, total: ${ShoppingCart.getTotal()}, items: ${ShoppingCart.getItems()}")
        }
    }


    override fun getItemCount() = menuItems.size
}
