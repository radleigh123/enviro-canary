package com.capstone.enviro.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.capstone.enviro.MainActivity
import com.capstone.enviro.R
import com.capstone.enviro.SessionManager
import com.capstone.enviro.data.remote.RetrofitClient
import com.capstone.enviro.data.remote.TokenManager
import com.capstone.enviro.databinding.FragmentLoginBinding
import com.capstone.enviro.domain.model.User
import com.capstone.enviro.domain.service.UserService
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A login [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {
    // This fragment is used for the login screen
    // You can add your login logic here
    // For example, you can use ViewModel to handle login data and authentication
    private var _binding: FragmentLoginBinding? = null
    private val viewBinding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        tokenManager = TokenManager(requireContext())

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Temporary... bypass login
//        startActivity(Intent(requireContext(), MainActivity::class.java))
//        requireActivity().finish()

        viewBinding.btnLogin.setOnClickListener {
            val email = viewBinding.etEmail.text.toString()
            val password = viewBinding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(view, "Email or Password must not be empty", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        getUserToken { idToken ->
                            if (idToken != null) {
                                tokenManager.saveToken(idToken)

                                auth.currentUser?.let {
                                    val uid = it.uid
                                    val email = it.email

                                    SessionManager.saveUserIdSession(requireContext(), uid.toString())
                                    Log.d("LoginFragment", "User ID: $uid, Email: $email")
                                    saveLoggedUser(email.toString())
                                }

                                Log.d("LoginFragment", "Login successful")
                                val intent = Intent(requireContext(), MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                requireActivity().finish() // Close the login activity or Prevent going back to it
                            } else {
                                Snackbar.make(view, "Failed to get authentication token", Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        val errorMessage = task.exception?.message
                        Snackbar.make(view, errorMessage ?: "Login failed", Snackbar.LENGTH_SHORT).show()
                    }
                }
        }

        viewBinding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_ForgotPasswordFragment)
        }

        viewBinding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun saveLoggedUser(email: String) {
        val tokenManager = TokenManager(requireContext())
        val userService = RetrofitClient.createService<UserService>(tokenManager)

        var user: User? = null

        val call = userService.getUserByEmail(email)
        call.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                // TODO: TODO("SAMPLE FORCED COMMENT")
                if (response.isSuccessful) {
                    user = response.body()
                    Log.d("LoginFragment", "User retrieved: $user")
                    SessionManager.saveUserSession(requireContext(), user)
                } else {
                    Log.e("LoginFragment", "Failed to retrieve user: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("LoginFragment", "Error retrieving user: ${t.message}")
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
