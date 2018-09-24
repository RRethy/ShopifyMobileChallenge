package com.bonnetrouge.shopifyproducts

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val retrofit = Retrofit.Builder()
        .baseUrl("https://shopicruit.myshopify.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

val shopifyService = retrofit.create(ShopifyService::class.java)
