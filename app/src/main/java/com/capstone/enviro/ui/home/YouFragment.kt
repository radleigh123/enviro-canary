package com.capstone.enviro.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.enviro.MainActivity
import com.capstone.enviro.SessionManager
import com.capstone.enviro.databinding.FragmentYouBinding

class YouFragment : Fragment() {
    private var _binding: FragmentYouBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYouBinding.inflate(inflater, container, false)
        // Hide top app bar from MainActivity
        (activity as MainActivity).supportActionBar?.hide()

        updateUserData()

        binding.btnEdit.setOnClickListener {
            val intent = Intent(requireContext(), YouEditActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun updateUserData() {
        // Get from SessionManager
        val context = requireContext()
        val user: Map<String, String?> = SessionManager.getUserSession(context)
        val uid = user["uid"] ?: "NULL".toString()

        val name = user["name"] ?: uid
        val email = user["email"] ?: "NULL".toString()

        binding.tvName.text = name
        binding.tvEmail.text = email
    }
}