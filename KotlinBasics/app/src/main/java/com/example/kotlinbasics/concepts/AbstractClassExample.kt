package com.example.kotlinbasics.concepts

abstract class AbstractClassExample {
}
/*
In Kotlin, abstract classes are classes that can't be instantiated directly.
They are designed to be inherited by other classes which define their actual behavior. This behavior is called an implementation.

An abstract class can declare abstract properties and functions, which must be implemented by subclasses.

Abstract classes can also have constructors.
These constructors initialize class properties and enforce required parameters for subclasses. Declare an abstract class using the abstract keyword:
*/

// Abstract class with a primary constructor that declares name and age
abstract class Person(
    val name: String,
    val age: Int
) {
    // Abstract member
    // Doesn't provide implementation,
    // and it must be implemented by subclasses
    abstract fun introduce()

    // Non-abstract member (has an implementation)
    fun greet() {
        println("Hello, my name is $name.")
    }
}

// Subclass that provides an implementation for the abstract member
class Student(
    name: String,
    age: Int,
    val school: String
) : Person(name, age) {
    override fun introduce() {
        println("I am $name, $age years old, and I study at $school.")
    }
}

fun main() {
    // Creates an instance of the Student class
    val student = Student("Alice", 20, "Engineering University")

    // Calls the non-abstract member
    student.greet()
    // Hello, my name is Alice.

    // Calls the overridden abstract member
    student.introduce()
    // I am Alice, 20 years old, and I study at Engineering University.
}











