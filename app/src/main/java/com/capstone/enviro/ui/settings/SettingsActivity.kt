package com.capstone.enviro.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.enviro.SessionManager
import com.capstone.enviro.databinding.ActivitySettingsBinding
import com.capstone.enviro.ui.account.AccountActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBarSettings)

        /*val btnLogout = _binding.btnLogout
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
        }*/
        val cardLogout = binding.cardLogout
        cardLogout.setOnClickListener {
            Log.d("SettingsActivity", "Logout card clicked")
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
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
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}