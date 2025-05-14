package com.capstone.enviro.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.capstone.enviro.data.remote.RetrofitClient
import com.capstone.enviro.data.remote.TokenManager
import com.capstone.enviro.databinding.FragmentRegister2Binding
import com.capstone.enviro.domain.model.ActivityLog
import com.capstone.enviro.domain.model.Address
import com.capstone.enviro.domain.model.ContactInfo
import com.capstone.enviro.domain.model.User
import com.capstone.enviro.domain.service.UserService
import com.capstone.enviro.utils.getMongoDBDate
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register2Fragment : Fragment() {
    private var _binding: FragmentRegister2Binding? = null
    private val viewBinding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegister2Binding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Accept the bundle passed from the previous fragment
        val bundle = this.arguments
        val email = bundle?.getString("email").toString()
        val password = bundle?.getString("password").toString()
        val fName = bundle?.getString("firstName").toString()
        val lName = bundle?.getString("lastName").toString()
        Log.d("Register2Fragment", "Email: $email, Password: $password, First Name: $fName, Last Name: $lName")

        viewBinding.btnRegister.setOnClickListener {
            val phone = viewBinding.etPhone.text.toString()
            val street = viewBinding.etStreet.text.toString()
            val city = viewBinding.etCity.text.toString()
            val province = viewBinding.etProvince.text.toString()
            val country = viewBinding.etCountry.text.toString()
            val zipCode = viewBinding.etZip.text.toString()

            // TODO: Add validations
            if (phone.isEmpty() || street.isEmpty() || city.isEmpty() || province.isEmpty() || country.isEmpty() || zipCode.isEmpty()) {
                Snackbar.make(
                    view,
                    "All fields must not be empty",
                    Snackbar.LENGTH_SHORT
                ).show()
                Log.i("Register2Fragment", "All fields must not be empty")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = auth.currentUser

                        // Store firebase user uid to database
                        getUserToken { idToken ->
                            if (idToken != null) {
                                firebaseUser?.let {
                                    val userId = it.uid
                                    storeUserData(
                                        userId = userId,
                                        email = email,
                                        name = "$fName $lName",
                                        phone = phone,
                                        street = street,
                                        city = city,
                                        province = province,
                                        country = country,
                                        zipCode = zipCode
                                    )
                                }

                                Log.d("RegisterFragment", "Registration successful")
                                startActivity(Intent(activity, AccountActivity::class.java))
                                activity?.finish()
                            }
                        }
                    } else {
                        Log.w("RegisterFragment", "Registration failed", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            Snackbar.make(view, "Invalid email format", Snackbar.LENGTH_SHORT).show()
                            Log.i("RegisterFragment", "Invalid email format")
                        } else if (task.exception is FirebaseAuthWeakPasswordException) {
                            Snackbar.make(view, "Weak password", Snackbar.LENGTH_SHORT).show()
                            Log.i("RegisterFragment", "Weak password")
                        } else {
                            Snackbar.make(view, "Registration failed: ${task.exception?.message}", Snackbar.LENGTH_SHORT).show()
                            Log.i("RegisterFragment", "Registration failed: ${task.exception?.message}")
                        }
                    }
                }
        }

        viewBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun storeUserData(
        userId: String,
        email: String,
        name: String,
        phone: String,
        street: String,
        city: String,
        province: String,
        country: String,
        zipCode: String
    ) {
        val tokenManager = TokenManager(requireContext())
        val userService = RetrofitClient.createService<UserService>(tokenManager)

        val user = User(
            userId = userId,
            email = email,
            name = name,
            profilePicture = null, // TODO: Image Placeholder URL
            roles = listOf("USER"),
            accountStatus = "ACTIVE",
            activityLog = listOf(
                ActivityLog(
                    action = "REGISTRATION",
                    timestamp = getMongoDBDate()
                )
            ),
            lastLogin = getMongoDBDate(),
            loginProvider = "FIREBASE",
            createdAt = getMongoDBDate(),
            updatedAt = getMongoDBDate(),
            contactInfo = ContactInfo(
                phone = phone,
                address = Address(
                    street = street,
                    city = city,
                    province = province,
                    country = country,
                    zipCode = zipCode
                )
            )
        )

        val call = userService.createUser(user)
        call.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val createdUser = response.body()
                    Log.d("RegisterFragment2", "User data stored successfully: $createdUser")
                } else {
                    Log.e("RegisterFragment2", "Failed to store user data: HTTP ${response.code()}")
                    try {
                        Log.e("RegisterFragment2", "Error body: ${response.errorBody()?.string()}")
                    } catch (e: Exception) {
                        Log.e("RegisterFragment2", "Error parsing error body: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("RegisterFragment2", "Failed to store user data: ${t.message}")
            }
        })
    }

    /**
     * Get the user's ID token from Firebase Authentication.
     * @param onTokenReceived Callback function to handle the received token.
     */
    private fun getUserToken(onTokenReceived: (String?) -> Unit) {
        auth.currentUser?.getIdToken( true)
            ?.addOnCompleteListener { tokenTask ->
                if (tokenTask.isSuccessful) {
                    val idToken = tokenTask.result?.token
                    Log.d("RegisterFragment", "ID Token: $idToken")
                    onTokenReceived(idToken)
                } else {
                    Log.e("RegisterFragment", "Failed to get ID token: ${tokenTask.exception?.message}")
                    onTokenReceived(null)
                }
            } ?: onTokenReceived(null)
        return
    }
}