package com.capstone.enviro.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.enviro.MainActivity
import com.capstone.enviro.databinding.FragmentActivityBinding
import com.capstone.enviro.ui.home.notification.adapter.MyActivitiesItemRecyclerViewAdapter
import com.capstone.enviro.domain.model.Activities
import com.capstone.enviro.utils.*

class ActivityFragment : Fragment() {
    private var _binding: FragmentActivityBinding? =null
    private val binding get() = _binding!!

    private var recyclerView: RecyclerView? = null
    private var adapter: MyActivitiesItemRecyclerViewAdapter? = null

    private val dummyList = listOf(
        Activities("1", "May 5K x Brooks Challenger", parseDate("2023-10-01"), parseDate("2023-10-05"), 50, "Running"),
        Activities("2", "REBOUND by Shimano Live Slow Ride Fast", parseDate("2023-10-02"), parseDate("2023-10-06"), 70, "Cycling"),
        Activities("3", "Half Marathon by Adidas", parseDate("2023-10-03"), parseDate("2023-10-07"), 30, "Running"),
        Activities("4", "Cycling Challenge by Specialized", parseDate("2023-10-04"), parseDate("2023-10-08"), 90, "Cycling"),
        Activities("5", "Yoga Retreat by Lululemon", parseDate("2023-10-05"), parseDate("2023-10-09"), 20, "Yoga"),
        Activities("6", "HIIT Bootcamp by Nike", parseDate("2023-10-06"), parseDate("2023-10-10"), 60, "HIIT"),
        Activities("7", "Pilates Class by Equinox", parseDate("2023-10-07"), parseDate("2023-10-11"), 40, "Pilates")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
         (activity as MainActivity).supportActionBar?.show()

        recyclerView = binding.activitiesRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)

        adapter = MyActivitiesItemRecyclerViewAdapter(dummyList) { selectedItem ->
            val bundle = Bundle().apply {
                putString("activityId", selectedItem.id)
                putString("activityTitle", selectedItem.title)
                putString("activityStartDate", selectedItem.startDate?.toString())
                putString("activityEndDate", selectedItem.endDate?.toString())
                putInt("activityProgress", selectedItem.progress)
                putString("activityType", selectedItem.type)
            }
        }

        recyclerView?.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView?.adapter = null
        recyclerView = null
        adapter = null
        _binding = null
    }
}