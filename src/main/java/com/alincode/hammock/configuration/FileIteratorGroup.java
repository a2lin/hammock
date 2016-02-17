package com.alincode.hammock.configuration;

import com.alincode.hammock.configuration.FileIterator;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is a holding class for FileIterators. The same fileIterator is given to all classes that request a certain
 * resource. Once the resource is requested, if not enough bytes are read from it, we throw a 500 error.
 */
public class FileIteratorGroup {
    private static class FileIteratorHolder {
        private static final Map<String, FileIterator> iteratorsByURI = new ConcurrentHashMap<>();
    }

    public synchronized static void addFileIterator(String uri, FileIterator iterator) {
        if(!FileIteratorHolder.iteratorsByURI.containsKey(uri)) {
            FileIteratorHolder.iteratorsByURI.put(uri, iterator);
        }
    }

    public synchronized static FileIterator getFileIterator(String uri) {
        return FileIteratorHolder.iteratorsByURI.get(uri);
    }
}
