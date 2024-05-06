package com.example.restau.Model.Menu

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Menu(

    @SerializedName("idMenu")
    val idMenu: Long,

    @SerializedName("namaMenu")
    val namaMenu: String,

    @SerializedName("hargaMenu")
    val hargaMenu: Double,

    @SerializedName("deskripsiMenu")
    val deskripsiMenu: String,

    @SerializedName("gambar")
    val gambar: String,

    @SerializedName("available")
    val available: Boolean,

):Serializable
