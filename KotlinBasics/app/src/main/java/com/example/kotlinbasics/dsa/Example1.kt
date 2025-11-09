package com.example.kotlinbasics.dsa

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

class Example {
    fun _twoSum(nums: IntArray, target: Int): IntArray {
        val map = HashMap<Int, Int>()
        for (i in nums.indices) {
            val complement = target - nums[i]
            if (map.containsKey(complement)) {
                return intArrayOf(map[complement]!!, i)
            }
            map[nums[i]] = i
        }
        return intArrayOf()
    }

    fun twoSum(nums: IntArray, target: Int): IntArray {
        val map = hashMapOf<Int, Int>()
        for (i in nums.indices) {
            if (map.containsKey(nums[i] - target)) {
                return intArrayOf(map.get(nums[i] - target)!!, i)
            }
        }
        return intArrayOf()
    }
}


fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())

    // Example input for a problem
    val n = st.nextToken().toInt()
    val target = st.nextToken().toInt()
    val arr = IntArray(n)
    val st2 = StringTokenizer(br.readLine())
    for (i in 0 until n) {
        arr[i] = st2.nextToken().toInt()
    }


    // Example usage
    val solver = Example()
    val result = solver.twoSum(arr, target)
    println(result.joinToString())


}