package com.bonnetrouge.shopifyproducts.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.shopifyproducts.MainActivity
import com.bonnetrouge.shopifyproducts.R
import com.bonnetrouge.shopifyproducts.adapters.TagAdapter
import com.bonnetrouge.shopifyproducts.lazyAndroid
import com.bonnetrouge.shopifyproducts.observe
import kotlinx.android.synthetic.main.fragment_tags.*

class TagsFragment : Fragment() {

    private val adapter: TagAdapter by lazyAndroid { TagAdapter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tags, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with (recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@TagsFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }
        with (activity as MainActivity) {
            viewModel.tagsLiveData.observe(this) {
                it?.let {
                    adapter.tags = it
                    adapter.notifyDataSetChanged()
                }
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.title = "Tags"
        }
    }

    fun onItemClick(tag: String) {
        (activity as MainActivity).onItemClick(tag)
    }

    companion object {
        fun getInstance() = TagsFragment()
    }
}
