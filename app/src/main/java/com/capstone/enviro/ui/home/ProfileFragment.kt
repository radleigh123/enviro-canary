package com.capstone.enviro.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.capstone.enviro.R
import com.capstone.enviro.SessionManager
import com.capstone.enviro.data.remote.RetrofitClient
import com.capstone.enviro.data.remote.TokenManager
import com.capstone.enviro.databinding.FragmentProfileBinding
import com.capstone.enviro.domain.service.UserService

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        updateUserData()

        return binding.root
    }

    private fun updateUserData() {
        // Get from SessionManager
        val context = requireContext()
        val user: Map<String, String?> = SessionManager.getUserSession(context)
        val uid = user["uid"] ?: "NULL".toString()
        val name = user["name"] ?: "NULL".toString()
        val email = user["email"] ?: "NULL".toString()

        binding.tvTopCardId.text = uid
        binding.tvTopCardName.text = name
        binding.tvTopCardEmail.text = email
    }
}