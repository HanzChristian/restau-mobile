package com.example.restau.Model.Struk

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Struk(
    @SerializedName("idTabel")
    val idTabel: Long,

    @SerializedName("namaKustomer")
    val namaKustomer: String

): Serializable
