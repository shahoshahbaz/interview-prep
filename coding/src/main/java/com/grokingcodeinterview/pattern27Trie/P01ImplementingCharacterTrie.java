package com.grokingcodeinterview.pattern27Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P01ImplementingCharacterTrie {

    public static class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord;

    }

    public static class Trie{

        TrieNode root = new TrieNode();

        public void insert(String word){

            TrieNode curr = root;

            for(char ch: word.toCharArray()){
                curr.children.putIfAbsent(ch,new TrieNode());
                curr = curr.children.get(ch);

            }
            curr.isEndOfWord = true;
        }


        public TrieNode findNode(String s){
            TrieNode curr = root;

            for(char ch: s.toCharArray()){
                if(!curr.children.containsKey(ch)) return null;
                curr = curr.children.get(ch);
            }

            return curr;
        }

        public boolean search(String word){
            TrieNode node = findNode(word);
            return node != null && node.isEndOfWord;
        }

        public boolean startsWidth(String prefix){
            return findNode(prefix)!= null;
        }
        public List<String> autoComplete(String prefix){
            List<String> results = new ArrayList<>();
            TrieNode prefixNode = findNode(prefix);
            if(prefixNode == null) return results;

            dfs(prefixNode, prefix, results);
            return results;
        }

        public void dfs(TrieNode node, String currentWord, List<String> results){
            if(node.isEndOfWord){
                results.add(currentWord);

            }
            for(Map.Entry<Character, TrieNode> entry:node.children.entrySet()){
                dfs(entry.getValue(), currentWord+entry.getKey(), results);
            }
        }

    }

    // I need main with some example
    static void main() {
        // example usage
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // returns true
        System.out.println(trie.search("app"));     // returns false
        System.out.println(trie.startsWidth("app")); // returns true


        // create new tire and insert soma words
        Trie trie2 = new Trie();
        trie2.insert("banana");
        trie2.insert("band");
        trie2.insert("bandana");
        trie2.insert("bandit");
        // search for words
        System.out.println(trie2.search("banana"));   // returns true
        System.out.println(trie2.search("band"));     // returns true
        System.out.println(trie2.search("bandana"));  // returns true
        System.out.println(trie2.search("bandit"));   // returns true
        System.out.println(trie2.search("ban"));      // returns false
        System.out.println(trie2.startsWidth("ban"));  // returns true
        System.out.println(trie2.startsWidth("band")); // returns true
        System.out.println(trie2.startsWidth("bandana")); // returns true
        System.out.println(trie2.startsWidth("bandit"));  // returns true

        System.out.println(trie2.autoComplete("ban").toString());
    }

}
