package com.estiak.todoapp.inventory.data

import com.estiak.todoapp.inventory.dao.InventoryItemDao
import kotlinx.coroutines.flow.Flow

class InventoryItemsRepositoryImpl(
    private val itemDao: InventoryItemDao
) : InventoryItemsRepository {

    override fun getAllItemsStream(): Flow<List<InventoryItem>> = itemDao.getAllItems()

    override fun getItemStream(id: Int): Flow<InventoryItem?> = itemDao.getItem(id)

    override suspend fun insertItem(item: InventoryItem) = itemDao.insert(item)

    override suspend fun deleteItem(item: InventoryItem) = itemDao.delete(item)

    override suspend fun updateItem(item: InventoryItem) = itemDao.update(item)
}