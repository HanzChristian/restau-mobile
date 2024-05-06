package com.example.restau.Service

import com.example.restau.Model.Transaksi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TransaksiService {
    @POST("/api/list-transaksi/v1/create")
    suspend fun createTransaksi(
        @Body transaksi: Transaksi
    ): Response<Transaksi>
}