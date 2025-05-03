package com.capstone.enviro

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.enviro.api.User
import com.capstone.enviro.api.UserService
import com.capstone.enviro.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.topAppBar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_notification, R.id.navigation_profile
            )
        )

//        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)
        // TODO: Later on, live data for notification count
        updateNotificationBadge(5)

        sampleApiCall()
    }

    private fun sampleApiCall() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.11:8080/") // Replace with your server's IP address
//            .baseUrl("http://10.0.2.2:8080/") // For Android Emulator
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val userService = retrofit.create(UserService::class.java)

        val call = userService.getUsers()
        call.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful) {
                    val users = response.body()
                    binding.testText.text = users.toString()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                binding.testText.text = "Failed to fetch users: ${t.message}"
            }
        })
    }

    private fun updateNotificationBadge(count: Int) {
        val badge = binding.navView.getOrCreateBadge(R.id.navigation_notification)
        badge.isVisible = count > 0
        badge.number = count
    }
}