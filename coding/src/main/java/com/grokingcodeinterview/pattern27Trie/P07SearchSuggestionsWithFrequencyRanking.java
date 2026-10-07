package com.grokingcodeinterview.pattern27Trie;

import java.util.*;

public class P07SearchSuggestionsWithFrequencyRanking {


    /**
     * Ranked Autocomplete — Option A (Naive: DFS + Sort at Query Time)
     *
     * Same requirements as the overall Part 2 problem, but keep it SIMPLE —
     * no per-node caching, no heap maintained during insert. Just get it
     * correct first.
     *
     * insert(String phrase, int frequency)
     *     - Inserts a phrase into the trie, splitting on spaces.
     *     - Stores frequency only on the end-of-phrase node.
     *     - If the phrase is inserted again (live update), frequency should
     *       be overwritten, not duplicated (there's only one node per phrase,
     *       so this should fall out naturally — no special handling needed
     *       here, unlike Option B's heap).
     *
     * topKByFrequency(String prefix, int k)
     *     - Given a word-level prefix, DFS from that prefix's node to collect
     *       ALL matching phrases with their frequencies.
     *     - Sort by frequency descending, tie-break lexicographically ascending.
     *     - Return the top k.
     *
     * Example:
     *   Logs inserted:
     *     "rice", 1000
     *     "rice fried", 500
     *     "rice fried chicken", 200
     *     "rice fried chicken with beans", 50
     *
     *   topKByFrequency("rice", 3)
     *     -> ["rice", "rice fried", "rice fried chicken"]
     *
     *   insert("rice fried chicken with beans", 900)   // live update
     *   topKByFrequency("rice", 3)
     *     -> ["rice fried chicken with beans", "rice", "rice fried"]
     *
     *   topKByFrequency("xyz", 3)
     *     -> []
     *
     * Complexity target:
     *   insert:            O(L)              where L = number of words in phrase
     *   topKByFrequency:   O(P + M + M log M) where P = prefix walk length,
     *                       M = number of matching phrases under that prefix
     *       why O(P + M + M log M) ? because we have to walk down the prefix (O(P)),
     *       then DFS to collect all matches (O(M)),
     *       then sort them (O(M log M)).
     */

    public static class TrieNode{
        Map<String, TrieNode> children = new HashMap<>();
        boolean isEndOfPhrase;
        int freq;

    }

    public class Trie{
        TrieNode root = new TrieNode();

        public void insert(String phrase, int freq){
            if(phrase == null || phrase.isEmpty()) return;
            TrieNode curr = root;

            for(String str: phrase.split(" ")){
                curr.children.putIfAbsent(str, new TrieNode());
                curr = curr.children.get(str);


            }
            curr.isEndOfPhrase = true;
            curr.freq = freq;
        }

        public TrieNode findNode(String prefix){
            TrieNode curr = root;

            for(String str: prefix.split(" ")){
                if(!curr.children.containsKey(str)) return null;
                curr = curr.children.get(str);
            }
            return curr;


        }

        public List<String> topKByFrequency(String prefix, int k){
            List<String> result = new ArrayList<>();
            TrieNode prefixNode = findNode(prefix);


            List<Map.Entry<String, Integer>> matches = new ArrayList<>();
            dfs(prefixNode, prefix, matches);

            matches.sort((a, b) -> {
                if(!a.getValue().equals(b.getValue()))
                    return b.getValue();
                return a.getKey().compareTo(b.getKey());
            });
            int minSize = Math.min(k,matches.size());
            for(int i =0; i< minSize; i++){
                result.add(matches.get(i).getKey());
            }

            return result;

        }

        public void dfs(TrieNode node, String currPhrase, List<Map.Entry<String, Integer>> result){
            if(node.isEndOfPhrase){
                result.add(new AbstractMap.SimpleEntry<>(currPhrase, node.freq));

            }
            for(Map.Entry<String, TrieNode> child:node.children.entrySet()){

                dfs(child.getValue(), currPhrase +" "+ child.getKey(), result);

            }
        }


    }

    static void main(String[] args) {

    }
}
