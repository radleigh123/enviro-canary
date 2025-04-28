package com.capstone.enviro.ui.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.capstone.enviro.R
import com.capstone.enviro.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val viewBinding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        viewBinding.btnRequest.setOnClickListener {
            val email = viewBinding.etEmail.text.toString().trim()

            if (email.isEmpty()) {
                viewBinding.etEmail.error = "Please enter your email"
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Password reset link sent to your email",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("ForgotPasswordFragment", "Email sent.")
                        findNavController().navigate(R.id.action_ForgotPasswordFragment_to_LoginFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to send password reset email",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("ForgotPasswordFragment", "Error: ${task.exception?.message}")
                    }
                }
        }

        viewBinding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_ForgotPasswordFragment_to_LoginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
