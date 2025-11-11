package com.example.todoapp

import androidx.room.Database


@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase {
    companion object {
        const val NAME = "Todo_DB"
    }
    abstract fun getTodoDao() : TodoDao
    
}