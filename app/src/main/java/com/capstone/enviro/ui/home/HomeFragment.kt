package com.capstone.enviro.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.enviro.MainActivity
import com.capstone.enviro.SessionManager
import com.capstone.enviro.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.show()

        updateUI()

        return binding.root
    }

    private fun updateUI() {
        val context = requireContext()
        val user: Map<String, String?> = SessionManager.getUserSession(context)

        val name = user["name"] ?: "NULL".toString()

        binding.heroGreeting.text = "Hello, $name!"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}