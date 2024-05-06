package com.example.restau.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.restau.View.Adapter.MenuPagerAdapter
import com.example.restau.Model.Struk.Struk
import com.example.restau.Retrofit.InstanceRetro
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.restau.databinding.ActivityMenuBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var struk: Struk


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initate viewPager and tabLayout
        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        //Get the struk data from intent
        struk = intent.getSerializableExtra("struk") as Struk
        val tableInfo = "No Tabel : ${struk.idTabel}\nNama Kustomer : ${struk.namaKustomer}"
        binding.tvTableInfo.text = tableInfo

        //Check if the struk should be created or not based on prev
        val shouldCreateStruk = intent.getBooleanExtra("createStruk", false)
        if (shouldCreateStruk) {

            //Post the struk data to the API
            createStruk(struk)
        }

        //Get the jenis Menu data
        getJenisMenu(viewPager)
    }

    private fun createStruk(struk: Struk){
        CoroutineScope(Dispatchers.IO).launch{

            //Get response from service
            val response = InstanceRetro.strukService.createStruk(struk)

            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    println("Request berhasil: ${response.body()}")

                    //Save the id to ShardPreferences
                    val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
                    with (sharedPref.edit()) {
                        putLong("strukId", response.body()?.data?.id ?: 0L)
                        apply()
                    }
                }else{
                    //When failed, intent back to Menu Activity
                    println("Request gagal createStruk: ${response.errorBody()}, ${response.body()}")
                    println("Response code: ${response.code()}")
                    Toast.makeText(this@MenuActivity,"Meja sedang dipakai!",Toast.LENGTH_LONG).show()
                    val intent = Intent(this@MenuActivity, StartActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun getJenisMenu(viewPager: ViewPager2) {
        CoroutineScope(Dispatchers.IO).launch {

            //Response from service
            val response = InstanceRetro.jenisMenuService.getAllJenisMenu()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    //Get the list of jenis menu object
                    val jenisMenus = response.body()?.data ?: listOf()

                    // Extract the IDs and names of the menu types
                    val menuTypeIds = jenisMenus.map { it.id }
                    val menuTypeNames = jenisMenus.map { it.namaJenis }

                    // Set up the ViewPager with the sections adapter
                    viewPager.adapter = MenuPagerAdapter(this@MenuActivity, menuTypeIds, struk)

                    // Connect the tab layout with the view pager
                    TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
                        tab.text = menuTypeNames[position]
                    }.attach()
                } else {
                    //If failed make toast
                    println("Request gagal getjenisMenu: ${response.errorBody()}, ${response.body()}")
                    println("Response code: ${response.code()}")
                    Toast.makeText(this@MenuActivity,"Failed to fetch menu types!",Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}