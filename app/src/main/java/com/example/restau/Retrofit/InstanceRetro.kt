package com.example.restau.Retrofit

import com.example.restau.Model.JenisMenu.JenisMenuResponse
import com.example.restau.Model.JenisMenu.JenisMenuResponseDeserializer
import com.example.restau.Service.JenisMenuService
import com.example.restau.Service.MenuService
import com.example.restau.Service.StrukService
import com.example.restau.Service.TransaksiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InstanceRetro {
//    private const val BASE_URL = "http://192.168.97.184:8080"
    private const val BASE_URL = "http://10.0.2.2:8080"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val strukService: StrukService by lazy {
        retrofit.create(StrukService::class.java)
    }

    val jenisMenuService: JenisMenuService by lazy {
        retrofit.create(JenisMenuService::class.java)
    }

    val menuService: MenuService by lazy {
        retrofit.create(MenuService::class.java)
    }

    val transaksiService: TransaksiService by lazy {
        retrofit.create(TransaksiService::class.java)
    }

}