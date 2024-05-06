package com.example.restau.Service

import com.example.restau.Model.Menu.MenuResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MenuService {
    @GET("/api/menu/v1/get-menu-android/{id}")
    suspend fun getMenuByJenisMenuId(@Path("id") id: Long): Response<MenuResponse>
}