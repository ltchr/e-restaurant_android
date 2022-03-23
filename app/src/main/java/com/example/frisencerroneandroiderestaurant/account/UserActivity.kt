package com.example.frisencerroneandroiderestaurant.account

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.frisencerroneandroiderestaurant.R
import com.example.frisencerroneandroiderestaurant.databinding.ActivityUserBinding
import com.example.frisencerroneandroiderestaurant.model.RegisterResult
import com.example.frisencerroneandroiderestaurant.model.User
import com.example.frisencerroneandroiderestaurant.net.NetworkConstant
import com.google.gson.GsonBuilder
import org.json.JSONObject

interface UserActivityFragmentInteraction {
    fun showLogin()
    fun showRegister()
    fun makeRequest(
        email: String?,
        password: String?,
        lastname: String?,
        firstname: String?,
        fromLogin: Boolean
    )
}

class UserActivity : AppCompatActivity(), UserActivityFragmentInteraction {
    lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit()
    }

    private fun verifyInformations(
        email: String?,
        password: String?,
        lastname: String?,
        firstname: String?,
        fromLogin: Boolean
    ): Boolean {
        var verified: Boolean = (email?.isNotEmpty() == true && password?.count() ?: 0 >= 6)

        if (!fromLogin) {
            verified =
                verified && (firstname?.isNotEmpty() == true && lastname?.isNotEmpty() == true)
        }
        return verified
    }

    override fun showLogin() {
        val fragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun showRegister() {
        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    private fun launchRequest(
        email: String?,
        password: String?,
        lastname: String?,
        firstname: String?,
        fromLogin: Boolean
    ) {
        val queue = Volley.newRequestQueue(this)
        var path = NetworkConstant.PATH_REGISTER
        if (fromLogin) {
            path = NetworkConstant.PATH_LOGIN
        }
        val url = NetworkConstant.BASE_URL + path

        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP, "1")
        jsonData.put(NetworkConstant.EMAIL, email)
        jsonData.put(NetworkConstant.PASSWORD, password)
        if (!fromLogin) {
            jsonData.put(NetworkConstant.FIRSTNAME, firstname)
            jsonData.put(NetworkConstant.LASTNAME, lastname)
        }

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                val userResult =
                    GsonBuilder().create().fromJson(response.toString(), RegisterResult::class.java)
                if (userResult.data != null) {
                    saveUser(userResult.data)
                } else {
                    Toast.makeText(this, "Logins incorrect", Toast.LENGTH_LONG).show()
                }
            },
            { error ->
                error.message?.let {
                } ?: run {
                }
            }
        )
        queue.add(request)
    }

    private fun saveUser(user: User) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ID_USER, user.id)
        editor.apply()

        setResult(Activity.RESULT_FIRST_USER)
        finish()
    }

    override fun makeRequest(
        email: String?,
        password: String?,
        lastname: String?,
        firstname: String?,
        fromLogin: Boolean
    ) {
        if (verifyInformations(email, password, lastname, firstname, fromLogin)) {
            launchRequest(email, password, lastname, firstname, fromLogin)
        } else {
            Toast.makeText(this, "Veuillez saisir tous les champs", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val REQUEST_CODE = 111
        const val ID_USER = "ID_USER"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}