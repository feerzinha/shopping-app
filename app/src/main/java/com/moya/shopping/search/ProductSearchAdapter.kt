package com.moya.shopping.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.moya.shopping.R
import com.moya.shopping.model.Product
import kotlinx.android.synthetic.main.view_product_search_item.view.*

class ProductSearchAdapter : RecyclerView.Adapter<ProductSearchAdapter.ViewHolder>() {

    private val products = mutableListOf<Product>()

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(products[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_product_search_item, parent, false)
    )

    fun updateData(newList: List<Product>) {
        products.clear()
        products.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: Product) {
            itemView.product_name.text = product.name

            itemView.product_image.load(product.image)
        }
    }
}
