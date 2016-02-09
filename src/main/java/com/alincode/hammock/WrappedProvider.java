package com.alincode.hammock;

import java.io.IOException;
import java.net.ProtocolFamily;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;

public class WrappedProvider extends SelectorProvider{
    private final SelectorProvider sp;
    public WrappedProvider(SelectorProvider sp) {
        System.out.println("Wrapped the provider!");
        this.sp = sp;
    }

    @Override
    public DatagramChannel openDatagramChannel() throws IOException {
        return sp.openDatagramChannel();
    }

    @Override
    public DatagramChannel openDatagramChannel(ProtocolFamily family) throws IOException {
        return sp.openDatagramChannel();
    }

    @Override
    public Pipe openPipe() throws IOException {
        return sp.openPipe();
    }

    @Override
    public AbstractSelector openSelector() throws IOException {
        return sp.openSelector();
    }

    @Override
    public ServerSocketChannel openServerSocketChannel() throws IOException {
        return sp.openServerSocketChannel();
    }

    @Override
    public SocketChannel openSocketChannel() throws IOException {
        return sp.openSocketChannel();
    }
}
