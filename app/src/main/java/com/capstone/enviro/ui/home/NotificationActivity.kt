package com.capstone.enviro.ui.home

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.enviro.R
import com.capstone.enviro.databinding.ActivityNotificationBinding
import com.capstone.enviro.ui.home.notification.MyNotifItemRecyclerViewAdapter
import com.capstone.enviro.ui.home.notification.model.Notification

class NotificationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNotificationBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyNotifItemRecyclerViewAdapter

    // Test dummy data
    private val dummyNotif = listOf(
        Notification("10000", "John has a new message", "Hello, how are you?", "2023-10-01", "10:00 AM"),
        Notification("10001", "Frank added you as a friend", "Let's connect!", "2023-10-02", "11:00 AM"),
        Notification("10002", "Sarah liked your post", "Great post!", "2023-10-03", "12:00 PM"),
        Notification("10003", "Mike commented on your photo", "Nice pic!", "2023-10-04", "1:00 PM"),
        Notification("10004", "Anna shared your post", "Check it out!", "2023-10-05", "2:00 PM"),
        Notification("10005", "Tom sent you a friend request", "Accept it!", "2023-10-06", "3:00 PM"),
        Notification("10006", "Emma mentioned you in a comment", "See it here!", "2023-10-07", "4:00 PM"),
        Notification("10007", "Lucas started following you", "Welcome!", "2023-10-08", "5:00 PM"),
        Notification("10008", "Olivia reacted to your story", "Check it out!", "2023-10-09", "6:00 PM"),
        Notification("10009", "Liam sent you a message", "Let's chat!", "2023-10-10", "7:00 PM"),
        Notification("10010", "Sophia tagged you in a post", "See it here!", "2023-10-11", "8:00 PM"),
        Notification("10011", "Noah invited you to an event", "Join us!", "2023-10-12", "9:00 PM"),
        Notification("10012", "Ava shared a photo with you", "Check it out!", "2023-10-13", "10:00 PM"),
        Notification("10013", "Mason commented on your status", "Nice update!", "2023-10-14", "11:00 PM"),
        Notification("10014", "Isabella sent you a gift", "Thank you!", "2023-10-15", "12:00 AM"),
        Notification("10015", "Ethan started a new group", "Join us!", "2023-10-16", "1:00 AM"),
        Notification("10016", "Charlotte liked your comment", "Great point!", "2023-10-17", "2:00 AM"),
        Notification("10017", "James shared a video with you", "Watch it here!", "2023-10-18", "3:00 AM"),
        Notification("10018", "Amelia sent you a voice message", "Listen to it!", "2023-10-19", "4:00 AM"),
        Notification("10019", "Benjamin invited you to a group", "Join us!", "2023-10-20", "5:00 AM"),
        Notification("10020", "Mia shared a link with you", "Check it out!", "2023-10-21", "6:00 AM"),
        Notification("10021", "Alexander commented on your photo", "Nice shot!", "2023-10-22", "7:00 AM"),
        Notification("10022", "Sofia sent you a sticker", "Cute!", "2023-10-23", "8:00 AM"),
        Notification("10023", "Jackson liked your story", "Great story!", "2023-10-24", "9:00 AM"),
        Notification("10024", "Avery sent you a video", "Watch it here!", "2023-10-25", "10:00 AM"),
        Notification("10025", "Daniel shared a post with you", "Check it out!", "2023-10-26", "11:00 AM"),
        Notification("10026", "Ella commented on your post", "Nice post!", "2023-10-27", "12:00 PM"),
        Notification("10027", "Matthew sent you a photo", "See it here!", "2023-10-28", "1:00 PM"),
        Notification("10028", "Grace liked your video", "Great video!", "2023-10-29", "2:00 PM"),
        Notification("10029", "David sent you a message", "Let's talk!", "2023-10-30", "3:00 PM"),
        Notification("10030", "Chloe shared a story with you", "Check it out!", "2023-10-31", "4:00 PM")
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
                putString("notificationDate", selectedItem.date)
                putString("notificationTime", selectedItem.time)
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