package com.example.frisencerroneandroiderestaurant


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.frisencerroneandroiderestaurant.databinding.HomeMainBinding
import com.scottyab.rootbeer.RootBeer
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class HomeActivity : BaseActivity() {
    private lateinit var binding: HomeMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeMainBinding.inflate(layoutInflater)

        if (checkRootMethod1() || checkRootMethod2() || checkRootMethod3())
        {
            exitProcess(0)
        }

        val rootBeer = RootBeer(this)
        if (rootBeer.isRooted) {
            exitProcess(10)
        }
        if (isEmulator()){
            Toast.makeText(baseContext, "Vous êtes sur un émulateur", Toast.LENGTH_SHORT).show()
            Log.d("", "Vous êtes sur émulateur")
        }

        binding = HomeMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.home_main)
        setContentView(binding.root)

        binding.buttonFirst.setOnClickListener {
            manageIntent(ItemType.STARTER)

            // Toast.makeText(baseContext, "Back to menu", Toast.LENGTH_SHORT).show()
        }
        binding.buttonFirst2.setOnClickListener {
            manageIntent(ItemType.MAIN)
        }
        binding.buttonFirst3.setOnClickListener {
            manageIntent(ItemType.DESSERT)
        }

        binding.warning.setOnClickListener{
            val myIntent = Intent(this, InformationActivity::class.java)
            this.startActivity(myIntent)
        }
    }

    private fun manageIntent(categoryKey: ItemType) {
        val myIntent = Intent(this, NextActivity::class.java)
        myIntent.putExtra(CATEGORY_KEY, categoryKey)

        Log.i("Info Debug", "End Home activity")

        this.startActivity(myIntent)
    }

    companion object {
        const val CATEGORY_KEY = "category_key"
    }

    private fun checkRootMethod1(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRootMethod2(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su",
            "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
            "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"
        )
        for (path in paths) {
            if (File(path).exists()) return true
        }
        return false
    }

    private fun checkRootMethod3(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val `in` = BufferedReader(InputStreamReader(process.inputStream))
            if (`in`.readLine() != null) true else false
        } catch (t: Throwable) {
            false
        } finally {
            process?.destroy()
        }
    }
    private fun isEmulator(): Boolean {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_gphone64_arm64")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator"))
    }


    private fun exitProcess(status: Int): Nothing{
        exitProcess(10)
    }
}