package com.estiak.todoapp.inventory.ui.inventory_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estiak.todoapp.inventory.data.InventoryItemsRepository
import com.estiak.todoapp.inventory.ui.InventoryEvent
import com.estiak.todoapp.utils.Routes
import com.estiak.todoapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryListViewModel @Inject internal constructor(
    repository: InventoryItemsRepository
) : ViewModel() {

    val inventories = repository.getAllItemsStream()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun onEvent(event: InventoryEvent) {
        when (event) {
            is InventoryEvent.OnAddInventory -> {
                sendUiEvent(UiEvent.Navigate(Routes.INVENTORY_ENTRY))
            }

            is InventoryEvent.OnInventoryClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.INVENTORY_DETAILS + "?inventoryId=${event.inventoryId}"))
            }

            else -> Unit
        }
    }

}