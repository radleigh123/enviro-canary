package com.capstone.enviro.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.enviro.databinding.ActivityNotificationBinding
import com.capstone.enviro.ui.home.notification.adapter.MyNotifItemRecyclerViewAdapter
import com.capstone.enviro.domain.model.Notification
import com.capstone.enviro.domain.model.TimeStamp
import com.capstone.enviro.utils.getMongoDBDate

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyNotifItemRecyclerViewAdapter

    // Test dummy data
    private val dummyNotif = mutableListOf(
        Notification("10000", "John has a new message", "Hello, how are you?", getMongoDBDate()),
        Notification("10001", "New activity available", "Join us for a fun run!", getMongoDBDate()),
        Notification("10002", "Reminder: Yoga class tomorrow", "Don't forget your mat!", getMongoDBDate()),
        Notification("10003", "New feature update", "Check out the latest updates in the app!", getMongoDBDate()),
        Notification("10004", "New message from Sarah", "Let's catch up soon!", getMongoDBDate()),
        Notification("10005", "Weekly progress report", "Your weekly activity summary is ready!", getMongoDBDate()),
        Notification("10006", "New friend request", "You have a new friend request from Alex!", getMongoDBDate()),
        Notification("10007", "Event reminder", "Don't miss the upcoming event!", getMongoDBDate()),
        Notification("10008", "New comment on your post", "Check out the new comment on your post!", getMongoDBDate()),
        Notification("10009", "System maintenance scheduled", "The system will be down for maintenance.", getMongoDBDate()),
        Notification("10010", "New article published", "Read the latest article on our blog!", getMongoDBDate()),
        Notification("10011", "Password change confirmation", "Your password has been changed successfully.", getMongoDBDate()),
        Notification("10012", "New friend suggestion", "You might know this person!", getMongoDBDate()),
        Notification("10013", "New event created", "Join us for the upcoming event!", getMongoDBDate()),
        Notification("10014", "New poll available", "Participate in the latest poll!", getMongoDBDate()),
        Notification("10015", "New survey available", "We value your feedback!", getMongoDBDate()),
        Notification("10016", "New tutorial available", "Learn something new today!", getMongoDBDate()),
        Notification("10017", "New feature request", "We value your input!", getMongoDBDate())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBarNotif)

        recyclerView = binding.notificationRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // TODO : Replace with actual data
        adapter = MyNotifItemRecyclerViewAdapter(dummyNotif) { selectedItem ->
            // Handle item click
            val bundle = Bundle().apply {
                putString("notificationId", selectedItem.id)
                putString("notificationTitle", selectedItem.title)
                putString("notificationMessage", selectedItem.message)
                putString("notificationDate", selectedItem.date?.toString())
            }
        }
        recyclerView.adapter = adapter
    }

    // Back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}