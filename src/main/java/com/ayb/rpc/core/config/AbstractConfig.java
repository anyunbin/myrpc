package com.ayb.rpc.core.config;

import com.ayb.rpc.core.module.URL;
import com.ayb.rpc.util.NetWorkUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by yunbinan on 16-8-30.
 */
public abstract class AbstractConfig {
    protected RegisterConfig loadRegistryUrl() {
        String serverHost = null;
        serverHost = NetWorkUtil.getHostAddress();
        return new RegisterConfig("192.168.13.137", 2181, serverHost);
    }
}
