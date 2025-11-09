package com.example.kotlinbasics.concepts

class ClassExample {
}

// private final name
// private int age
// constructor with default value
class Person(val name: String = "John", var age: Int = 30)


// Class with a primary constructor that initializes name and age
class Person2(val name: String, var age: Int) {
    init {
        // Initializer block runs when an instance is created, it is like constructor block
        println("Person created: $name, age $age.")
    }

    // Second initializer block
    init {
        // Runs after the first initializer block
        if (age < 18) {
            println("$name is a minor.")
        } else {
            println("$name is an adult.")
        }
        // A common use case for init blocks is data validation. For example, by calling the require function:
        require(age > 0, { "age must be positive" })
    }

    // Secondary constructor that takes age as a String and converts it to an Int
    // we can have multiple constructor blocks
    constructor(name: String, age: String) : this(name, age.toIntOrNull() ?: 0) {
        println("$name created with converted age: $age")
    }
}

fun main() {
    // Creates an instance using default values
    val person = Person()
    println("Name: ${person.name}, Age: ${person.age}")  // Name: John, Age: 30

    val nihal = Person2("Nihal", 21)

    val samir = Person2("Sameer", "19")



}