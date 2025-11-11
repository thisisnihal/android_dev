package com.example.todoapp

import java.util.Date

object TodoManager {
    private val todoList = mutableListOf<Todo>()

    fun getAllTodo(): List<Todo> {
        return todoList
    }

    fun addTodo(title: String) {
        todoList.add(
            Todo(
                System.currentTimeMillis().toInt(),
                title,
                Date(System.currentTimeMillis())
            )
        )
    }

    fun deleteTodo(id: Int) {
        todoList.removeIf {
            it.id == id
        }
    }
}