package com.example.kotlinbasics.concepts

class InheritanceExample {
}



//All classes in Kotlin have a common superclass, "Any", which is the default superclass for a class with no supertypes declared:

class ExampleClass // Implicitly inherits from "Any"


//"Any" has three methods: equals(), hashCode(), and toString(). Thus, these methods are defined for all Kotlin classes.
// By default, Kotlin classes are final – they can't be inherited. To make a class inheritable, mark it with the open keyword:

open class Base // Class is open for inheritance


fun main() {
    val eg = ExampleClass()
    
}