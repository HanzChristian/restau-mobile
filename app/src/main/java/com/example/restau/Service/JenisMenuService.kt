package com.example.restau.Service

import com.example.restau.Model.JenisMenu.JenisMenuResponse
import retrofit2.http.GET
import retrofit2.Response

interface JenisMenuService {
    @GET("/api/jenis-menu/v1/get-all")
    suspend fun getAllJenisMenu(): Response<JenisMenuResponse>
}