package com.estiak.todoapp.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "inventories"
)
data class InventoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int
)
