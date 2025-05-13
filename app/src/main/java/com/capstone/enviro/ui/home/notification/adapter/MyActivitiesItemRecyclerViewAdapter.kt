package com.capstone.enviro.ui.home.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.enviro.databinding.ItemActivitiesBinding
import com.capstone.enviro.ui.home.notification.model.Activities

class MyActivitiesItemRecyclerViewAdapter(
    private val values: List<Activities>,
    private val onItemClick: (Activities) -> Unit
): RecyclerView.Adapter<MyActivitiesItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemActivitiesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.titleView.text = item.title
        val startDate: Int = item.startDate.split("-")[2].toInt()
        val endDate: Int = item.endDate.split("-")[2].toInt()
        holder.daysLeft.text = "${endDate - startDate} days left"

        holder.progressView.progress = item.progress
        holder.typeView.setImageResource(
            when (item.type) {
//                "Running" -> com.capstone.enviro.R.drawable.ic_running
//                "Cycling" -> com.capstone.enviro.R.drawable.ic_cycling
//                "Yoga" -> com.capstone.enviro.R.drawable.ic_yoga
//                "HIIT" -> com.capstone.enviro.R.drawable.ic_hiit
                "Pilates" -> com.capstone.enviro.R.drawable.ic_profile_96
                else -> com.capstone.enviro.R.drawable.ic_activities_96
            }
        )

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemActivitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.activitiesTitle

        val daysLeft: TextView = binding.activitiesDays
        val progressView: ProgressBar = binding.activitiesProgressBar
        val typeView: ImageView = binding.ivActivitiesIcon

        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "'"
        }
    }
}