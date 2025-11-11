package com.example.todoapp


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity()
data class Todo(
    @PrimaryKey(autoGenerate = true)var id: Int,
    var title : String,
    var createdAt : Date
)


fun getFakeTodo(): List<Todo> {
    return listOf(
        Todo(1, "DL assignment submission", Date(System.currentTimeMillis())),
        Todo(2, "SML assignment submission", Date(System.currentTimeMillis())),
        Todo(3, "AWS Bedrock tutorial", Date(System.currentTimeMillis())),
        Todo(4, "Major Project review", Date(System.currentTimeMillis())),
        Todo(5, "write Record", Date(System.currentTimeMillis())),
    )
}
