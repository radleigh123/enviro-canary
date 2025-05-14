package com.capstone.enviro.ui.home.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.enviro.databinding.ItemActivitiesBinding
import com.capstone.enviro.domain.model.Activities

class MyActivitiesItemRecyclerViewAdapter(
    private val values: MutableList<Activities>,
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
        val startDate = item.startDate.toString()
        val endDate = item.endDate.toString()
        holder.daysLeft.text = "${endDate} ${startDate} days left"

        holder.progressView.progress = item.progress
        holder.typeView.setImageResource(
            when (item.type) {
                "Running" -> com.capstone.enviro.R.drawable.icons8_act1_192
                "Cycling" -> com.capstone.enviro.R.drawable.icons8_act2_192
                "Yoga" -> com.capstone.enviro.R.drawable.icons8_act3_192
                "HIIT" -> com.capstone.enviro.R.drawable.icons8_act4_192
                "Pilates" -> com.capstone.enviro.R.drawable.icons8_act5_192
                else -> com.capstone.enviro.R.drawable.icons8_act1_192
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