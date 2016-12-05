package com.ayb.rpc.core.protocol;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.export.DefaultExporter;
import com.ayb.rpc.core.export.Exporter;
import com.ayb.rpc.core.provider.Provider;
import com.ayb.rpc.core.refer.DefaultReferer;
import com.ayb.rpc.core.refer.Referer;

/**
 * Created by yunbinan on 16-8-25.
 */
public class DefaultProtocol<T> implements Protocol<T> {

    @Override
    public Exporter export(Provider<T> provider, RegisterConfig config, ProtocolConfig protocolConfig) {
        Exporter exporter = new DefaultExporter<T>(provider, config, protocolConfig);
        return exporter;
    }

    @Override
    public Referer refer(Class clz, DiscoverServiceConfig config, ProtocolConfig protocolConfig) {
        Referer<T> referer = new DefaultReferer<T>(config, protocolConfig);  //TODO 默认
        return referer;
    }
}
