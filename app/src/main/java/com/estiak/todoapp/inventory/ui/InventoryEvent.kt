package com.estiak.todoapp.inventory.ui

import com.estiak.todoapp.inventory.data.InventoryItem

sealed class InventoryEvent {

    data object OnAddInventory : InventoryEvent()
    data class OnInventoryClick(val inventoryId: Int) : InventoryEvent()
    data object OnInventoryEditClick : InventoryEvent()
    data object OnDeleteInventory : InventoryEvent()
}