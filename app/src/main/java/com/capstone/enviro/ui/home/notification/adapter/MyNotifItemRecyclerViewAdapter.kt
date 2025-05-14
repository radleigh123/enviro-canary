package com.capstone.enviro.ui.home.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.enviro.databinding.ItemNotificationBinding
import com.capstone.enviro.domain.model.Notification
import com.capstone.enviro.R

/**
 * [androidx.recyclerview.widget.RecyclerView.Adapter] that can display a [Notification].
 * TODO: Replace the implementation with code for your data type.
 */
class MyNotifItemRecyclerViewAdapter(
    private val values: MutableList<Notification>,
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
        holder.titleView.text = item.title
        holder.messageView.text = item.message

        if (item.isRead) {
            holder.itemView.setBackgroundResource(R.color.transparent)
        } else {
            holder.itemView.setBackgroundResource(R.color.black_20)
        }

        holder.menuButton.setOnClickListener { view ->
            showPopupMenu(view, item)
        }

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.notificationTitle
        val messageView: TextView = binding.notificationMessage
        val menuButton: ImageButton = binding.btnNotificationMenu

        override fun toString(): String {
            return super.toString() + " '" + messageView.text + "'"
        }
    }

    private fun showPopupMenu(view: android.view.View, item: Notification) {
        val popup = PopupMenu(view.context, view)
        popup.menuInflater.inflate(R.menu.item_notification, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_mark_read -> {
                    markNotificationAsRead(item)
                    true
                }

                R.id.action_delete -> {
                    deleteNotification(item)
                    true
                }

                else -> false
            }
        }

        popup.show()
    }

    private fun markNotificationAsRead(notification: Notification) {
        notification.isRead = true

        // Finding the position of the item in the list
        val position = values.indexOf(notification)
        if (position != -1) {
            notifyItemChanged(position)
        }
    }

    private fun deleteNotification(notification: Notification) {
        // Find position of the item
        val position = values.indexOf(notification)
        if (position != -1) {
            // Remove from the dataset
            values.removeAt(position)
            // Notify the adapter that item was removed
            notifyItemRemoved(position)
        }

    }
}