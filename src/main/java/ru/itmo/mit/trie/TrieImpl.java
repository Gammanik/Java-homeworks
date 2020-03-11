package ru.itmo.mit.trie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class TrieImpl implements Trie, SerializableTrie {

    @Override
    public void serialize(OutputStream out) throws IOException {

    }

    @Override
    public void deserialize(InputStream in) throws IOException {

    }

    class Vertex {
        boolean isTerminal = false;
        int containsWords = 0;
        HashMap<Character, Vertex> mp = new HashMap<>();

        Vertex addOrPass(Character ch) {
            containsWords++;

            if (this.hasChar(ch)) {
                return this.getNext(ch);
            } else {
                Vertex v = new Vertex();
                mp.put(ch, v);
                return v;
            }
        }

        void deleteChain(Character ch) { mp.remove(ch); }
        Vertex getNext(Character ch) { return mp.get(ch); }
        boolean hasChar(Character ch) { return mp.containsKey(ch); }
    }

    private final Vertex root = new Vertex();

    @Override
    public boolean add(String el) {
        if (contains(el)) return false;

        Vertex curr = this.root;
        for (int i = 0; i < el.length(); i++) {
            Character ch = el.charAt(i);
            curr = curr.addOrPass(ch);
        }

        curr.containsWords++;
        curr.isTerminal = true;
        return true;
    }

    @Override
    public boolean contains(String el) {
        Vertex curr = this.root;
        for (int i = 0; i < el.length(); i++) {
            Character ch = el.charAt(i);
            if (curr.hasChar(ch)) {
                curr = curr.getNext(ch);
            } else {
                return false;
            }
        }

        return curr.isTerminal;
    }

    @Override
    public boolean remove(String el) {
        if (!contains(el)) return false;

        Vertex prev = this.root;
        Vertex curr = this.root;
        curr.containsWords--;

        Character ch;
        Character ch_prev = '\0';
        for (int i = 0; i < el.length(); i++) {
            ch = el.charAt(i);
            if (curr.containsWords == 0) {
                prev.deleteChain(ch_prev);
                return true;
            }

            prev = curr;
            ch_prev = ch;
            curr = curr.getNext(ch);
            curr.containsWords--;
        }

        curr.isTerminal = false;
        return true;
    }

    @Override
    public int size() {
        return this.root.containsWords;
    }

    @Override
    public int howManyStartsWithPrefix(String prefix) {
        Vertex curr = this.root;
        Character ch;
        for (int i = 0; i < prefix.length(); i++) {
            ch = prefix.charAt(i);
            if (curr.hasChar(ch)) {
                curr = curr.getNext(ch);
            } else {
                return 0;
            }
        }

        return curr.containsWords;
    }

    @Override
    public String nextString(String element, int k) {
        return null;
    }
}
