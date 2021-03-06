package ru.itmo.mit.util;

import org.jetbrains.annotations.NotNull;
import java.util.*;

public class DictionaryImpl<K, V> extends AbstractMap<K, V> implements Dictionary<K, V> {

    private int sz = 0;
    private int buckets = 2;
    private ArrayList<List<Entry<K, V>>> arr = new ArrayList<>(buckets);
    private final int RESIZE_FACTOR = 2;
    private double LOAD_FACTOR = 0.8;

    DictionaryImpl() {
        for (int i = 0; i <buckets; i++) {
            arr.add(new LinkedList<>());
        }
    }
    public void setLoadFactor(double newLoadFactor) {
        LOAD_FACTOR = newLoadFactor;
    }

    static class Pair<L, R> implements Entry<L, R> {
        private L k;
        private R v;

        Pair(L key, R value) {
            k = key;
            v = value;
        }

        @Override
        public L getKey() {
            return k;
        }

        @Override
        public R getValue() {
            return v;
        }

        @Override
        public R setValue(R r) {
            R prev = v;
            v = r;
            return prev;
        }
    }

    private int getBucket(Object key) {
        return key.hashCode() % buckets;
    }

    private void rehash() {
        ArrayList<List<Entry<K, V>>> tmpArr = new ArrayList<>(buckets);
        for (int i = 0; i < buckets; i++) {
            tmpArr.add(new LinkedList<>());
        }

        for (List<Entry<K, V>> lst : arr) {
            for (Entry<K,V> p : lst) {
                int h = getBucket(p.getKey());
                tmpArr.get(h).add(p);
            }
        }
        arr = tmpArr;
    }

    private boolean isFilledEnough() {
        return sz >= Math.round(buckets * LOAD_FACTOR);
    }

    private boolean isFreeEnough() {
        return sz <= Math.round(buckets * LOAD_FACTOR / RESIZE_FACTOR);
    }

    @Override
    public V put(K key, V value) {
        sz += 1;
        if (isFilledEnough()) {
            buckets *= RESIZE_FACTOR;
            rehash();
        }

        int i = getBucket(key);
        List<Entry<K, V>> lst = arr.get(i);
        for (Entry<K, V> el : lst) {
            if (el.getKey().equals(key)) {
                sz -= 1;
                return el.setValue(value);
            }
        }

        arr.get(i).add(new Pair<>(key, value));
        return null;
    }

    @Override
    public int size() {
        return sz;
    }

    @Override
    public V get(Object key) {
        int i = getBucket(key);
        List<Entry<K, V>> lst = arr.get(i);
        for (Entry<K, V> p : lst) {
            if (p.getKey().equals(key)) {
                return p.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public V remove(Object key) {
        int i = getBucket(key);
        List<Entry<K, V>> lst = arr.get(i);
        
        if (isFreeEnough()) {
            buckets = (int) Math.round((double) buckets / (double) RESIZE_FACTOR);
            rehash();
        }

        V val = get(key);
        lst.removeIf(el -> el.getKey().equals(key));
        if (val != null) {
            sz -= 1;
        }

        return val;
    }

    @Override
    public @NotNull Set<Entry<K, V>> entrySet() {
        return new AbstractSet<Entry<K, V>>() {
            @NotNull
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new DictionaryIterator();
            }

            @Override
            public int size() {
                return sz;
            }
        };
    }

    class DictionaryIterator implements Iterator<Entry<K, V>> {
        private final Iterator<List<Entry<K,V>>> it = arr.iterator();
        private Iterator<Entry<K, V>> subIt = it.next().iterator();
        private Entry<K, V> lstNxt;
        private boolean changingBucked = false;

        DictionaryIterator() {
            while (!subIt.hasNext() && it.hasNext()) {
                subIt = it.next().iterator();
            }
        }

        @Override
        public boolean hasNext() {
            return subIt.hasNext() || it.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            lstNxt = subIt.next();
            changingBucked = false;
            while (!subIt.hasNext() && it.hasNext()) {
                subIt = it.next().iterator();
                changingBucked = true;
            }
            return lstNxt;
        }

        @Override
        public void remove() {
            // todo: can't do rehash here because of UB in iterator
            if (changingBucked) {
                K key = lstNxt.getKey();
                arr.get(getBucket(key)).removeIf(e -> e.getKey().equals(key));
            } else {
                subIt.remove();
                K key = lstNxt.getKey();
                arr.get(getBucket(key)).removeIf(e -> e.getKey().equals(key));
            }
            sz -= 1;
        }
    };

}
