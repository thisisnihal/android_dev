package com.example.kotlinbasics.dsa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionJava {
    static String encode(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                sb.append(freq[i]).append((char) (i + 'a'));
            }
        }
        return sb.toString();
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            String code = encode(s);
            if (!map.containsKey(code)) {
                map.put(code, new ArrayList<>());
            }
            map.get(code).add(s);
        }
        List<List<String>> list = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }
}
