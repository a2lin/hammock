package com.alincode.hammock.net;

import java.net.Socket;

public class IntrospectedSocket extends Socket{
    private final String host;
    private final int port;

    public IntrospectedSocket(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
