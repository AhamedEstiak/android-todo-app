package com.estiak.todoapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Todo : BottomBarScreen(
        route = "todo",
        title = "Todo",
        icon = Icons.Default.Home
    )
    object SunflowerList : BottomBarScreen(
        route = "todo",
        title = "Todo",
        icon = Icons.Default.List
    )
}