package com.ayb.rpc.core.cluster;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.refer.Referer;

import java.util.List;

/**
 * Created by yunbinan on 16-8-31.
 */
public class DefaultCluster extends AbstractCluster {

    public DefaultCluster(Class interfaceClass, RegisterConfig registerConfig, ProtocolConfig protocolConfig) {
        super(interfaceClass, registerConfig, protocolConfig);
    }
}
