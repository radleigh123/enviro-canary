package com.capstone.enviro.ui.home

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.capstone.enviro.SessionManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.capstone.enviro.R
import com.capstone.enviro.databinding.ActivityYouEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

class YouEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYouEditBinding
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
                binding.profileCardImage.setImageURI(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYouEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBarEditYou)
        loadUserData()

        binding.profileCardImage.setOnClickListener {
            showImagePickerOptions()
        }
    }

    private fun showImagePickerOptions() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_image_picker, null)

        bottomSheetView.findViewById<MaterialButton>(R.id.btn_gallery).setOnClickListener {
            openGallery()
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<MaterialButton>(R.id.btn_camera).setOnClickListener {
            // For camera implementation, you'll need additional code for permissions
            // and handling camera capture
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun loadUserData() {
        val user = SessionManager.getUserSession(this)
        // Populate your fields with user data
        // For demo purposes, I'll leave this unimplemented
    }

    private fun saveUserData() {
        // TODO: Implement save changes functionality
        Log.d("YouEditActivity", "User data saved")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_done -> {
                Log.d("YouEditActivity", "Done clicked")
                saveUserData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}