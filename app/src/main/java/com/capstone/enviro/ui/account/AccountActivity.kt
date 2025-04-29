package com.capstone.enviro.ui.account

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.enviro.R
import com.capstone.enviro.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       _binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }
}