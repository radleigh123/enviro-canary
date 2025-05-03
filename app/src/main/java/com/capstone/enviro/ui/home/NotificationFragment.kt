package com.capstone.enviro.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.enviro.R
import com.capstone.enviro.ui.home.notification.MyNotifItemRecyclerViewAdapter
import com.capstone.enviro.ui.home.notification.model.Notification

class NotificationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyNotifItemRecyclerViewAdapter

    // Test dummy data
    private val dummyNotif = listOf(
        Notification("10000", "John has a new message", "Hello, how are you?", "2023-10-01", "10:00 AM"),
        Notification("10001", "Frank added you as a friend", "Let's connect!", "2023-10-02", "11:00 AM"),
        Notification("10002", "Sarah liked your post", "Great post!", "2023-10-03", "12:00 PM"),
        Notification("10003", "Mike commented on your photo", "Nice pic!", "2023-10-04", "1:00 PM"),
        Notification("10004", "Anna shared your post", "Check it out!", "2023-10-05", "2:00 PM")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        Log.d("NotificationFragment", "onCreateView called")

        recyclerView = view.findViewById(R.id.notification_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

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
            // TODO: Add type or targetScreen to route accordingly
            // TODO: BROKEN (later on if have time)
            // findNavController().navigate(R.id.navigation_profile, bundle, navOptions)
            // To access the bundle on other side, `val notificationId = arguments?.getString("notificationId")`
        }
        recyclerView.adapter = adapter

        return view
    }

}