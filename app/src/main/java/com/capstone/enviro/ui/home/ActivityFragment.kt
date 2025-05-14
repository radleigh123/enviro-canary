package com.capstone.enviro.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.enviro.MainActivity
import com.capstone.enviro.databinding.DialogAddActivityBinding
import com.capstone.enviro.databinding.FragmentActivityBinding
import com.capstone.enviro.ui.home.notification.adapter.MyActivitiesItemRecyclerViewAdapter
import com.capstone.enviro.domain.model.Activities
import com.capstone.enviro.utils.*
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import java.util.UUID
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.android.material.datepicker.MaterialDatePicker
import com.capstone.enviro.R
import com.capstone.enviro.utils.parseDate

class ActivityFragment : Fragment() {
    private var _binding: FragmentActivityBinding? =null
    private val binding get() = _binding!!

    private var recyclerView: RecyclerView? = null
    private var adapter: MyActivitiesItemRecyclerViewAdapter? = null
    private val dummyList = mutableListOf(
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

        binding.fabAddActivity.setOnClickListener {
            showAddActivityDialog()
        }

        return binding.root
    }

    private fun showAddActivityDialog() {
        val dialogBinding = DialogAddActivityBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                // Don't handle click here, we'll override this later
            }
            .setNegativeButton("Cancel", null)
            .create()

        var startDate: Calendar? = null
        var endDate: Calendar? = null

        // Setup start date picker
        dialogBinding.editStartDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Start Date")
                .setSelection(startDate?.timeInMillis ?: MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                startDate = Calendar.getInstance().apply { timeInMillis = selection }
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                dialogBinding.editStartDate.setText(dateFormat.format(startDate?.time))
            }
            datePicker.show(parentFragmentManager, "START_DATE_PICKER")
        }

        // Setup end date picker
        dialogBinding.editEndDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select End Date")
                .setSelection(endDate?.timeInMillis ?: MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                endDate = Calendar.getInstance().apply { timeInMillis = selection }
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                dialogBinding.editEndDate.setText(dateFormat.format(endDate?.time))
            }
            datePicker.show(parentFragmentManager, "END_DATE_PICKER")
        }

        dialog.show()

        // Override positive button to validate before dismissing
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val title = dialogBinding.editActivityTitle.text.toString()
            val startDateStr = dialogBinding.editStartDate.text.toString()
            val endDateStr = dialogBinding.editEndDate.text.toString()
            val progressStr = dialogBinding.editProgress.text.toString()
            val type = dialogBinding.editType.text.toString()

            if (title.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty() ||
                progressStr.isEmpty() || type.isEmpty()) {
                Snackbar.make(dialogBinding.root, "Please fill all fields", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val progress = progressStr.toIntOrNull() ?: 0
            if (progress < 0 || progress > 100) {
                Snackbar.make(dialogBinding.root, "Progress must be between 0 and 100", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create new activity and add to list
            val newActivity = Activities(
                id = UUID.randomUUID().toString(),
                title = title,
                startDate = parseDate(startDateStr),
                endDate = parseDate(endDateStr),
                progress = progress,
                type = type
            )

            dummyList.add(newActivity)
            adapter?.notifyItemInserted(dummyList.size - 1)
            dialog.dismiss()

            Snackbar.make(binding.root, "Activity added successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView?.adapter = null
        recyclerView = null
        adapter = null
        _binding = null
    }

}