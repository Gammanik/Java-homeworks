package ru.itmo.mit.util;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DictionaryImplTest {

    private DictionaryImpl<String, Integer> dc;

    @Before
    public void setUp() {
        dc = new DictionaryImpl<>();
    }

    @Test
    public void addBasicTest() {
        assertEquals(dc.size(), 0);

        assertNull(dc.put("1", 1));
        assertEquals(dc.size(), 1);
        assertEquals(dc.get("1"), (Integer) 1);
        assertTrue(dc.containsKey("1"));

        assertNull(dc.put("2", 2));
        assertEquals(dc.size(), 2);
        assertEquals(dc.get("2"), (Integer) 2);
        assertTrue(dc.containsKey("2"));

        assertNull(dc.put(Integer.toString(3), 3));
        assertEquals(dc.size(), 3);
        assertEquals(dc.get(Integer.toString(3)), (Integer) 3);
        assertTrue(dc.containsKey("3"));

        assertNull(dc.put(Integer.toString(0), 0));
        assertEquals(dc.size(), 4);
        assertEquals(dc.get("0"), (Integer) 0);
        assertTrue(dc.containsKey("0"));

        assertEquals(dc.put(Integer.toString(0), 42), (Integer) 0);
        assertEquals(dc.size(), 4);
        assertEquals(dc.get("0"), (Integer) 42);
        assertTrue(dc.containsKey("0"));
    }

    @Test
    public void addStressTest() {
        LinkedHashMap<String, Integer> mp = new LinkedHashMap<>();
        int repetitions = 206; // 206

        for (int i = 0; i < repetitions; i++) {
            mp.put(Integer.toString(i), i);
            assertNull(dc.put(Integer.toString(i), i));
        }

        for (Map.Entry<String, Integer> el : mp.entrySet()) {
            assertEquals(dc.get(el.getKey()), el.getValue());
            assertTrue(dc.containsKey(el.getKey()));
        }

        int i = 0;
        for (Map.Entry<String, Integer> el : dc.entrySet()) {
            i += 1;
            System.out.println(el.getKey() + ": " + el.getValue() + " - " + i);
            assertEquals(el.getValue(), mp.get(el.getKey()));
        }
    }

    @Test
    public void addSameStressTest() {
        LinkedHashMap<String, Integer> mp = new LinkedHashMap<>();
        int repetitions = 99;

        for (int i = 0; i < repetitions; i++) {
            mp.put(Integer.toString(i % 20), i);
            dc.put(Integer.toString(i % 20), i);
        }

        for (Map.Entry<String, Integer> el : mp.entrySet()) {
            System.out.println(el.getKey() + ": " + el.getValue());
            assertEquals(dc.get(el.getKey()), el.getValue());
            assertTrue(dc.containsKey(el.getKey()));
        }

        int i = 0;
        for (Map.Entry<String, Integer> el : dc.entrySet()) {
            i += 1;
            System.out.println(el.getKey() + ": " + el.getValue() + " - " + i);
            assertEquals(el.getValue(), mp.get(el.getKey()));
        }

    }

    @Test
    public void iterTest() {
        assertEquals(dc.size(), 0);

        assertNull(dc.put("1", 1));
        Iterator<Map.Entry<String, Integer>> it = dc.entrySet().iterator();
        assertTrue(it.hasNext());
        Map.Entry<String, Integer> p = it.next();
        assertEquals(p.getKey(), "1");
        assertEquals(p.getValue(), (Integer) 1);
        assertFalse(it.hasNext());

        dc.put("2", 2);
        it = dc.entrySet().iterator();
        it.next();
        Map.Entry<String, Integer> p2 = it.next();
        assertEquals(p2.getKey(), "2");
        assertEquals(p2.getValue(), (Integer) 2);
        assertFalse(it.hasNext());

        dc.put("3", 3);
        it = dc.entrySet().iterator();
        it.next();
        it.next();
        Map.Entry<String, Integer> p3 = it.next();
        assertEquals(p3.getKey(), "3");
        assertEquals(p3.getValue(), (Integer) 3);
        assertFalse(it.hasNext());
    }


    @Test
    public void iterRemoveTest() {
        assertEquals(dc.size(), 0);

        assertNull(dc.put("1", 1));
        assertNull(dc.put("2", 2));
        assertNull(dc.put("3", 3));
        Iterator<Map.Entry<String, Integer>> it = dc.entrySet().iterator();
        it.next();
        it.next();
//        it.remove(); todo:

        Map.Entry<String, Integer> p3 = it.next();
        assertEquals(p3.getKey(), "3");
        assertEquals(p3.getValue(), (Integer) 3);
        assertFalse(it.hasNext());
    }

    @Test
    public void removeTest() {
        assertNull(dc.put("1", 1));
        assertNull(dc.put("2", 2));
        assertNull(dc.put("3", 3));

        assertEquals(dc.remove("2"), (Integer) 2);
        assertEquals(dc.size(), 2);

        assertNull(dc.remove("4"));
        assertEquals(dc.size(), 2);

        assertNull(dc.put("4", 4));
        assertEquals(dc.size(), 3);
        assertEquals(dc.remove("4"), (Integer) 4);
    }

    @Test
    public void clearTest() {

    }


}
