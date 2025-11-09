package com.example.kotlinbasics.concepts

class CompanionObject {
}


//In Kotlin, each class can have a companion object.
// Companion objects are a type of object declaration that allows you to access its members using the class name without creating a class instance.
//
//Suppose you need to write a function that can be called without creating an instance of a class,
// but it is still logically connected to the class (such as a factory function).
// In that case, you can declare it inside a companion object declaration within the class:

// Class with a primary constructor that declares the name property
class Person(
    val name: String
) {
    // Class body with a companion object
    companion object {
        fun createAnonymous() = Person("Anonymous")
    }
}

fun main() {
    // Calls the function without creating an instance of the class
    val anonymous = Person.createAnonymous()
    println(anonymous.name)
    // Anonymous
}