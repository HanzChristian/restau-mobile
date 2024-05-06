package com.example.restau.Model.Struk

import com.google.gson.annotations.SerializedName

data class StrukResponse (
    @SerializedName("data")
    val data: StrukData
)

data class StrukData(
    @SerializedName("id")
    val id: Long
)



