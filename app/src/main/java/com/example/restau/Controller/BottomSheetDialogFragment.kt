package com.example.restau.Controller

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.restau.Model.Menu.Menu
import com.example.restau.Model.MenuItemQuantity
import com.example.restau.Model.ShoppingCart
import com.example.restau.Model.Struk.Struk
import com.example.restau.Model.Transaksi
import com.example.restau.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson

class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var menuItem: Menu
    private lateinit var struk: Struk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            menuItem = it.getSerializable("menu_item") as Menu
            struk = it.getSerializable("struk") as Struk
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)

        val totalPriceTextView: TextView = view.findViewById(R.id.total_price)
        val checkoutButton: Button = view.findViewById(R.id.checkout_button)

        // Update Total
        totalPriceTextView.text = "Total: Rp. ${ShoppingCart.getTotal()}"

        // Checkout Button to Intent
        checkoutButton.setOnClickListener {

            if(::struk.isInitialized){
                val intent = Intent(context, ProceedActivity::class.java)

                //Save TableId and CustomerName to intent
                intent.putExtra("tableId", struk.idTabel.toString())
                intent.putExtra("customerName", struk.namaKustomer)

                // Get the struk Id from Shard Preferences
                val sharedPref = activity?.getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
                val strukId = sharedPref?.getLong("strukId", 0L) ?: 0L

                // Get All the transactions
                val transactions = ArrayList(ShoppingCart.getItems().map {
                    Transaksi(strukId, it.key.idMenu, it.value)
                })

                // Convert transactions to JSON
                val transactionsJson = Gson().toJson(transactions)

                //Save Items, transactions, total price, and struk to intent
                intent.putExtra("items", ArrayList(ShoppingCart.getItems().map { MenuItemQuantity(it.key, it.value) }))
                intent.putExtra("transactions", transactionsJson)
                intent.putExtra("totalPrice", ShoppingCart.getTotal())
                intent.putExtra("struk",struk)

                //Start intent to ProceedActivity
                startActivity(intent)
            }
        }

        return view
    }

    companion object {
        fun newInstance(menuItem: Menu, struk: Struk) = MyBottomSheetDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable("menu_item", menuItem)
                putSerializable("struk", struk)
            }
        }
    }
}