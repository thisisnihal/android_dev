Kotlin val ≈ Java final (reference can't be reassigned)
Kotlin var ≈ Java non-final reference (modifiable)


🔁 Example Behavior

```Kotlin
val p = Person("Alice")
// p = Person("Bob") ❌ not allowed (val)
```

```Java
final Person p = new Person("Alice");
// p = new Person("Bob"); ❌ not allowed (final)
```

*Primary constructor:*
The primary constructor sets up the initial state of an instance when it's created.

To declare a primary constructor, place it in the class header after the class name:

`class Person constructor(name: String) { /*...*/ }`
If the primary constructor doesn't have any annotations or visibility modifiers, you can omit the constructor keyword:
`class Person(name: String) { /*...*/ }`

The primary constructor can declare parameters as properties. Use the val keyword before the argument name to declare a read-only property and the var keyword for a mutable property:

class Person(val name: String, var age: Int) { /*...*/ }