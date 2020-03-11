package ru.itmo.mit.trie;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TrieSerializableTest {
    private TrieImpl tr;

    @Before
    public void setup() {
        this.tr = new TrieImpl();
    }

    private TrieImpl getDeserialized(TrieImpl t) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        t.serialize(out);

        TrieImpl res = new TrieImpl();
        res.deserialize(new ByteArrayInputStream(out.toByteArray()));
        return res;
    }

    @Test
    public void serializeTest() throws IOException {
        assertTrue(this.tr.add("0"));
        assertTrue(this.tr.add("1"));
        assertTrue(this.tr.add("2"));
        assertTrue(this.tr.add("3"));

        TrieImpl _tr;
        _tr = getDeserialized(this.tr);
        assertEquals(_tr.size(), this.tr.size());
        assertEquals(_tr.size(), 4);
        assertTrue(_tr.contains("0"));
        assertTrue(_tr.contains("1"));
        assertTrue(_tr.contains("2"));
        assertTrue(_tr.contains("3"));
    }

    @Test
    public void serializeEmptyTest() throws IOException {
        TrieImpl _tr;
        _tr = getDeserialized(this.tr);
        assertEquals(_tr.size(), this.tr.size());
        assertEquals(_tr.size(), 0);
    }

    @Test
    public void serializeSamePrefixes() throws IOException {
        TrieImpl _tr;
        assertTrue(this.tr.add("he"));
        assertTrue(this.tr.add("heh"));
        assertTrue(this.tr.add("hehe"));
        assertTrue(this.tr.add("heheh"));

        _tr = getDeserialized(this.tr);
        assertEquals(_tr.size(), 4);
        assertEquals(_tr.size(), this.tr.size());
        assertTrue(_tr.contains("he"));
        assertTrue(_tr.contains("heh"));
        assertTrue(_tr.contains("hehe"));
        assertTrue(_tr.contains("heheh"));
    }

    @Test
    public void serializeComplex() throws IOException {
        TrieImpl _tr;
        assertTrue(this.tr.add("he"));
        assertTrue(this.tr.add("she"));
        assertTrue(this.tr.add("his"));
        assertTrue(this.tr.add("hers"));

        _tr = getDeserialized(this.tr);
        assertEquals(_tr.size(), 4);
        assertEquals(_tr.size(), this.tr.size());
        assertTrue(_tr.contains("he"));
        assertTrue(_tr.contains("she"));
        assertTrue(_tr.contains("his"));
        assertTrue(_tr.contains("hers"));
    }


    private String[] genStrings(int size, int range){
        String[] arr= new String[size];
        for (int i=0; i<arr.length; i++) {
            arr[i] = String.valueOf((int)(range * Math.random()));
        }

        return arr;
    }

    @Test
    public void stressSerialize() throws IOException {
        TrieImpl _tr;

        String[] arr = genStrings(1000, 1000);
        HashSet<String> mp = new HashSet<>();
        Collections.addAll(mp, arr);

        for (String st : mp) {
            assertTrue(this.tr.add(st));
        }

        _tr = getDeserialized(this.tr);
        for (String st : mp) {
            assertTrue(_tr.contains(st));
        }

    }
}
