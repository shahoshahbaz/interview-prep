package com.grokingcodeinterview.pattern27Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Insert: O(L) — where L = length of the word. You do exactly one pass through the characters, each step is O(1) (HashMap get/put).
Search / startsWith: O(L) — same reasoning, one pass through the characters, each lookup O(1).
Autocomplete (bonus, since it's fresh): O(P + K) where P = prefix length (walking down) and K = total characters across all matched words (DFS collecting them).

Space complexity:

Insert: O(L) worst case per word (if no shared prefix, you create L new nodes) — O(1) if the entire word path already exists as a prefix of another word.
Overall trie: O(N·L) worst case (N words, avg length L, no shared prefixes) — but shared prefixes reduce this significantly in practice
 */
public class TrieNodePattern {
    public static class TrieNode{
      Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord;

    }

    public  static class Trie{
        TrieNode root = new TrieNode();

        public void insert(String word){
            TrieNode curr = root;
            for(char c: word.toCharArray()){
                curr.children.putIfAbsent(c, new TrieNode());
                curr = curr.children.get(c);
            }
            curr.isEndOfWord = true;
        }

        boolean search(String word){
            TrieNode node = findNode(word);
            return node !=null && node.isEndOfWord;
        }
        public boolean startsWith(String prefix){
            return findNode(prefix) != null;
        }

        public TrieNode findNode( String word){
            TrieNode curr = root;
            for(char c: word.toCharArray()){
                if(!curr.children.containsKey(c)) return null;
                curr = curr.children.get(c); // Move to the next node
            }
            return curr;
        }

        public List<String> autocomplete(String prefix){
            List<String> results = new ArrayList<>();

            TrieNode prefixNode = findNode(prefix);
            if(prefixNode == null) return results;
            dfs(prefixNode, prefix, results);
            return results;


        }

        private void dfs(TrieNode node, String currWord, List<String> results){
            if(node.isEndOfWord){
                results.add(currWord);
            }

            for(Map.Entry<Character, TrieNode> entry: node.children.entrySet()){

                // entry.getValue(): the child TrieNode
                // entry.getKey(): the character associated with that child node
                dfs(entry.getValue(), currWord+ entry.getKey(), results);

            }
        }

        public void delete(String word){
            delete(root, word, 0);
        }
        private boolean delete(TrieNode node, String word, int index){
            // base case: consumed the whole word, we are standing on its lastNode
            if(index == word.length()){
                if(!node.isEndOfWord) return false; //word never inserted
                node.isEndOfWord = false; // unmaked it
                return node.children.isEmpty();
            }

            char c= word.charAt(index);
            TrieNode child = node.children.get(c);
            if(child == null) return false; // word not in trie

            boolean shouldBeDeleted= delete(child, word, index + 1);
            if(shouldBeDeleted){
                node.children.remove(c);
                return node.children.isEmpty() && !node.isEndOfWord;
            }



            return false;

        }
        public static void main(String[] args) {
                Trie trie = new Trie();
                trie.insert("cat");
                trie.insert("car");
                trie.insert("cart");

                System.out.println("input: search(\"cat\") | output: " + trie.search("cat") + " | expected: true");
                System.out.println("input: search(\"ca\") | output: " + trie.search("ca") + " | expected: false");
                System.out.println("input: search(\"dog\") | output: " + trie.search("dog") + " | expected: false");
                System.out.println("input: startsWith(\"ca\") | output: " + trie.startsWith("ca") + " | expected: true");
                System.out.println("input: startsWith(\"do\") | output: " + trie.startsWith("do") + " | expected: false");

                trie.insert("ca");
                System.out.println("input: search(\"ca\") after insert | output: " + trie.search("ca") + " | expected: true");

                System.out.println("input: autocomplete(\"ca\") | output: " + trie.autocomplete("ca") + " | expected: [ca, cat, car, cart] (order may vary)");
                System.out.println("input: autocomplete(\"xyz\") | output: " + trie.autocomplete("xyz") + " | expected: [] (empty list)");
            }
        }


    }



