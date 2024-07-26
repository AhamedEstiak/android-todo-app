package com.estiak.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.estiak.todoapp.data.Todo
import com.estiak.todoapp.data.TodoDao
import com.estiak.todoapp.inventory.dao.InventoryItemDao
import com.estiak.todoapp.inventory.data.InventoryItem

@Database(
    entities = [
        Todo::class,
        InventoryItem::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val todoDao: TodoDao
    abstract val inventoryDao: InventoryItemDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "todo_db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}