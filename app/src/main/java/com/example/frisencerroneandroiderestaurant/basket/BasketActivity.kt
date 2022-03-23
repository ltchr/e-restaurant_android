package com.example.frisencerroneandroiderestaurant.basket

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.frisencerroneandroiderestaurant.DetailViewFragment
import com.example.frisencerroneandroiderestaurant.HomeActivity
import com.example.frisencerroneandroiderestaurant.R
import com.example.frisencerroneandroiderestaurant.account.UserActivity
import com.example.frisencerroneandroiderestaurant.databinding.ActivityBasketBinding
import com.example.frisencerroneandroiderestaurant.net.NetworkConstant
import org.json.JSONObject

class BasketActivity : AppCompatActivity(), BasketCellInterface {
    lateinit var binding: ActivityBasketBinding
    private lateinit var basket: Basket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        basket = Basket.getBasket(this)

        val fragment = BasketItemsFragment(basket, this)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit()

        binding.orderButton.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivityForResult(intent, UserActivity.REQUEST_CODE)
        }
    }

    override fun onDeleteItem(item: BasketItem) {
        basket.items.remove(item)
        basket.save(this)
    }

    override fun onShowDetail(item: BasketItem) {
        val fragment = DetailViewFragment(item.dish)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UserActivity.REQUEST_CODE && resultCode == Activity.RESULT_FIRST_USER) {
            val sharedPreferences =
                getSharedPreferences(UserActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val idUser = sharedPreferences.getInt(UserActivity.ID_USER, -1)
            if (idUser != -1) {
                sendOrder(idUser)
            }
        }
    }

    private fun sendOrder(idUser: Int) {
        val message = basket.items.map { "${it.count}x ${it.dish.name_title}" }.joinToString("\n")

        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_ORDER

        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP, "1")
        jsonData.put(NetworkConstant.ID_USER, idUser)
        jsonData.put(NetworkConstant.MSG, message)

        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Votre commande a bien été prise en compte")
                builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                    basket.clear()
                    basket.save(this)
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                builder.show()
            },
            { error ->
                error.message?.let {
                } ?: run {
                }
            }
        )
        queue.add(request)
    }
}