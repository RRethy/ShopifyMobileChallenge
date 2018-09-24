package com.bonnetrouge.shopifyproducts.models

data class Products(val products: List<Product>)

data class Product(val id: Long,
                   val title: String,
                   val tags: String,
                   var tagList: List<String>? = null,
                   val variants: List<Variant>,
                   val images: List<Image>)

data class Variant(val id: Long,
                   val inventory_quantity: Long)

data class Image(val id: Long,
                 val src: String)
