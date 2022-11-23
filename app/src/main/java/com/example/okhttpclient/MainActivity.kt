package com.example.okhttpclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.example.okhttpclient.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // Client server interaction
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //BuildConfig.VERSION_CODE
        //BuildConfig.VERSION_NAME

        binding.urlButton.setOnClickListener {
            val URL = "https://mccrimmon.me/mobileTech.json"
            if (URL.isNotEmpty()){

                // Create the HTTP client
                val client = OkHttpClient()
                // Build the request
                val request = Request.Builder()
                    .url(URL)
                    .build()
                // Enqueue the request and handle the callbacks
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.d("AppLog", "Response received!")
                        response.use {
                            if (!response.isSuccessful){
                                Log.e("AppLog", "HTTP error!")
                            } else {
                                val body = response.body?.string() // fetch the body as separate string
                                binding.urlDump.text = body // print the body to the TextView
                                // process the json and save the local cache or whatever
                            }
                        }
                    }

                })
            } else {
                binding.urlDump.text = "URL was empty"
            }
        }
    }

}