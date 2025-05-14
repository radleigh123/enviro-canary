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
import com.capstone.enviro.data.remote.RetrofitClient
import com.capstone.enviro.data.remote.TokenManager
import com.capstone.enviro.databinding.ActivityYouEditBinding
import com.capstone.enviro.domain.model.Address
import com.capstone.enviro.domain.model.ContactInfo
import com.capstone.enviro.domain.model.MongoId
import com.capstone.enviro.domain.model.PhysicalAttributes
import com.capstone.enviro.domain.model.User
import com.capstone.enviro.domain.service.UserService
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        // loadUserData()

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

    // TODO: Loads user data but makes saving complicated
    private fun loadUserData() {
        val context = this
        val user = SessionManager.getUserSession(context)

        val firstName = user["name"]?.split(" ")?.get(0) ?: "NULL"
        val lastName = user["name"]?.split(" ")?.get(1) ?: "NULL"

        binding.tfFName.editText?.setText(firstName)
        binding.tfLName.editText?.setText(lastName)
        binding.tfStreet.editText?.setText(user["street"])
        binding.tfCity.editText?.setText(user["city"])
        binding.tfProvince.editText?.setText(user["province"])
        binding.tfPhone.editText?.setText(user["phone"])
        binding.tfBio.editText?.setText(user["biography"])
        binding.tfHeight.editText?.setText(user["height"])
        binding.tfWeight.editText?.setText(user["weight"])
        binding.tfAge.editText?.setText(user["age"])
    }

    private fun saveUserData() {
        val tokenManager = TokenManager(this)
        val userService = RetrofitClient.createService<UserService>(tokenManager)
        val user = SessionManager.getUserSession(this)

        val fName = binding.tfFName.editText?.text.toString()
        val lName = binding.tfLName.editText?.text.toString()
        val street = binding.tfStreet.editText?.text.toString()
        val city = binding.tfCity.editText?.text.toString()
        val province = binding.tfProvince.editText?.text.toString()
        val phone = binding.tfPhone.editText?.text.toString()
        val bio = binding.tfBio.editText?.text.toString()
        val height = binding.tfHeight.editText?.text.toString()
        val weight = binding.tfWeight.editText?.text.toString()
        val age = binding.tfAge.editText?.text.toString()

        // Update user data by calling the API by id
        val uid = user["uid"].toString()

        val call = userService.updateUserById(uid, User(
            id = MongoId(
                oid = uid
            ),
            userId = user["userId"].toString(),
            email = user["email"].toString(),
            name = "$fName $lName",
            profilePicture = user["profilePicture"].toString(),
            physicalAttributes = PhysicalAttributes(
                age = age.toIntOrNull(),
                weight = weight.toDoubleOrNull(),
                height = height.toIntOrNull(),
                bloodType = user["bloodType"].toString(),
            ),
            contactInfo = ContactInfo(
                phone = phone,
                address = Address(
                    street = street,
                    city = city,
                    province = province,
                    country = user["country"].toString(),
                    zipCode = user["zipCode"].toString()
                )
            )
        ))

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val updatedUser = response.body()
                    if (updatedUser != null) {
                        SessionManager.saveUserSession(this@YouEditActivity, updatedUser)
                        Log.d("YouEditActivity", "User data updated successfully")
                    } else {
                        Log.e("YouEditActivity", "Failed to update user data")
                    }
                } else {
                    Log.e("YouEditActivity", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<User>, t: Throwable) {
                Log.e("YouEditActivity", "Network error: ${t.message}")
            }
        })

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