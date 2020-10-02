package com.example.code.screens.catalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import com.example.code.R
import com.example.code.utils.data.persistence.entities.ProductEntity


class ProductsRecyclerViewAdapter: RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder>() {


    private var listener: ItemClickListener? = null
    private var items = ArrayList<ProductEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false))
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }


    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val ivImage = view.ivImage
        private val tvTitle = view.tvTitle
        private val tvPrice = view.tvPrice
        private val tvNew = view.tvNew
        private val ivFavorite = view.ivFavorite

        fun onBind(item: ProductEntity) {

            view.setOnClickListener {
                listener?.onItemClick(item)
            }
            ivFavorite.setOnClickListener {
                listener?.onItemFavoriteClick(item)
            }
            if(item.image.isNotEmpty()) {
                Picasso.get()
                    .load(item.image)
                    .fit()
                    .centerCrop()
                    .into(ivImage)
            }
            tvNew.visibility =  if(item.new) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            if(item.isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_favorite_added)
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite_not_added)
            }
            tvPrice.text = view.context.getString(R.string.price,item.price)
            tvTitle.text = item.title
        }
    }

    fun updateItems(items: ArrayList<ProductEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    interface ItemClickListener {
        fun onItemFavoriteClick(item: ProductEntity)
        fun onItemClick(item: ProductEntity)
    }

}