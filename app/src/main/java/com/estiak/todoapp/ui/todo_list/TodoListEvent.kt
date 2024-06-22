package com.estiak.todoapp.ui.todo_list

import com.estiak.todoapp.data.Todo

sealed class TodoListEvent {

    data object OnAddTodoClick : TodoListEvent()
    data class OnTodoClick(val todo: Todo) : TodoListEvent()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean) : TodoListEvent()
    data class OnDeleteTodoClick(val todo: Todo) : TodoListEvent()
    data object OnUndoTodoClick : TodoListEvent()
}