package com.example.restau.Model.JenisMenu

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import java.io.Serializable

data class JenisMenu(
    @SerializedName("id")
    val id: Long,

    @SerializedName("namaJenis")
    val namaJenis: String
):Serializable
