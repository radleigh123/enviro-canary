package com.capstone.enviro.ui.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.capstone.enviro.R
import com.capstone.enviro.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnNext.setOnClickListener {
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

            // Store to pass into another fragment
            val bundle = Bundle()
            bundle.putString("email", email)
            bundle.putString("password", password)
            bundle.putString("firstName", fName)
            bundle.putString("lastName", lName)
            findNavController().navigate(R.id.action_RegisterFragment_to_Register2Fragment, bundle)
        }

        viewBinding.btnLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
