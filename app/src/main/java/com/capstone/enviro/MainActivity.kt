package com.capstone.enviro

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.capstone.enviro.data.remote.RetrofitClient
import com.capstone.enviro.data.remote.TokenManager
import com.capstone.enviro.domain.User
import com.capstone.enviro.domain.UserService
import com.capstone.enviro.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val tokenManager = TokenManager(this)
        val userService = RetrofitClient.createService<UserService>(tokenManager)

        val call = userService.getUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                if (response.isSuccessful) {
                    val users = response.body()
                    binding.testText.text = users.toString()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("MainActivity", "Failed to fetch users", t)
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