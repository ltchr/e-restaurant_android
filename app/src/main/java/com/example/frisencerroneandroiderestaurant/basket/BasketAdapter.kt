package com.example.frisencerroneandroiderestaurant.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frisencerroneandroiderestaurant.R
import com.example.frisencerroneandroiderestaurant.databinding.BasketCellBinding
import com.squareup.picasso.Picasso

interface BasketCellInterface {
    fun onDeleteItem(item: BasketItem)
    fun onShowDetail(item: BasketItem) // Optional
}

class BasketAdapter(
    private val basket: Basket,
    private val context: Context,
    private val delegate: BasketCellInterface
) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    class BasketViewHolder(binding: BasketCellBinding) : RecyclerView.ViewHolder(binding.root) {
        private val itemTitle: TextView = binding.basketItemTitle
        private val itemPrice: TextView = binding.basketItemPrice
        private val itemQuantity: TextView = binding.basketItemQuantity
        private val itemImageView: ImageView = binding.basketItemImageView
        private val deleteButton: ImageView = binding.basketItemDelete
        val layout = binding.root

        fun bind(item: BasketItem, context: Context, delegate: BasketCellInterface) {
            itemTitle.text = item.dish.name_title
            itemPrice.text = "${item.dish.prices.first().price}€"
            itemQuantity.text = "${context.getString(R.string.quantity)} ${item.count.toString()}"
            Picasso.get()
                .load(R.drawable.android_pie)
                .error(R.drawable.android_pie)
                .placeholder(R.drawable.android_pie)
                .into(itemImageView)
            deleteButton.setOnClickListener {
                delegate.onDeleteItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(
            BasketCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val item = basket.items[position]
        holder.layout.setOnClickListener {
            delegate.onShowDetail(item)
        }
        holder.bind(item, context, delegate)
    }

    override fun getItemCount(): Int {
        return basket.items.count()
    }
}