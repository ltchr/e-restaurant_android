package com.example.frisencerroneandroiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.frisencerroneandroiderestaurant.databinding.ActivityCategoryBinding
import com.example.frisencerroneandroiderestaurant.model.DishesModel
import com.example.frisencerroneandroiderestaurant.model.DishModel
import com.google.gson.Gson
import org.json.JSONObject

import com.android.volley.DefaultRetryPolicy
import com.example.frisencerroneandroiderestaurant.model.MenuResult
import com.example.frisencerroneandroiderestaurant.model.RegisterResult
import com.google.gson.GsonBuilder

enum class ItemType {
    STARTER, MAIN, DESSERT;

    companion object {
        fun categoryTitle(item: ItemType?): String {
            return when (item) {
                STARTER -> "Entrées"
                MAIN -> "Plats"
                DESSERT -> "Desserts"
                else -> ""
            }
        }
    }
}

class NextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
     
        setContentView(binding.root)




        val category = intent.getSerializableExtra(HomeActivity.CATEGORY_KEY) as? ItemType

        
        binding.categoryTitle.text = getCategoryTitle(category)
        loadView(listOf<DishModel>())
        callApi(category)
    }

    private fun callApi(category: ItemType?) {
        val apiURL = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObj = JSONObject()

        jsonObj.put("id_shop", "1")

        val jsonObject = JsonObjectRequest(
            Request.Method.POST, apiURL, jsonObj,
            { response ->
            
                parseResult(response.toString(), category)

            },
            { error ->
                error.printStackTrace()
      
            }
        )

   
        Volley.newRequestQueue(this).add(jsonObject)
    }


    private fun loadView(dishesList: List<DishModel>?) {
        val recyclerViewCategory = binding.recyclerView
        dishesList?.let {
            val adapter = RecyclerViewAdapter(it) { dish ->
                val intent = Intent(this, DishDetailActivity::class.java)
                intent.putExtra(DishDetailActivity.DISH_EXTRA, dish)
                startActivity(intent)
            }
            recyclerViewCategory.layoutManager = LinearLayoutManager(this)
            recyclerViewCategory.setHasFixedSize(true)
            recyclerViewCategory.adapter = adapter
        }

    }

    private fun parseResult(response: String, category: ItemType?) {
        val menuResult = GsonBuilder().create().fromJson(response, MenuResult::class.java)
        val items = menuResult.data.firstOrNull { it.name == ItemType.categoryTitle(category) }
        loadView(items?.items)
    }

    /*
    override fun onItemClickListener(dishData: DishModel) {
        val intent = Intent(this, DishDetailActivity::class.java)
        intent.putExtra("dishModel", dishData)
        startActivity(intent)
    }
    */

    private fun getCategoryTitle(item: ItemType?): String {
        return when (item) {
            ItemType.STARTER -> getString(R.string.starter)
            ItemType.MAIN -> getString(R.string.main)
            ItemType.DESSERT -> getString(R.string.dessert)
            else -> ""
        }
    }

}

