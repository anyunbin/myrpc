package com.ayb.rpc.core.refer;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.extension.ExtensionLoader;
import com.ayb.rpc.core.transport.*;
import com.ayb.rpc.transport.netty3.Netty3Client;

/**
 * Created by yunbinan on 16-8-31.
 */
public class DefaultReferer<T> extends AbstractReferer {
    private EndpointFactory endpointFactory;
    private Client client;

    @Override
    protected Response doCall(Request request) {
        ResponseFuture responseFuture = new ResponseFuture(request, timeout);
        this.putResponseFuture(request, responseFuture);  //请求前就缓存
        client.request(request);
        return responseFuture;
    }

    public DefaultReferer(DiscoverServiceConfig config, ProtocolConfig protocolConfig) {
        super(config);
        endpointFactory = ExtensionLoader.getExtensionLoader(EndpointFactory.class).getExtension(protocolConfig.getTransport());
        client = endpointFactory.getClient(config, this, protocolConfig);
    }
}
