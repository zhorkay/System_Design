package io.myzoe.system_design;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trie {
    private final int R;
    private TrieNode root;

    public Trie(int r) {
        R = r;
        this.root = new TrieNode();
    }

    public void put(String s) {
        TrieNode x = root;
        for (char c : s.toCharArray()) {
            if (x.next[c] == null) {
                x.next[c] = new TrieNode();
            }
            x = x.next[c];
        }
        x.word = s;
    }

    public void put(String s, int rank) {
        root.put(s, 0, rank);
    }

    public boolean contains(String s) {
        TrieNode x = root;
        for (char c : s.toCharArray()) {
            if (x.next[c] == null) {
                return false;
            }
            x = x.next[c];
        }
        return x.word != null;
    }

    public TrieNode get(TrieNode x, int c) {
        if (x == null) {
            return root.next[c];
        }
        return x.next[c];
    }

    public TrieNode root() {
        return root;
    }


    protected class TrieNode implements Comparable<TrieNode> {

        private String word;
        private int rank;
        private TrieNode[] next;
        private List<TrieNode> topten;

        public TrieNode() {
            next = new TrieNode[R];
            topten = new ArrayList<>();
        }

        public List<String> getTopTen() {
            List<String> res = new ArrayList<>();
            for (TrieNode x : topten) {
                res.add(x.word);
            }
            return res;
        }

        private TrieNode put(String s, int i, int rank) {
            if (i == s.length()) {
                this.word = s;
                this.rank = rank;
                return this;
            }
            int c = s.charAt(i);

            if (next[c] == null) {
                next[c] = new TrieNode();
            }

            TrieNode leaf = next[c].put(s, i+1, rank);
            topten.add(leaf);
            Collections.sort(topten);
            if (topten.size() > 10) {
                topten.remove(10);
            }

            return leaf;
        }

        @Override
        public int compareTo(TrieNode o) {
            return o.rank - this.rank;
        }
    }


}
