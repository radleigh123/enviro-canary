package com.capstone.enviro.ui.home.notification.placeholder

import com.capstone.enviro.domain.model.Notification
import com.capstone.enviro.utils.parseDate
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<Notification> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, Notification> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createNotification(i))
        }
    }

    private fun addItem(item: Notification) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createNotification(position: Int): Notification {
        return Notification(position.toString(), "Item " + position, makeMessage("Details about Item: $position"), parseDate("2023-10-01"), false)
    }

    private fun makeMessage(message: String): String {
        val builder = StringBuilder()
        builder.append("Message: ").append(message)
        return builder.toString()
    }
}