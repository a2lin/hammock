package com.alincode.hammock.net;

import com.alincode.hammock.configuration.Configuration;
import com.alincode.hammock.configuration.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

/**
 * Wrapping a SocketChannel so we can control what gets written to a socket. If a request is made to 'bind' to a socket
 * we know how to read (pre-set), we will intercept the request instead of passing-through.
 */
public class IntrospectedSocketChannel extends SocketChannel {
    private Logger LOGGER = LoggerFactory.getLogger(IntrospectedSocketChannel.class);
    private final HammockSocket socket = null;
    private SocketChannel socketChannel;
    private final Configuration cfg;
    private final Matcher matcher;

    public IntrospectedSocketChannel(SocketChannel socketChannel, Configuration cfg, Matcher matcher, SelectorProvider provider) {
        // The super implementation is to add a class variable, but since we are delegating everything
        // to the actual channel, we don't care about this.
        super(provider);
        this.cfg = cfg;
        this.matcher = matcher;
        LOGGER.info("Socket Channel Introspected");
    }

    @Override
    public SocketChannel bind(SocketAddress local) throws IOException {
        socketChannel = socketChannel.bind(local);
        return this;
    }

    @Override
    public <T> SocketChannel setOption(SocketOption<T> name, T value) throws IOException {
        socketChannel = socketChannel.setOption(name, value);
        return this;
    }

    @Override
    public <T> T getOption(SocketOption<T> name) throws IOException {
        return socketChannel.getOption(name);
    }

    @Override
    public Set<SocketOption<?>> supportedOptions() {
        return socketChannel.supportedOptions();
    }

    @Override
    public SocketChannel shutdownInput() throws IOException {
        socketChannel = socketChannel.shutdownInput();
        return this;
    }

    @Override
    public SocketChannel shutdownOutput() throws IOException {
        socketChannel = socketChannel.shutdownOutput();
        return this;
    }

    @Override
    public Socket socket() {
        return socketChannel.socket();
    }

    @Override
    public boolean isConnected() {
        return socketChannel.isConnected();
    }

    @Override
    public boolean isConnectionPending() {
        return socketChannel.isConnectionPending();
    }

    @Override
    public boolean connect(SocketAddress remote) throws IOException {
        if(remote instanceof InetSocketAddress) {
            String hostName = ((InetSocketAddress) remote).getHostName();
            int port = ((InetSocketAddress) remote).getPort();
            String concat = String.join(":", hostName, String.valueOf(port);
            if(matcher.match(concat)) {
            }
        }
        return socketChannel.connect(remote);

    }

    @Override
    public boolean finishConnect() throws IOException {
        return socketChannel.finishConnect();
    }

    @Override
    public SocketAddress getRemoteAddress() throws IOException {
        return socketChannel.getRemoteAddress();
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        return socketChannel.read(dst);
    }

    @Override
    public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
        return socketChannel.read(dsts, offset, length);
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        return socketChannel.write(src);
    }

    @Override
    public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
        return socketChannel.write(srcs, offset, length);
    }

    @Override
    public SocketAddress getLocalAddress() throws IOException {
        return socketChannel.getLocalAddress();
    }

    @Override
    protected void implCloseSelectableChannel() throws IOException {
        socketChannel.close();
    }

    @Override
    protected void implConfigureBlocking(boolean block) throws IOException {
        socketChannel.configureBlocking(block);
    }
}
