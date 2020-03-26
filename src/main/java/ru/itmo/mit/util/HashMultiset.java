package ru.itmo.mit.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HashMultiset<E> extends AbstractCollection<E> implements Multiset<E> {
    private LinkedHashMap<E, Integer> table = new LinkedHashMap<>();
    private int currentSize = 0;

    @Override
    public int count(Object element) throws ClassCastException {
        return table.getOrDefault((E) element, 0);
    }

    @Override
    public Set<E> elementSet() {
        return table.keySet();
    }

    @Override
    public Set<Entry<E>> entrySet() {
        return new AbstractSet<Entry<E>>() {
            @NotNull
            @Override
            public Iterator<Entry<E>> iterator() {
                return new Iterator<Entry<E>>() {
                    int currBucketSz = 0;
                    final Iterator<Map.Entry<E, Integer>> it = table.entrySet().iterator();

                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override
                    public Entry<E> next() {
                        Map.Entry<E, Integer> bucket = it.next();
                        currBucketSz = bucket.getValue();
                        return new Entry<E>() {
                            @Override
                            public E getElement() {
                                return bucket.getKey();
                            }

                            @Override
                            public int getCount() {
                                return bucket.getValue();
                            }
                        };
                    }

                    @Override
                    public void remove() {
                        it.remove();
                        currentSize -= currBucketSz;
                    }
                };
            }

            @Override
            public int size() {
                return table.size();
            }
        };
    }

    @NotNull
    @Override
    public Iterator<E> iterator() throws IllegalStateException {
        return new Iterator<E>() {
            private final Iterator<Map.Entry<E, Integer>> it = table.entrySet().iterator();
            private Map.Entry<E, Integer> currBucket;
            private int currBucketSz = 0;
            private boolean nextCalled = false;

            @Override
            public boolean hasNext() {
                return it.hasNext() || currBucketSz != 0;
            }

            @Override
            public E next() {
                // todo: check if next was called?

                if (currBucketSz == 0) {
                    currBucket = it.next();
                    currBucketSz = currBucket.getValue();
                }

                currBucketSz -= 1;
                return currBucket.getKey();
            }

            @Override
            public void remove() {
                if (nextCalled) {
                    throw new IllegalStateException("Can't remove if next was called");
                }

                int count = currBucket.getValue();
                if (count > 1) {
                    currBucket.setValue(count - 1);
                } else {
                    it.remove();
                }

                currentSize -= 1;
            }
        };
    }

    @Override
    public boolean contains(Object o) {
        return table.containsKey(o);
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean remove(Object o) {
        int cnt = count(o);

        if (cnt == 0) {
            return false;
        } else if (cnt == 1) {
            table.remove(o);
        } else {
            // decrease occurences
            table.replace((E) o, cnt - 1);
        }
        currentSize -= 1;
        return true;
    }

    @Override
    public boolean add(E e) {
        currentSize += 1;
        table.put(e, count(e) + 1);
        return true;
    }



}