package com.example.restau.Service

import com.example.restau.Model.Struk.Struk
import com.example.restau.Model.Struk.StrukResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StrukService {
    @POST("/api/list-struk/v1/create")
    suspend fun createStruk(
        @Body struk: Struk
    ): Response<StrukResponse>
}