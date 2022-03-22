package com.example.frisencerroneandroiderestaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frisencerroneandroiderestaurant.basket.Basket
import com.example.frisencerroneandroiderestaurant.basket.BasketItem
import com.example.frisencerroneandroiderestaurant.databinding.ActivityDishDetailBinding
import com.example.frisencerroneandroiderestaurant.model.DishModel
import com.google.android.material.snackbar.Snackbar
import kotlin.math.max

class DishDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDishDetailBinding
    private var itemCount = 1

    companion object {
        const val DISH_EXTRA = "DISH_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dishDetailModel: DishModel? = intent.getSerializableExtra(DISH_EXTRA) as DishModel?

        binding.tvDishName.text = dishDetailModel!!.name_title
        binding.tvDishPrice.text = dishDetailModel.prices[0].price + " €"
       


        for (index in dishDetailModel.ingredients)
            binding.tvDishIngredient.append(index.name + "\n")

        updateCounter(dishDetailModel.prices[0].price)
        binding.addToCart.setOnClickListener {
            addToBasket(dishDetailModel, itemCount)
        }
    }

    private fun updateView(dish: DishModel) {
        refreshShop(dish)

        binding.btnRemove.setOnClickListener {
            itemCount = max(1, itemCount - 1)
            refreshShop(dish)
        }
        binding.btnAdd.setOnClickListener {
            itemCount += 1
            refreshShop(dish)
        }
        binding.addToCart.setOnClickListener {
            addToBasket(dish, itemCount)
        }
    }

    private fun refreshShop(dish: DishModel) {
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.tvCounter.text = itemCount.toString()
        binding.addToCart.text = "${getString(R.string.total)} $price€"
    }

    private fun addToBasket(dish: DishModel, count: Int) {
        val basket = Basket.getBasket(this)
        basket.addItem(BasketItem(dish, count))
        basket.save(this)
        invalidateOptionsMenu()
        Snackbar.make(binding.root, getString(R.string.basket_validation), Snackbar.LENGTH_LONG)
            .show()
    }

    private fun updateCounter(value: String) {
        var priceCount = 0.0
        var counter = 0

        binding.btnAdd.setOnClickListener {
            priceCount += value.toFloat()
            counter += 1
            binding.tvCounter.text = priceCount.toString() + "€"
            binding.dishNumberQuantity.text = counter.toString()
        }

        binding.btnRemove.setOnClickListener {
            if (counter > 0) {
                counter -= 1
                priceCount -= value.toFloat()
            } else {
                priceCount = 0.0
                counter = 0
            }

            binding.tvCounter.text = priceCount.toString() + "€"
            binding.dishNumberQuantity.text = counter.toString()
        }
    }
}

