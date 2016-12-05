package com.ayb.rpc.core.protocol;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.export.Exporter;
import com.ayb.rpc.core.module.URL;
import com.ayb.rpc.core.provider.AbstractProvider;
import com.ayb.rpc.core.provider.Provider;
import com.ayb.rpc.core.refer.Referer;

/**
 * Created by yunbinan on 16-8-25.
 */
public interface Protocol<T> {
    //暴露
    Exporter<T> export(Provider<T> provider,RegisterConfig config,ProtocolConfig protocolConfig);

    //调用
    Referer<T> refer(Class<T> clz, DiscoverServiceConfig config,ProtocolConfig protocolConfig);
}
