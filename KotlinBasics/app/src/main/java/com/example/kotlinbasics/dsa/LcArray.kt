package com.example.kotlinbasics.dsa

import kotlin.math.min


fun main() {
    val arr = intArrayOf(2, 7, 11, 15)
    val target = 9

    val res = Solution.twoSum(arr, target)
    println(res.joinToString())


}


object Solution {

    // https://leetcode.com/problems/two-sum/description/
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val map = hashMapOf<Int, Int>()

        for (i in nums.indices) {
            val comp = target - nums[i]
            if (map.containsKey(comp)) {
                return intArrayOf(map.get(comp)!!, i)
            }
            map.put(nums[i], i)
        }
        return intArrayOf(-1, -1)
    }


    // https://leetcode.com/problems/concatenation-of-array
    fun getConcatenation(nums: IntArray): IntArray {
        return intArrayOf(*nums, *nums)
        // nums + nums
        // nums.plus(nums)
        // intArrayOf(*nums, *nums) or arrayOf(*nums, *nums) if type allows
    }


    // https://leetcode.com/problems/contains-duplicate/
    fun containsDuplicate(nums: IntArray): Boolean {
        val set = hashSetOf<Int>()
        for (num in nums) {
            // if (num in set) return true else set.add(num) // this one or below two lines
            if (set.contains(num)) return true
            set.add(num)
        }
        return false
    }

    // https://leetcode.com/problems/valid-anagram/
    fun isAnagram(s: String, t: String): Boolean {

        if (s.length != t.length) return false

        val map = hashMapOf<Char, Int>()
        for (c in s) {
            map[c] = map.getOrDefault(c, 0) + 1
        }
        for (c in t) {
            val ct = map.getOrDefault(c, 0)
            if (ct == 0) return false
            map[c] = ct - 1
        }
        return true
    }

    // https://leetcode.com/problems/longest-common-prefix/
    fun longestCommonPrefix(strs: Array<String>): String {
        val ans = StringBuilder()
        var minlen = Int.MAX_VALUE
        for (str in strs) {
            minlen = min(minlen, str.length)
        }
        for (i in 0 until minlen) {
            var c = strs[0][i]
            for (str in strs) {
                if (str[i] != c) {
                    c = '*'
                    break
                }
            }
            if (c == '*') break
            else ans.append(c)

        }
        return ans.toString()
    }

    // https://leetcode.com/problems/group-anagrams/
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val encode: (String) -> String = { s ->
            val freq = IntArray(26) { 0 }
            for (c in s.toCharArray()) {
                freq[c - 'a']++
            }
            val sb = StringBuilder()
            for (i in 0 until 26) {
                if (freq[i] > 0) {
                    sb.append(freq[i]).append((i + 'a'.code).toChar())
                }
            }
            sb.toString()
        }
        val map = hashMapOf<String, MutableList<String>>()

        for (str in strs) {
            val code = encode(str)
            if (!map.containsKey(code)) {
                map.put(code, mutableListOf())
            }
            map.get(code)?.add(str)
        }
        val list = mutableListOf<MutableList<String>>()
        for (entry in map.entries) {
            list.add(entry.value)
        }
        return list
    }

    // https://leetcode.com/problems/remove-element/
    fun removeElement(nums: IntArray, `val`: Int): Int {
        var l = nums.size - 1
        var k = 0
        var i = 0
        while (i <= l) {
            if (nums[i] == `val`) {
                while (i < l && nums[l] == `val`) l--
                nums[i] = nums[l].also { nums[l] = nums[i] } // swap
            }
            if (nums[i] != `val`) k++
            i++
        }
        return k
    }

    // https://leetcode.com/problems/majority-element/
    fun majorityElement(nums: IntArray): Int {
        // credits: https://www.cs.utexas.edu/~moore/best-ideas/mjrty/example.html
        var major = nums[0]
        var count = 1
        for (i in 1 until nums.size) {
            if (count == 0) {
                count++;
                major = nums[i];
            } else if (major == nums[i]) {
                count++;
            } else count--;
        }
        return major
    }

    
}