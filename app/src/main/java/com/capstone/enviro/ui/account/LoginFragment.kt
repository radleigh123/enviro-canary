package com.capstone.enviro.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.capstone.enviro.MainActivity
import com.capstone.enviro.R
import com.capstone.enviro.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Temporary... bypass login
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()

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
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        requireActivity().finish() // Close the login activity or Prevent going back to it
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
}
