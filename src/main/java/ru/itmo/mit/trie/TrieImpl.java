package ru.itmo.mit.trie;

import java.io.*;
import java.util.*;


public class TrieImpl implements SerializableTrie {

    @Override
    public void serialize(OutputStream out) throws IOException {
        DataOutputStream stream = new DataOutputStream(out);
        Stack<Vertex> st = new Stack<>();
        Vertex tmp = this.root;

        tmp.serialize(stream, st);
        while (!st.empty()) {
            tmp = st.pop();
            tmp.serialize(stream, st);
        }
        tmp.serialize(stream, st);
    }

    @Override
    public void deserialize(InputStream in) throws IOException {
        DataInputStream stream = new DataInputStream(in);
        Stack<Vertex> st = new Stack<>();
        Vertex rt = new Vertex();
        Vertex tmp = rt;

        tmp.deserialize(stream, st);
        while (!st.empty()) {
            tmp = st.pop();
            tmp.deserialize(stream, st);
        }
        tmp.deserialize(stream, st);
        this.root = rt;
    }


class Vertex implements Serializable {

        void serialize(DataOutputStream stream, Stack<Vertex> st) throws IOException {
            stream.writeBoolean(this.isTerminal);
            stream.writeInt(this.mp.size());
            stream.writeInt(this.containsWords);
            for (Map.Entry<Character, Vertex> entry : this.mp.entrySet()) {
                stream.writeChar(entry.getKey());
                st.push(entry.getValue());
            }
        }

        void deserialize(DataInputStream stream, Stack<Vertex> st) throws IOException {
            this.isTerminal = stream.readBoolean();
            int mapSize = stream.readInt();
            this.containsWords = stream.readInt();

            for (int i = 0; i < mapSize; i++) {
                char ch = stream.readChar();
                Vertex next = new Vertex();
                this.mp.put(ch, next);
                st.push(next);
            }
        }


        boolean isTerminal = false;
        int containsWords = 0;
        final HashMap<Character, Vertex> mp = new HashMap<>();

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

    private Vertex root = new Vertex();

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
