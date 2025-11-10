// InheritanceExample
package com.example.kotlinbasics.concepts

class InheritanceExample {
}



// 1. All classes in Kotlin have a common superclass, "Any", which is the default superclass for a class with no supertypes declared:
//"Any" has three methods: equals(), hashCode(), and toString(). Thus, these methods are defined for all Kotlin classes.
class ExampleClass // Implicitly inherits from "Any"


// 2. By default, Kotlin classes are final – they can't be inherited. To make a class inheritable, mark it with the open keyword:
open class Base // Class is open for inheritance



// 3. ------------
open class Base1(p: Int)
// If the derived class has a primary constructor, the base class can (and must) be initialized in that primary constructor according to its parameters.
class Derived(p: Int) : Base1(p)


// 3. example ------
// If the derived class has no primary constructor, 
// then "each" secondary constructor has to initialize the base type using the super keyword or it has to delegate to another constructor which does.
// Note that in this case different secondary constructors can call different constructors of the base type:

// class MyView : View {
//     constructor(ctx: Context) : super(ctx)

//     constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
// }

// 3. example END ----



// 4. -------
// By default, Kotlin classes and their members are final, meaning they cannot be inherited from (for classes) or overridden (for members) unless you explicitly mark them as open:
// Base class with the open keyword to allow inheritance
open class Person(
    val name: String
) {
    // Open function that can be overridden in a subclass
    open fun introduce() {
        println("Hello, my name is $name.")
    }
    // try making the below function open and override this func in child class "Student"
    // without open you cant override in subclass, you can just call it
    fun greet() {
        println("Hi, $name here. How r u doin?")
    }
    
}

// Subclass inheriting from Person and overriding the introduce() function
class Student(
    name: String,
    val school: String
) : Person(name) {
    override fun introduce() {
        println("Hi, I'm $name, and I study at $school.")
    }
    
//     override fun greet() { // might throw error if greet() in super class is not "open" 
//         println("greet")
//     }
}

// 4. END -----


// 5. A member marked override is itself open, so it may be overridden in subclasses. If you want to prohibit re-overriding, use final:
open class Shape {
    open fun draw() { /*...*/ }
    fun fill() { /*...*/ }
    
    open val vertexCount: Int = 0  // overriding mechanism works on properties in the same way that it does on methods.
    
}

class Circle() : Shape() {
    override fun draw() { /*...*/ }
}
open class Rectangle() : Shape() {
    final override fun draw() { /*...*/ } //  now this draw function cant be overriden
    override var vertexCount = 4    //  can override a val property with a var property, but not vice versa. (try changing val with var and vice-versa in base & super)
}

// 5. END ------


// 6. using interface

interface ShapeI {
    val vertexCount: Int
}

class Square(override val vertexCount: Int = 4) : ShapeI // Always has 4 vertices

class Polygon : ShapeI {
    override var vertexCount: Int = 0  // Can be set to any number later
}


// 7. Calling the superclass implementation
// 7.1 using super keyword ----
open class Rectangle_1 {
    open fun draw() { println("Drawing a rectangle") }
    val borderColor: String get() = "black"
}

class FilledRectangle : Rectangle_1() {
    override fun draw() {
        super.draw()
        println("Filling the rectangle")
    }

    val fillColor: String get() = super.borderColor
}

// 7.2 inside inner class, accessing the superclass of the outer class is done using the super keyword qualified with the outer class name: super@Outer
class FilledRectangle_1: Rectangle_1() {
    override fun draw() {
        val filler = Filler()
        filler.drawAndFill()
    }

    inner class Filler {
        fun fill() { println("Filling") }
        fun drawAndFill() {
            super@FilledRectangle_1.draw() // Calls Rectangle's implementation of draw()
            fill()
            println("Drawn a filled rectangle with color ${super@FilledRectangle_1.borderColor}") // Uses Rectangle's implementation of borderColor's get()
        }
    }
}



// 8. if a class inherits multiple implementations of the same member from its immediate superclasses
// To denote the supertype from which the inherited implementation is taken, 
// use super qualified by the supertype name in angle brackets, such as super<Base>:

open class Rectangle_3 {
    open fun draw() { /* ... */ }
}

interface Polygon_3 {
    fun draw() { /* ... */ } // interface members are 'open' by default
}

class Square_3() : Rectangle_3(), Polygon_3 {
    // The compiler requires draw() to be overridden:
    override fun draw() {
        super<Rectangle_3>.draw() // call to Rectangle.draw()
        super<Polygon_3>.draw() // call to Polygon.draw()
    }
}


// MAIN -----------
fun main() {
    val eg = ExampleClass()
    
    val nihal = Student("Nihal", "Don Bosco Academy")
    nihal.introduce()
    nihal.greet()
    
    
    
    //
    val rect = Rectangle()
    println(rect.vertexCount)
    
    //
    val square = Square()
    println(square.vertexCount)
    
}
