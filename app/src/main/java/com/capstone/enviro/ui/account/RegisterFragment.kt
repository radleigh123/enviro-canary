package com.capstone.enviro.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.capstone.enviro.R
import com.capstone.enviro.data.remote.RetrofitClient
import com.capstone.enviro.data.remote.TokenManager
import com.capstone.enviro.databinding.FragmentRegisterBinding
import com.capstone.enviro.domain.model.TimeStamp
import com.capstone.enviro.domain.model.ActivityLog
import com.capstone.enviro.domain.model.User
import com.capstone.enviro.domain.service.UserService
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val viewBinding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnRegister.setOnClickListener {
            val email = viewBinding.etEmail.text.toString()
            val password = viewBinding.etPassword.text.toString()
            val fName = viewBinding.etFirstName.text.toString()
            val lName = viewBinding.etLastName.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(
                    view,
                    "Email or Password must not be empty",
                    Snackbar.LENGTH_SHORT
                ).show()
                Log.i("RegisterFragment", "Email or Password must not be empty")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = auth.currentUser

                        getUserToken { idToken ->
                            if (idToken != null) {
                                firebaseUser?.let {
                                    val userId = it.uid
                                    storeUserData(userId, email, "$fName $lName")
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

        viewBinding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun storeUserData(userId: String, email: String, name: String) {
        val tokenManager = TokenManager(requireContext())
        val userService = RetrofitClient.createService<UserService>(tokenManager)

        // Converting to MongoDB date format
        // MongoDB date format: {"$date": "2023-10-01T12:00:00.000Z"}
        // TODO: Clean up this code
        val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
        isoDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val currentTimeFormatted = isoDateFormat.format(Date())
        val currentTime = TimeStamp(date = currentTimeFormatted)

        val user = User(
            userId = userId,
            email = email,
            name = name,
            profilePicture = "https://example.com/profile.jpg", // TODO: Image Placeholder URL
            roles = listOf("USER"),
            accountStatus = "ACTIVE",
            activityLog = listOf(ActivityLog(action = "REGISTRATION", currentTime)),
            lastLogin = currentTime,
            loginProvider = "FIREBASE",
            createdAt = currentTime,
            updatedAt = currentTime
        )

        val call = userService.createUser(user)
        call.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Log.d("RegisterFragment", "User data stored to MongoDB successfully")
                } else {
                    Log.e("RegisterFragment", "Failed to store user data: HTTP ${response.code()}")
                    try {
                        Log.e("RegisterFragment", "Error body: ${response.errorBody()?.string()}")
                    } catch (e: Exception) {
                        Log.e("RegisterFragment", "Error parsing error body: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("RegisterFragment", "Failed to store user data: ${t.message}")
            }
        })
    }

    // TODO: Duplicate code
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
    }

}
