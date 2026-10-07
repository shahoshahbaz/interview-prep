package com.grokingcodeinterview.pattern27Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 /**
 * Search Suggestions / Autocomplete System — Part 1: Basic Autocomplete
 *
 * You're given a stream of search log entries (phrases users have searched for,
 * tokenized by WORD, not character).
 *
 * Implement a word-level trie supporting:
 *
 * insert(String phrase)        - Inserts a phrase into the trie, splitting on spaces.
 * autocomplete(String prefix)  - Given a word-level prefix, return ALL previously
 *                                 inserted phrases that start with that prefix.
 *
 * Example:
 *   Logs inserted:
 *     "rice chicken"
 *     "rice chicken fries"
 *     "rice chicken fries with beans"
 *     "fried chicken with egg"
 *     "fried chicken with beans"
 *
 *   autocomplete("rice chicken")
 *     -> ["rice chicken", "rice chicken fries", "rice chicken fries with beans"]
 *
 *   autocomplete("fried chicken with")
 *     -> ["fried chicken with egg", "fried chicken with beans"]
 *
 *   autocomplete("spicy")
 *     -> []   (no phrase starts with this prefix)
 */

public class P06SearchSuggestions {

    public static class TrieNode{
        Map<String, TrieNode> children = new HashMap<>();
        boolean endOfPhrase;
    }

    public static class Trie{
        TrieNode root = new TrieNode();
        public void insert(String phrase){
            TrieNode curr = root;

            for(String str: phrase.split(" ")){
                curr.children.putIfAbsent(str, new TrieNode());
                curr = curr.children.get(str);

            }
            curr.endOfPhrase= true;

        }

        public TrieNode find(String phrase){

            TrieNode curr = root;

            for (String str: phrase.split(" ")){
                if(!curr.children.containsKey(str)) return null;
                curr = curr.children.get(str);
            }
            return curr;
        }

        public List<String> autoComplete(String phrase){
            List<String> list = new ArrayList<>();
            if(phrase == null || phrase.isEmpty()) return list;

            TrieNode prefixNode = find(phrase);
            if(prefixNode == null) return list;
            dfs(prefixNode, phrase, list);
            return list;
        }

        private void dfs(TrieNode prefixNode, String phrase, List<String> list){
            if(prefixNode.endOfPhrase){
                list.add(phrase);
            }
            for(Map.Entry<String, TrieNode> entry: prefixNode.children.entrySet()){
                dfs(entry.getValue(), phrase +" " + entry.getKey() , list);
            }

        }
    }
    static void main(String[] args) {
        Trie wordTrie = new Trie();
        wordTrie.insert("hello world");
        wordTrie.insert("hello there");
        wordTrie.insert("hello everyone");
        wordTrie.insert("hi there");
        wordTrie.insert("hi everyone");

        // Test autoComplete
        List<String> results = wordTrie.autoComplete("hello");
        System.out.println("Auto-complete results for 'hello': " + results);
        results = wordTrie.autoComplete("hi");
        System.out.println("Auto-complete results for 'hi': " + results);

        wordTrie =  new Trie();
        wordTrie.insert("rice chicken");
        wordTrie.insert("rice chicken fries");
        wordTrie.insert("rice chicken fries with beans");
        wordTrie.insert("fried chicken with egg");
        wordTrie.insert("fried chicken with beans");
        System.out.println("Auto-complete results for 'rice chicken': " + wordTrie.autoComplete("rice chicken"));
        System.out.println("Auto-complete results for 'fried chicken': " + wordTrie.autoComplete("fried chicken"));
        System.out.println("Auto-complete results for 'fried': " + wordTrie.autoComplete("fried"));
        System.out.println("Auto-complete results for 'rice': " + wordTrie.autoComplete("rice"));
    }

}
