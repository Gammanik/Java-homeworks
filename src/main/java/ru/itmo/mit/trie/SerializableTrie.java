package ru.itmo.mit.trie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public interface SerializableTrie extends Trie, Serializable {
    /**
     * Write trie state to output stream
     */
    void serialize(OutputStream out) throws IOException;

    /**
     * Replace current state with data from input stream
     */
    void deserialize(InputStream in) throws IOException;
}
