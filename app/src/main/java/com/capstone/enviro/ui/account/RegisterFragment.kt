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
import com.capstone.enviro.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

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
                        Log.d("RegisterFragment", "Registration successful")
                        startActivity(Intent(activity, AccountActivity::class.java))
                        activity?.finish()
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

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)

        auth = FirebaseAuth.getInstance()

        btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val email = findViewById<android.widget.EditText>(R.id.etEmail).text.toString()
            val password = findViewById<android.widget.EditText>(R.id.etPassword).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Log.i("RegisterActivity", "Email or Password must not be empty")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("RegisterActivity", "Registration successful")
                    } else {
                        Log.w("RegisterActivity", "Registration failed", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            Log.i("RegisterActivity", "Invalid email format")
                        } else if (task.exception is FirebaseAuthWeakPasswordException) {
                            Log.i("RegisterActivity", "Weak password")
                        } else {
                            Log.i("RegisterActivity", "Registration failed: ${task.exception?.message}")
                        }
                    }
                }

            startActivity(Intent(this, AccountActivity::class.java))
            finish()
        }
    }*/
}
