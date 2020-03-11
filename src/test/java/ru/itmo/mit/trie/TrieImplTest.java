package ru.itmo.mit.trie;


import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;



public class TrieImplTest {
    private TrieImpl tr;

    @Before
    public void setup() {
        this.tr = new TrieImpl();
    }

    private String[] genStrings(int size, int range){
        String[] arr= new String[size];
        for (int i=0; i<arr.length; i++) {
            arr[i] = String.valueOf((int)(range * Math.random()));
        }

        return arr;
    }


    @Test
    public void testAdd() {
        assertEquals(tr.size(), 0);
        assertFalse(tr.contains(""));

        String s1 = "trie";
        assertTrue(tr.add(s1));
        assertEquals(tr.size(), 1);
        assertFalse(tr.add(s1));
        assertEquals(tr.size(), 1);

        String s2 = "triee";
        assertTrue(tr.add(s2));
        assertEquals(tr.size(), 2);
    }

    @Test
    public void testContains() {
        String s = "trie";
        assertFalse(tr.contains(s));
        tr.add(s);
        assertTrue(tr.contains(s));
        assertFalse(tr.contains(s + "e"));
        assertFalse(tr.contains(s.substring(0, 2)));
    }

    @Test
    public void testRemove() {
        tr.add("he");
        tr.add("she");
        tr.add("his");
        tr.add("hers");

        assertEquals(tr.size(), 4);
        assertFalse(tr.remove(""));
        assertEquals(tr.size(), 4);
        assertFalse(tr.remove("not exists"));
        assertEquals(tr.size(), 4);

        assertTrue(tr.remove("he"));
        assertEquals(tr.size(), 3);
        assertFalse(tr.contains("he"));
        assertTrue(tr.contains("hers"));
        assertFalse(tr.remove("he"));

        assertTrue(tr.remove("she"));
        assertEquals(tr.size(), 2);
        assertFalse(tr.contains("she"));
    }

    @Test
    public void testHowManyStartsWithPrefix() {
        tr.add("he");
        tr.add("she");
        tr.add("his");
        tr.add("hers");

        assertEquals(tr.howManyStartsWithPrefix(""), 4);
        assertEquals(tr.howManyStartsWithPrefix("h"), 3);
        assertEquals(tr.howManyStartsWithPrefix("he"), 2);
        assertEquals(tr.howManyStartsWithPrefix("his"), 1);
        assertEquals(tr.howManyStartsWithPrefix("hers"), 1);
        assertEquals(tr.howManyStartsWithPrefix("hiss"), 0);

        assertEquals(tr.howManyStartsWithPrefix("s"), 1);
        assertEquals(tr.howManyStartsWithPrefix("sh"), 1);
        assertEquals(tr.howManyStartsWithPrefix("she"), 1);


        tr.remove("his");
        assertEquals(tr.howManyStartsWithPrefix(""), 3);
        assertEquals(tr.howManyStartsWithPrefix("h"), 2);
        assertEquals(tr.howManyStartsWithPrefix("he"), 2);
        assertEquals(tr.howManyStartsWithPrefix("his"), 0);
        assertEquals(tr.howManyStartsWithPrefix("hers"), 1);
        assertEquals(tr.howManyStartsWithPrefix("hiss"), 0);


        // remove a word that is a prefix of other word
        tr.add("his");
        tr.remove("he");
        assertEquals(tr.howManyStartsWithPrefix(""), 3);
        assertEquals(tr.howManyStartsWithPrefix("h"), 2);
        assertEquals(tr.howManyStartsWithPrefix("he"), 1);
        assertEquals(tr.howManyStartsWithPrefix("his"), 1);
        assertEquals(tr.howManyStartsWithPrefix("hers"), 1);
        assertEquals(tr.howManyStartsWithPrefix("hiss"), 0);
        assertEquals(tr.howManyStartsWithPrefix("she"), 1);


        // remove a word that is a prefix of other word
        tr.add("he");
        tr.add("hehe");
        tr.remove("he");
        assertEquals(tr.howManyStartsWithPrefix(""), 4);
        assertEquals(tr.howManyStartsWithPrefix("h"), 3);
        assertEquals(tr.howManyStartsWithPrefix("he"), 2);
        assertEquals(tr.howManyStartsWithPrefix("his"), 1);
        assertEquals(tr.howManyStartsWithPrefix("hers"), 1);
        assertEquals(tr.howManyStartsWithPrefix("hiss"), 0);
        assertEquals(tr.howManyStartsWithPrefix("she"), 1);
        tr.remove("hehe");

        // remove word in a separate branch
        tr.remove("she");
        assertEquals(tr.howManyStartsWithPrefix(""), 2);
        assertEquals(tr.howManyStartsWithPrefix("h"), 2);
        assertEquals(tr.howManyStartsWithPrefix("he"), 1);
        assertEquals(tr.howManyStartsWithPrefix("his"), 1);
        assertEquals(tr.howManyStartsWithPrefix("hers"), 1);
        assertEquals(tr.howManyStartsWithPrefix("hiss"), 0);
        assertEquals(tr.howManyStartsWithPrefix("she"), 0);

    }


    @Test
    public void shouldDeleteAlreadyCreated() {
        String[] arr = genStrings(1000, 50);
        HashMap<String, String> mp = new HashMap<>();

        // should successfully delete already created
        for (String st : arr) {
            if (mp.containsValue(st)) {
                assertTrue(this.tr.remove(st));
            }

            assertTrue(this.tr.add(st));
            mp.put(st, st);
        }

        // should contains all added strings
        for (String st : mp.keySet()) {
            assertTrue(this.tr.contains(st));
        }


        // stress removing
        Random rand = new Random();
        for(Iterator<Map.Entry<String, String>> it = mp.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> entry = it.next();
            if(rand.nextInt(100) < 30) {
                String st = entry.getKey();
                if (mp.containsValue(st)) {
                    assertTrue(this.tr.remove(st));
                    it.remove();
                } else {
                    assertFalse(this.tr.remove(st));
                }

            }
        }

    }

}
