package com.alincode.hammock.net;

import com.alincode.hammock.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ProtocolFamily;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;

public class WrappedProvider extends SelectorProvider{
    private static final Logger LOGGER = LoggerFactory.getLogger(WrappedProvider.class);
    private final SelectorProvider sp;
    private final Configuration cfg;

    public WrappedProvider(SelectorProvider sp, Configuration cfg) {
        this.sp = sp;
        this.cfg = cfg;
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
        LOGGER.info("SocketChannel opened");
        return new IntrospectedSocketChannel(sp.openSocketChannel(), cfg, this);
    }
}
