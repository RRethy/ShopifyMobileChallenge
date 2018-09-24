package com.bonnetrouge.shopifyproducts

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.bonnetrouge.shopifyproducts.models.Product
import com.bonnetrouge.shopifyproducts.models.Products
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean

class MainViewModel : ViewModel() {

    private var hasFetched = false

    val tagsLiveData: LiveData<Array<String>> by lazyAndroid {
        MediatorLiveData<Array<String>>().apply {
            addSource(productsLiveData) {
                GlobalScope.launch(bgPool) {
                    it?.let {
                        val tags = mutableSetOf<String>().apply {
                            it.products.forEach {
                                it.tagList?.let { this.addAll(it) }
                            }
                        }.toTypedArray().sortedArray()
                        this@apply.postValue(tags)
                    }
                }
            }
        }
    }

    val productsLiveData = MutableLiveData<Products>()

    init {
        fetchProductInfo()
    }

    fun fetchProductInfo() = if (!hasFetched) {
        hasFetched = true
        shopifyService.getProducts().enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>?, response: Response<Products>?) {
                if (response?.isSuccessful == true) {
                    GlobalScope.launch(bgPool) {
                        val cleanedProductList = response.body()!!.apply {
                            products.forEach {
                                it.tagList = it.tags.split(", ")
                            }
                        }
                        productsLiveData.postValue(cleanedProductList)
                    }
                } else {
                    onFailure(call, null)
                }
            }

            override fun onFailure(call: Call<Products>?, t: Throwable?) {
                Log.d(TAG, "Network call failed for Products")
                hasFetched = false
            }
        })
    } else Unit

    fun getProducts(tag: String): List<Product>? {
        return productsLiveData.value?.products?.filter { it.tagList?.contains(tag) ?: false }
    }

    companion object {
        private val TAG = MainViewModel::class.simpleName
    }
}