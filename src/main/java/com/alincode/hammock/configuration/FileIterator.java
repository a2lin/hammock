package com.alincode.hammock.configuration;

import java.util.Iterator;
import java.util.stream.Stream;

public class FileIterator {
    private final Iterator<String> fileStreamIterator;

    /**
     * A FileIterator is responsible for providing Lines of input from a file that is the source of the mocked-out
     * socket.
     */
    public FileIterator(Stream<String> fileStream) {
        this.fileStreamIterator = fileStream.iterator();
    }

    public String getNext() {
        return fileStreamIterator.hasNext() ? fileStreamIterator.next() : null;
    }
}
