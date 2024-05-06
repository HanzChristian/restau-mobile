package com.example.restau.Model

import com.example.restau.Model.Menu.Menu

object ShoppingCart {
    private var total: Double = 0.0
    private val items: MutableMap<Menu, Int> = mutableMapOf()

    fun getTotal(): Double {
        return total
    }

    fun addItem(item: Menu) {
        val count = items.getOrDefault(item, 0)
        items[item] = count + 1
        total += item.hargaMenu.toDouble()
    }

    fun getItems(): Map<Menu, Int> {
        return items
    }

    fun removeItem(item: Menu) {
        val count = items.getOrDefault(item, 0)
        if (count > 1) {
            items[item] = count - 1
        } else {
            items.remove(item)
        }
        total -= item.hargaMenu.toDouble()
    }

    fun getQuantity(item: Menu): Int {
        return items.getOrDefault(item, 0)
    }

    fun clearAllItems() {
        items.clear()
        total = 0.0
    }
}