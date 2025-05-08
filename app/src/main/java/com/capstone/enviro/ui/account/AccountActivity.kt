package com.capstone.enviro.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.enviro.MainActivity
import com.capstone.enviro.R
import com.capstone.enviro.databinding.ActivityAccountBinding
import com.google.firebase.auth.FirebaseAuth

class AccountActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityAccountBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

       _binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        // If still logged in, go to MainActivity
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}