package com.ayb.rpc.transport.netty3;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.export.Exporter;
import com.ayb.rpc.core.refer.Referer;
import com.ayb.rpc.core.transport.AbstractEndpointFactory;
import com.ayb.rpc.core.transport.Client;
import com.ayb.rpc.core.transport.Server;

/**
 * Created by yunbinan on 16-10-31.
 */
public class Netty3EndpointFactory extends AbstractEndpointFactory {
    @Override
    public Server createServer(Exporter exporter, RegisterConfig config, ProtocolConfig protocolConfig) {
        return new Netty3Server(exporter, config, protocolConfig);
    }


    @Override
    public Client createClient(DiscoverServiceConfig config, Referer referer, ProtocolConfig protocolConfig) {
        return new Netty3Client(config, referer, protocolConfig);
    }
}
