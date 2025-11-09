package com.example.kotlinbasics.dsa


fun main() {
    CrashCourse2.solve()
}

object CrashCourse2 {

    fun solve() {
        println("---- 1. Iterator with List ----")
        val list = mutableListOf("A", "B", "C")
        val it = list.iterator()

        while (it.hasNext()) {
            val value = it.next()
            println(value)
            if (value == "B") it.remove() // safe removal
        }
        println(list) // [A, C]

        println("---- 2. Iterator with Set ----")
        val set = mutableSetOf(10, 20, 30)
        val setIt = set.iterator()

        while (setIt.hasNext()) {
            val value = setIt.next()
            if (value == 20) setIt.remove()
        }
        println(set) // [10, 30]

        println("---- 3. Iterator with Map ----")
        val map = mutableMapOf("Alice" to 25, "Bob" to 30, "Charlie" to 35)

        // Keys
        map.keys.iterator().forEach { println("Key: $it") }

        // Values
        map.values.iterator().forEach { println("Value: $it") }

        // Entries (safe removal)
        val entryIt = map.entries.iterator()
        while (entryIt.hasNext()) {
            val (k, v) = entryIt.next()
            if (k == "Bob") entryIt.remove()
            println("$k -> $v")
        }
        println(map)

        println("---- 4. ListIterator (bidirectional) ----")
        val list2 = mutableListOf("X", "Y", "Z")
        val lit = list2.listIterator()

        while (lit.hasNext()) {
            val value = lit.next()
            if (value == "Y") {
                lit.set("YY") // update
                lit.add("NEW") // insert after YY
            }
        }
        println(list2) // [X, YY, NEW, Z]

        while (lit.hasPrevious()) {
            println("Backward: ${lit.previous()}")
        }

        println("---- 5. forEachRemaining ----")
        val it2 = list2.iterator()
        it2.forEachRemaining { println("Remaining: $it") }

        println("---- 6. for-each (syntactic sugar) ----")
        for (s in list2) println("for-each: $s")
    }
}
