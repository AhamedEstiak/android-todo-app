package com.estiak.todoapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.estiak.todoapp.AppDatabase
import com.estiak.todoapp.data.TodoRepository
import com.estiak.todoapp.data.TodoRepositoryImpl
import com.estiak.todoapp.inventory.data.InventoryItemsRepository
import com.estiak.todoapp.inventory.data.InventoryItemsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: AppDatabase): TodoRepository {
        return TodoRepositoryImpl(db.todoDao)
    }

    @Provides
    @Singleton
    fun provideInventoryRepository(db: AppDatabase): InventoryItemsRepository {
        return InventoryItemsRepositoryImpl(db.inventoryDao)
    }
}