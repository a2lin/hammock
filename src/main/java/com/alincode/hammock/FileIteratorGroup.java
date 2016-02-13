package com.alincode.hammock;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is a holding class for FileIterators. The same fileIterator is given to all classes that request a certain
 * resource. Once the resource is requested, if not enough bytes are read from it, we throw a 500 error.
 */
public class FileIteratorGroup {
    private static class FileIteratorHolder {
        private static final Map<URI, FileIterator> iteratorsByURI = new ConcurrentHashMap<>();
    }

    public void addFileIterator(URI uri, FileIterator iterator) {
        FileIteratorHolder.iteratorsByURI.put(uri, iterator);
    }

    public static FileIterator getFileIterator(URI uri) {
        return FileIteratorHolder.iteratorsByURI.get(uri);
    }
}
