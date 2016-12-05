package com.ayb.rpc.core.config;

/**
 * Created by yunbinan on 16-9-8.
 */
public class DiscoverServiceConfig {
    private String host;
    private int port;

    public DiscoverServiceConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public boolean equals(DiscoverServiceConfig o) {
        if (this.host.equals(o.getHost()) && this.port == o.getPort())
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "DiscoverServiceConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
