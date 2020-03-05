package ru.itmo.mit.trie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializableTrie extends Trie {
    /**
     * Write trie state to output stream
     */
    void serialize(OutputStream out) throws IOException;

    /**
     * Replace current state with data from input stream
     */
    void deserialize(InputStream in) throws IOException;
}