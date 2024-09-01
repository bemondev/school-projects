package com.example.cruds1.model

data class Product(
    var id: Int,
    var name: String = "",
    var description: String = "",
    var price: Double,
    var urlImagen: String,
    var categoria: Categoria
)