package com.grokingcodeinterview.pattern27Trie;

import java.util.HashMap;

public class WordTrieNode {
    HashMap<String, WordTrieNode> children = new HashMap<>();
    boolean isEndOfPhrase;

}
