package com.example.frisencerroneandroiderestaurant.basket

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frisencerroneandroiderestaurant.databinding.FragmentBasketItemsBinding

class BasketItemsFragment(private val basket: Basket, private val delegate: BasketCellInterface) :
    Fragment(), BasketCellInterface {

    lateinit var binding: FragmentBasketItemsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            reloadData(it)
        }
    }

    private fun reloadData(context: Context) {
        binding.basketItemRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.basketItemRecyclerView.adapter = BasketAdapter(basket, context, this)
    }

    override fun onDeleteItem(item: BasketItem) {
        context?.let {
            basket.items.remove(item)
            basket.save(it)
            reloadData(it)
        }
    }

    override fun onShowDetail(item: BasketItem) {
        delegate.onShowDetail(item)
    }
}

