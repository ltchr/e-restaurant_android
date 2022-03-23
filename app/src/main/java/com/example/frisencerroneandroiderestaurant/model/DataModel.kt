package com.example.frisencerroneandroiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DishesModel(var data: List<CategoryModel>) : Serializable
data class CategoryModel(val name_title: String, val items: List<DishModel>) : Serializable


data class DishModel(
    @SerializedName("name_fr") val name_title: String,
    val image: List<String>,
    val prices: List<PriceData>,
    val ingredients: List<IngredientData>
) : Serializable {
    fun getThumbnailUrl(): String? {
        return if (image.isNotEmpty() && image[0].isNotEmpty()) {
            image[0]
        } else {
            null
        }
    }
}


data class PriceData(val price: String) : Serializable
class IngredientData(@SerializedName("name_title") val name: String) : Serializable {}
class Category(@SerializedName("name_fr") val name: String, val items: List<DishModel>) {}

class MenuResult(val data: List<Category>) {
}
class RegisterResult(val data: User) {}

// class User(@SerializedName("name_title") val name: String, val items: List<DishModel>) {}
class User(val id: Int) : Serializable {}