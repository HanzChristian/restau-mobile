package com.example.restau.Model

import com.example.restau.Model.Menu.Menu
import java.io.Serializable

data class MenuItemQuantity(
    val menu: Menu,
    var quantity: Int
) : Serializable
