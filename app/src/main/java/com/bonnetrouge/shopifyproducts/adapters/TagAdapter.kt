package com.bonnetrouge.shopifyproducts.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bonnetrouge.shopifyproducts.R
import com.bonnetrouge.shopifyproducts.fragments.TagsFragment

class TagAdapter(val fragment: TagsFragment) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    var tags = arrayOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TagViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_tag, parent, false))

    override fun getItemCount() = tags.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) = holder.bind()

    inner class TagViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        init {
            itemView.setOnClickListener { fragment.onItemClick(tags[adapterPosition]) }
        }

        fun bind() {
            itemView.findViewById<TextView>(R.id.tagStr).text = tags[adapterPosition]
        }
    }
}