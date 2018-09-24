package com.bonnetrouge.shopifyproducts.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bonnetrouge.shopifyproducts.R
import com.bonnetrouge.shopifyproducts.models.Product
import com.bumptech.glide.Glide

class ProductAdapter(val ctx: Context) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var products: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ProductViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_product, parent, false))

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind()

    inner class ProductViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        init {
        }

        fun bind() {
            itemView.findViewById<TextView>(R.id.productTitle).text = products[adapterPosition].title
            val inventory = products[adapterPosition].variants.fold(0L) { sum, variant ->
                sum + variant.inventory_quantity
            }
            itemView.findViewById<TextView>(R.id.productInventory).text = "Inventory: ${inventory.toString()}"
            if (products[adapterPosition].images.isNotEmpty()) {
                Glide.with(ctx)
                        .load(products[adapterPosition].images[0].src)
                        .into(itemView.findViewById(R.id.productImage))
            }
        }
    }
}
