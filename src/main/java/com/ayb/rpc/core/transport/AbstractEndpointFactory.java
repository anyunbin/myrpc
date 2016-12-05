package com.ayb.rpc.core.transport;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.export.Exporter;
import com.ayb.rpc.core.refer.Referer;

/**
 * Created by yunbinan on 16-10-31.
 */
public abstract class AbstractEndpointFactory implements EndpointFactory {
    public abstract Server createServer(Exporter exporter, RegisterConfig config, ProtocolConfig protocolConfig);

    public abstract Client createClient(DiscoverServiceConfig config, Referer refere, ProtocolConfig protocolConfig);

    @Override
    public Server getServer(Exporter exporter, RegisterConfig config, ProtocolConfig protocolConfig) {
        return createServer(exporter, config, protocolConfig);
    }

    @Override
    public Client getClient(DiscoverServiceConfig config, Referer referer, ProtocolConfig protocolConfig) {
        return createClient(config, referer, protocolConfig);
    }
}
