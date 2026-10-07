package com.grokingcodeinterview.pattern27Trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordTrie {

    WordTrieNode root = new WordTrieNode();

    public void insert(String phrase){
        WordTrieNode curr = root;
        for(String word: phrase.split(" ")){
            curr.children.putIfAbsent(word, new WordTrieNode());
            curr = curr.children.get(word);
        }
        curr.isEndOfPhrase = true;

    }

    public WordTrieNode findNode(String phrase){
        WordTrieNode curr = root;

        for(String str: phrase.split(" ")){
            if(!curr.children.containsKey(str)) return null;
            curr = curr.children.get(str);
        }
        return curr;

    }

    public boolean search(String phrase){
        WordTrieNode node = findNode(phrase);
        return node != null && node.isEndOfPhrase;
    }

    public boolean startWith(String phrase){
        return findNode(phrase)!= null;
    }

    public List<String> autoComplete(String phrase){
        List<String> result = new ArrayList<>();
        WordTrieNode prefixWordTrieNode = findNode(phrase);
        if(prefixWordTrieNode == null) return result;

        dfs(prefixWordTrieNode, phrase, result);
        return result;
    }

    private  void dfs(WordTrieNode  node, String phrase, List<String> result){
        if(node.isEndOfPhrase){
            result.add(phrase);
        }

        for(Map.Entry<String, WordTrieNode> entry: node.children.entrySet()){
            dfs(entry.getValue(), phrase +" " + entry.getKey(), result);
        }

    }


    //Give  me some example and print the result of autoComplete
     static void main(String[] args) {
        WordTrie wordTrie = new WordTrie();
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

        wordTrie = new WordTrie();
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
