package com.alincode.hammock.net;

import com.alincode.hammock.configuration.FileIterator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * This class retrieves a String to play back from the FileIterator it is registered with.
 */
public class HammockSocket extends Socket{
    private final String host;
    private final int port;

    private final FileIterator fileIterator;
    private InputStream stream = null;

    private boolean created = false;
    private boolean bound = false;
    private boolean connected = false;
    private boolean closed = false;

    public HammockSocket(String host, int port, FileIterator fileIterator) {
        this.host = host;
        this.port = port;
        this.fileIterator = fileIterator;
    }

    /**
     * Not supporting generic sockets that are bound later.
     */
    public void connect(SocketAddress endpoint) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supporting generic sockets that are bound later.
     */
    public void bind(SocketAddress bindpoint) {
        throw new UnsupportedOperationException();
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileIterator.getNext().getBytes());
    }
}
