package com.ayb.rpc.core.transport;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.extension.ExtensionLoader;
import com.ayb.rpc.core.refer.Referer;
import com.ayb.rpc.core.serialize.Serialization;

/**
 * Created by yunbinan on 16-11-2.
 */
public abstract class AbstractClient implements Client {
    protected Serialization serialization;
    protected DiscoverServiceConfig config;
    protected Referer referer;

    public AbstractClient(DiscoverServiceConfig config, Referer referer, ProtocolConfig protocolConfig) {
        serialization = ExtensionLoader.getExtensionLoader(Serialization.class).getExtension(protocolConfig.getSerialization());
        this.config = config;
        this.referer = referer;
    }

}
