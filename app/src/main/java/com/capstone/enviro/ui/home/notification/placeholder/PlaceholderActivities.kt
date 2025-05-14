package com.capstone.enviro.ui.home.notification.placeholder

import com.capstone.enviro.domain.model.Activities

object PlaceholderActivities {
    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<Activities> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, Activities> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createActivities(i))
        }
    }

    private fun addItem(item: Activities) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createActivities(position: Int): Activities {
        return Activities(
            position.toString(),
            "Item " + position,
            null,
            null,
            0,
            "Running"
        )
    }
}