package com.example.restau.Model

import com.google.gson.annotations.SerializedName

data class Transaksi(

    @SerializedName("idStruk")
    val idStruk: Long,

    @SerializedName("idMenu")
    val idMenu: Long,

    @SerializedName("jumlahMenu")
    val jumlahMenu: Int,
)
