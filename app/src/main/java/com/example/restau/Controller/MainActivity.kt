package com.example.restau.Controller

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restau.Model.Struk.Struk
import com.example.restau.R
import com.google.zxing.integration.android.IntentIntegrator
import com.example.restau.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var qrScanIntegrator: IntentIntegrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Force to Potrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Get nama Kustomer from intent
        val name = intent.getStringExtra("namaKustomer")
        binding.tvWelcome.text = "Selamat Datang, $name!"

        //Launch Barcode Scanner
        binding.btnLaunch.setOnClickListener { scanBarcode() }

        //Initialize QR Scanner
        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.setOrientationLocked(false)
    }

    private fun scanBarcode() {
        getResult.launch(qrScanIntegrator?.createScanIntent())
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            val result = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show()
                } else {
                    binding.tvResult.text = result.contents
                    val tableNumber = result.contents.toLong()
                    val name = intent.getStringExtra("namaKustomer") ?: "User"
                    val struk = Struk(tableNumber,name)
                    val intent = Intent(this, MenuActivity::class.java)

                    //Intent the data of struk and createStruk to true
                    intent.putExtra("struk", struk)
                    intent.putExtra("createStruk",true) //CreateStruk to make validation if struk created or not
                    Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show()

                    //Start intent to MenuActivity
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show()
            }
        }
}