package com.ayb.rpc.core.export;

import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.extension.ExtensionLoader;
import com.ayb.rpc.core.provider.Provider;
import com.ayb.rpc.core.transport.EndpointFactory;
import com.ayb.rpc.core.transport.Server;
import com.ayb.rpc.transport.netty3.Netty3Server;

/**
 * Created by yunbinan on 16-8-25.
 */
public class DefaultExporter<T> extends AbstractExporter<T> {
    private EndpointFactory endpointFactory;
    private Server server;

    public DefaultExporter(Provider<T> provider, RegisterConfig config, ProtocolConfig protocolConfig) {
        super(provider);
        endpointFactory = ExtensionLoader.getExtensionLoader(EndpointFactory.class).getExtension(protocolConfig.getTransport());
        server = endpointFactory.getServer(this, config, protocolConfig);
    }

}
