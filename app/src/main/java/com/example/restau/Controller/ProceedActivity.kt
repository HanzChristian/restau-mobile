package com.example.restau.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restau.MenuListAdapter
import com.example.restau.Model.MenuItemQuantity
import com.example.restau.Model.ShoppingCart
import com.example.restau.Model.Struk.Struk
import com.example.restau.Model.Transaksi
import com.example.restau.R
import com.example.restau.Retrofit.InstanceRetro
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProceedActivity : AppCompatActivity() {

    private lateinit var struk: Struk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proceed)

        //Retrieve the struk data from Intent
        struk = intent.getSerializableExtra("struk") as Struk

        // Retrieve the data from the Intent
        val tableId = intent.getStringExtra("tableId")
        val customerName = intent.getStringExtra("customerName")
        val items = intent.getSerializableExtra("items") as ArrayList<MenuItemQuantity>
        val totalPrice = intent.getDoubleExtra("totalPrice", 0.0)

        // Retrieve Transaction to requested API
        val transactionsJson = intent.getStringExtra("transactions")
        val type = object : TypeToken<List<Transaksi>>() {}.type
        val transactions = Gson().fromJson<List<Transaksi>>(transactionsJson, type)

        //Print data Transaction
        for (transaction in transactions) {
            println("Id: ${transaction.idStruk} Id Menu: ${transaction.idMenu}, Jumlah: ${transaction.jumlahMenu}\n")
        }

        val proceedButton = findViewById<TextView>(R.id.btnProceed)

        proceedButton.setOnClickListener{
            var successCounter = 0
            for (transaction in transactions){
                CoroutineScope(Dispatchers.IO).launch {
                    val response = InstanceRetro.transaksiService.createTransaksi(transaction)
                    if(response.isSuccessful) {
                        println("Request berhasil: ${response.body()}")
                        successCounter++
                        if (successCounter == transactions.size) {
                            // All requests were successful
                            launch(Dispatchers.Main) {
                                Toast.makeText(this@ProceedActivity, "Data is saved", Toast.LENGTH_SHORT).show()
                                ShoppingCart.clearAllItems()
                                val intent = Intent(this@ProceedActivity, MenuActivity::class.java)
                                intent.putExtra("struk", struk)
                                intent.putExtra("createStruk", false)
                                startActivity(intent)
                            }
                        }
                    }else{
                        println("Request gagal createTransaksi: ${response.errorBody()}, ${response.body()}")
                        println("Response code: ${response.code()}")
                        launch(Dispatchers.Main) {
                            Toast.makeText(this@ProceedActivity, "Data is not saved", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // Find the views in the layout
        val tvTableInfo = findViewById<TextView>(R.id.tvTableInfo)
        val rvMenuList = findViewById<RecyclerView>(R.id.rvMenuList)
        val tvTotalPrice = findViewById<TextView>(R.id.tvTotalPrice)

        // Update the views with the data
        tvTableInfo.text = "Table ID: $tableId, Customer Name: $customerName"
        tvTotalPrice.text = "Total Price: Rp. $totalPrice"

        // Set up the RecyclerView with the list of items
        rvMenuList.layoutManager = LinearLayoutManager(this)
        rvMenuList.adapter = MenuListAdapter(items)
    }

}