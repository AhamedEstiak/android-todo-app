package com.estiak.todoapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.estiak.todoapp.inventory.ui.inventory_list.InventoryListScreen
import com.estiak.todoapp.inventory.ui.item.AddInventoryScreen
import com.estiak.todoapp.inventory.ui.item.InventoryDetailsScreen
import com.estiak.todoapp.inventory.ui.item.InventoryEditScreen
import com.estiak.todoapp.ui.add_edit_todo.AddEditTodoScreen
import com.estiak.todoapp.sunflower.ui.HomeScreen
import com.estiak.todoapp.ui.todo_list.TodoListScreen
import com.estiak.todoapp.utils.Routes

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@Composable
fun TodoNavGraph() {
    val items = listOf(
        BottomNavigationItem(
            title = "Todo",
            route = Routes.TODO_LIST,
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Inventory",
            route = Routes.INVENTORY_LIST,
            selectedIcon = Icons.Filled.List,
            unSelectedIcon = Icons.Outlined.List,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Sunflowers",
            route = Routes.SUNFLOWER,
            selectedIcon = Icons.Filled.List,
            unSelectedIcon = Icons.Outlined.List,
            hasNews = false,
        ),
    )
    val navController: NavHostController = rememberNavController()
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(route = item.route)
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // re selecting the same item
                                launchSingleTop = true
                                // Restore state when re selecting a previously selected item
                                restoreState = true
                            }
                        },
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedIndex) {
                                    item.selectedIcon
                                } else item.unSelectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Routes.TODO_LIST
        ) {
            composable(Routes.TODO_LIST) {
                TodoListScreen(
                    onNavigate = {
                        navController.navigate(it.route)
                    }
                )
            }
            composable(
                route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                arguments = listOf(
                    navArgument(name = "todoId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) {
                AddEditTodoScreen(
                    onPopBackStack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Routes.INVENTORY_LIST) {
                InventoryListScreen(
                    onNavigate = {
                        navController.navigate(it.route)
                    }
                )
            }
            composable(
                route = Routes.INVENTORY_ENTRY,
            ) {
                AddInventoryScreen(
                    onPopBackStack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = Routes.INVENTORY_DETAILS + "?inventoryId={inventoryIdArg}",
                arguments = listOf(
                    navArgument(name = "inventoryIdArg") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) {
                InventoryDetailsScreen(
                    onNavigate = {
                        navController.navigate(it.route)
                    },
                    onPopBackStack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = Routes.INVENTORY_EDIT + "?inventoryId={inventoryIdArg}",
                arguments = listOf(
                    navArgument(name = "inventoryIdArg") {
                        type = NavType.IntType
                    }
                )
            ) {
                InventoryEditScreen(
                    onPopBackStack = {
                        navController.popBackStack()
                    }
                )
            }
            // Sunflower feature routes
            composable(Routes.SUNFLOWER) {
                HomeScreen(
                    onNavigate = {
                        navController.navigate(it.route)
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavGraph() {
}