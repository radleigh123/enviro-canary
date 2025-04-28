package com.capstone.enviro.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        // Set up your login logic here
        // For example, you can set up click listeners for buttons
        viewBinding.btnLogin.setOnClickListener {
            // temp
            /*val i = Intent(requireContext(), HomeActivity::class.java)
            startActivity(i)*/

            val email = viewBinding.etEmail.text.toString()
            val password = viewBinding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Snackbar.make(view, "Email or Password must not be empty", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_LoginFragment_to_HomeActivity)
//                        finish()
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
