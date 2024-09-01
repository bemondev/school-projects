package com.example.cruds1.remote

import com.example.cruds1.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("product/all")
    fun obtenerProductos(): Call<List<Product>>

    @GET("product/filter")
    fun obtenerPorFiltros(
        @Query("name") nombre: String?,
        @Query("category") categoria: String?
    ): Call<List<Product>>

}