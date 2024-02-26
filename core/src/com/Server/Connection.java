package com.Server;

import java.net.InetAddress;

public class Connection {
    private InetAddress ip;
    private int puerto;

    public Connection(InetAddress ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }

    public InetAddress getIp() {
        return this.ip;
    }

    public int getPuerto() {
        return this.puerto;
    }

}

