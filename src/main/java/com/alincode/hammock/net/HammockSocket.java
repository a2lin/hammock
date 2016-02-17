package com.alincode.hammock.net;

import com.alincode.hammock.configuration.FileIterator;
import org.apache.commons.io.output.NullOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * This class retrieves a String to play back from the FileIterator it is registered with.
 */
public class HammockSocket extends Socket{
    private final String host;
    private final int port;

    private final FileIterator fileIterator;
    private InputStream iStream = null;
    private OutputStream oStream = new NullOutputStream();


    public HammockSocket(String host, int port, FileIterator fileIterator) {
        this.host = host;
        this.port = port;
        this.fileIterator = fileIterator;
    }

    /**
     * Not supporting generic sockets that are bound later.
     */
    @Override
    public void connect(SocketAddress endpoint) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supporting generic sockets that are bound later.
     */
    @Override
    public void bind(SocketAddress bindpoint) {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileIterator.getNext().getBytes());
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return oStream;
    }

    public InetAddress getInetAddress() {
        throw new UnsupportedOperationException();
    }
}
