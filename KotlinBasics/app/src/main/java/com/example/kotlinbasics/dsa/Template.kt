package com.example.kotlinbasics.dsa

import java.util.StringTokenizer


@JvmField
val INPUT = System.`in`
@JvmField
val OUTPUT = System.out

@JvmField
val _reader = INPUT.bufferedReader()
fun readLine(): String? = _reader.readLine()
fun readLn() = _reader.readLine()!!
@JvmField
var _tokenizer: StringTokenizer = StringTokenizer("")
fun read(): String {
    while (_tokenizer.hasMoreTokens().not()) _tokenizer =
        StringTokenizer(_reader.readLine() ?: return "", " ")
    return _tokenizer.nextToken()
}

fun readInt() = read().toInt()
fun readDouble() = read().toDouble()
fun readLong() = read().toLong()
fun readStrings(n: Int) = List(n) { read() }
fun readLines(n: Int) = List(n) { readLn() }
fun readInts(n: Int) = List(n) { read().toInt() }
fun readIntArray(n: Int) = IntArray(n) { read().toInt() }
fun readDoubles(n: Int) = List(n) { read().toDouble() }
fun readDoubleArray(n: Int) = DoubleArray(n) { read().toDouble() }
fun readLongs(n: Int) = List(n) { read().toLong() }
fun readLongArray(n: Int) = LongArray(n) { read().toLong() }


fun main() {
    val n = readInt()
    var nums = readInts(n)

    nums = nums.map { it * it }

    println(nums.joinToString())

    val list = mutableListOf<Int>()
    list.add(20)
    list.add(30)
    println(list.joinToString())

    val arr = Array<IntArray>(3) { IntArray(3) { 0 } }

    val finalList = listOf<Int>(2, 4)

    val users = mutableListOf<MutableList<String>>()



    users.add(mutableListOf("Nihal", "Jake"))
    users.get(0).add("Tom")


    val list2 = List(5) { it * 2 } // [0,2,4,6,8]

// MutableList with initializer
    val mlist2 = MutableList(3) { 0 } // [0,0,0]


    val threeDArray = Array(2) { Array(3) { Array(4) { 0 } } }
    val threeDList = MutableList(2) { MutableList(3) { MutableList(4) { 0 } } }


    println(users.joinToString())


    val graph = Array(5) { hashSetOf<Int>() }  // graph[0].add(1)

    val json = mutableMapOf<String, MutableMap<String, Int>>()
}