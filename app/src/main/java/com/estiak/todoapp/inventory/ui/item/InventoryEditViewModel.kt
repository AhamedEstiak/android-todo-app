package com.estiak.todoapp.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estiak.todoapp.inventory.data.InventoryItemsRepository
import com.estiak.todoapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryEditViewModel @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: InventoryItemsRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var itemUiState by mutableStateOf(ItemUiState())

    private val inventoryId: Int = checkNotNull(savedStateHandle["inventoryIdArg"])

    init {
        viewModelScope.launch {
            itemUiState = repository.getItemStream(inventoryId)
                .filterNotNull()
                .first()
                .toItemUiState(true)
        }
    }

    suspend fun updateItem() {
        if (validateInput(itemUiState.itemDetails)) {
            repository.updateItem(itemUiState.itemDetails.toItem())
        }
    }

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
