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

    // var category: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)

        setContentView(binding.root)




        val category = intent.getSerializableExtra(HomeActivity.CATEGORY_KEY) as? ItemType

        
        //setContentView(R.layout.activity_category)
        setContentView(binding.root)

        //val mRecycler = findViewById<View>(R.id.yourid) as RecyclerView
        //mRecycler.adapter = adapter

        // findViewById<TextView>(R.id.category_title)

        /*if (intent.hasExtra("category_key")) {
            // binding.categoryTitle.text = intent.getStringExtra("category_key")
            category = intent.getStringExtra("category_key")
        }*/
        /*else {
            category = intent.getSerializableExtra(HomeActivity.CATEGORY_KEY).toString()
        }*/

        // binding.categoryTitle.text = category
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

                // val dishesResponse = Gson().fromJson(response.toString(), DishesModel::class.java)
                parseResult(response.toString(), category)
                // parseResult(dishesResponse.data.firstOrNull { it.name_title == category }?.items)
                // loadView(dishesResponse.data.firstOrNull { true }?.items ?: listOf())
            },
            { error ->
                error.printStackTrace()
                Log.e("NextActivity", "ERR0R request api")
            }
        )
        /*jsonObject.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0,
            1f
        )*/
        //Log.d("### DEBUG jsonObject: ", jsonObject.toString())
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
        //recyclerViewCategory.layoutManager = GridLayoutManager(this, 2)
        //recyclerViewCategory.layoutManager = LinearLayoutManager(this)
        //recyclerViewCategory.setHasFixedSize(true)
        //recyclerViewCategory.adapter = dishesList?.let { RecyclerViewAdapter(it, this) }
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

