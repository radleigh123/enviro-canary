package com.capstone.enviro.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.capstone.enviro.SessionManager
import com.capstone.enviro.databinding.ActivitySettingsBinding
import com.capstone.enviro.ui.account.AccountActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySettingsBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val btnLogout = _binding.btnLogout
        btnLogout.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            auth.signOut()

            // Optional: Clear any other session data if needed
            SessionManager.clearUserSession(context = this)

            // Go to account activity
            val intent = Intent(this, AccountActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}