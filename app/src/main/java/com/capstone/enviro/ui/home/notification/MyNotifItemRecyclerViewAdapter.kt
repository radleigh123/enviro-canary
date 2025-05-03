package com.capstone.enviro.ui.home.notification

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.capstone.enviro.databinding.ItemNotificationBinding
import com.capstone.enviro.ui.home.notification.model.Notification

/**
 * [RecyclerView.Adapter] that can display a [com.capstone.enviro.ui.home.notification.model.Notification].
 * TODO: Replace the implementation with code for your data type.
 */
class MyNotifItemRecyclerViewAdapter(
    private val values: List<Notification>,
    private val onItemClick: (Notification) -> Unit
) : RecyclerView.Adapter<MyNotifItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
//        holder.idView.text = item.id
//        holder.contentView.text = item.title
        holder.titleView.text = item.title
        holder.messageView.text = item.message

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.notificationTitle
        val messageView: TextView = binding.notificationMessage

        override fun toString(): String {
            return super.toString() + " '" + messageView.text + "'"
        }
    }

}