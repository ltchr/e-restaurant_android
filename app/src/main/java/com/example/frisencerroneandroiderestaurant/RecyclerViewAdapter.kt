package com.example.frisencerroneandroiderestaurant;

// https://openclassrooms.com/forum/sujet/recyclerview-no-adapter-attached-skipping-layout

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frisencerroneandroiderestaurant.model.DishModel;
import com.example.frisencerroneandroiderestaurant.databinding.ItemRowBinding
import com.squareup.picasso.Picasso


class RecyclerViewAdapter(
    private val itemListEntries: List<DishModel>,
    private val entriesClickListener: (DishModel) -> Unit
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = itemListEntries[position]
        //holder.binding.nameTitle.text = dish.name_title
        holder.layout.setOnClickListener {
            entriesClickListener.invoke(dish)
            // entriesClickListener.onItemClickListener(itemListEntries[position])
        }
        holder.bind(dish)
    }

    override fun getItemCount(): Int {
        //Log.d("### DEBUG itemListEntries.size: ", itemListEntries.size.toString())
        // return itemListEntries.size
        return itemListEntries.count()
    }

    class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.wrapImage
        private val titleView: TextView = binding.nameTitle
        private val priceView: TextView = binding.prices
        val layout = binding.root

        fun bind(dish: DishModel) {
            titleView.text = dish.name_title
            priceView.text = "${dish.prices.first().price} €"
            Picasso.get()
                // .load(dish.getThumbnailUrl())
                .load(R.drawable.android_pie)
                .error(R.drawable.android_pie)
                .placeholder(R.drawable.android_pie)
                .into(imageView)
        /*if (dish.getThumbnailUrl() != null || dish.getThumbnailUrl()!!.isEmpty()) {
                Picasso.get()
                    .load(dish.getThumbnailUrl())
                    .error(R.drawable.android_pie)
                    .placeholder(R.drawable.android_pie)
                    .into(imageView)
            } else {
                Picasso.get()
                    .load(R.drawable.android_pie)
                    .error(R.drawable.android_pie)
                    .placeholder(R.drawable.android_pie)
                    .into(imageView)
            }*/
        }
    }
}