// properties
package com.example.kotlinbasics.concepts

// 1. 
var initialized = 1 // The inferred type is Int
// var outside_property  // it must be init else it will give error 


// 2. we can have custom getter, when we need extra logic, such as validation, formatting, or calculations
class Rectangle(val width: Int, val height: Int) {
    val area: Int
        get() = this.width * this.height
    // A custom getter runs every time the property is accessed
	// val area get() = this.width * this.height // same as above
}


// 3. custom setter runs every time you assign a value to the property, except during initialization.
class Point(var x: Int, var y: Int) {
    var coordinates: String
        get() = "$x,$y"
        set(value) {
            val parts = value.split(",")
            x = parts[0].toInt()
            y = parts[1].toInt()
        }
}


// 4.  To change the visibility of an accessor, use the modifier before the get or set keyword:
class BankAccount(initialBalance: Int) {
    var balance: Int = initialBalance
        // Only the class can modify the balance
        private set 

    fun deposit(amount: Int) {
        if (amount > 0) balance += amount
    }

    fun withdraw(amount: Int) {
        if (amount > 0 && amount <= balance) balance -= amount
    }
}


// 5. To annotate an accessor, use the annotation before the get or set keyword:

// Defines an annotation that can be applied to a getter
@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class Inject

class Service {
    var dependency: String = "Default Service"
        // Annotates the getter
        @Inject get
}


// 6.
class Scoreboard {
    
    // isEmpty property has no backing field
    val isEmpty: Boolean
    	get() = this.size == 0 

    // the score property has a backing field because the setter uses the field keyword
    var score: Int = 0
        set(value) {
            field = value
            // Adds logging when updating the value
            println("Score updated to $field")
        }
     
}




fun main() {
    
    val kotlin = "🙂"
    var allByDefault2 : String // it is fine not to init inside fun scope but mind u you must declare its type
    println(kotlin)

    
    //
    val rect = Rectangle(2, 3)
    println(rect.area)
    
    // 3.
    val location = Point(1, 2)
    println(location.coordinates) // 1,2
    location.coordinates = "10,20"
    println("${location.x}, ${location.y}") // 10, 20
    
    // 4.
    
    // 5.
    val service = Service()
    println(service.dependency) // Default service
    println(service::dependency.getter.annotations) // [@Inject()]
    println(service::dependency.setter.annotations) // []
}
















