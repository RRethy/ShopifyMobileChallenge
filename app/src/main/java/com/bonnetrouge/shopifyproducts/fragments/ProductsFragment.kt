package com.bonnetrouge.shopifyproducts.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.shopifyproducts.MainActivity
import com.bonnetrouge.shopifyproducts.R
import com.bonnetrouge.shopifyproducts.adapters.ProductAdapter
import com.bonnetrouge.shopifyproducts.lazyAndroid
import com.bonnetrouge.shopifyproducts.observe
import kotlinx.android.synthetic.main.fragment_tags.*

class ProductsFragment : Fragment() {

    private val adapter: ProductAdapter by lazyAndroid { ProductAdapter(requireContext()) }

    lateinit var productTag: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tags, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with (recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@ProductsFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }
        with (activity as MainActivity) {
            viewModel.getProducts(productTag)?.let {
                adapter.products = it
                adapter.notifyDataSetChanged()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = productTag
        }
    }

    companion object {
        fun getInstance(tag: String) = ProductsFragment().apply { productTag = tag }
    }
}
