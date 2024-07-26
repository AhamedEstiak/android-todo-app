package com.estiak.todoapp.inventory.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estiak.todoapp.inventory.data.InventoryItemsRepository
import com.estiak.todoapp.inventory.ui.InventoryEvent
import com.estiak.todoapp.utils.Routes
import com.estiak.todoapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: InventoryItemsRepository
) : ViewModel() {

    private val inventoryId: Int = checkNotNull(savedStateHandle["inventoryIdArg"])

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val uiState: StateFlow<ItemDetailsUiState> = repository.getItemStream(inventoryId)
        .filterNotNull()
        .map {
            ItemDetailsUiState(outOfStock = it.quantity <= 0, itemDetails = it.toItemDetails())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5),
            initialValue = ItemDetailsUiState()
        )

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentItem = uiState.value.itemDetails.toItem()
            if (currentItem.quantity > 0) {
                repository.updateItem(currentItem.copy(quantity = currentItem.quantity - 1))
            }
        }
    }

    private fun deleteInventory() {
        viewModelScope.launch {
            repository.deleteItem(uiState.value.itemDetails.toItem())
            sendUiEvent(UiEvent.PopBackStack)
        }
    }

    fun onEvent(event: InventoryEvent) {
        when (event) {
            is InventoryEvent.OnInventoryEditClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.INVENTORY_EDIT + "?inventoryId=${inventoryId}"))
            }

            is InventoryEvent.OnDeleteInventory -> {
                deleteInventory()
            }

            else -> Unit
        }
    }

}