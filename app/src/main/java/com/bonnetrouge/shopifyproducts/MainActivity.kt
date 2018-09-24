package com.bonnetrouge.shopifyproducts

import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bonnetrouge.shopifyproducts.fragments.ProductsFragment
import com.bonnetrouge.shopifyproducts.fragments.TagsFragment
import com.bonnetrouge.shopifyproducts.models.Products
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val viewModel by lazyAndroid {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.fetchProductInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            fragmentTransaction {
                add(R.id.fragmentContainer, TagsFragment.getInstance(), null)
            }
        }
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        try {
            unregisterReceiver(networkReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "Unable to unregister connectivity receiver")
        }
    }

    fun onItemClick(tag: String) {
        fragmentTransaction(true) {
            replace(R.id.fragmentContainer, ProductsFragment.getInstance(tag), null)
        }
    }

    companion object {
        val TAG = MainActivity::class.simpleName
    }
}
