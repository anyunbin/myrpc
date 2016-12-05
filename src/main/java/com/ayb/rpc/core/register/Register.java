package com.ayb.rpc.core.register;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.module.URL;

import java.util.List;

/**
 * Created by yunbinan on 16-9-7.
 */
public interface Register {
    void register();

    void subscribe(final RegisterConfig registerConfig, NotifyListener listener);

    List<DiscoverServiceConfig> discover();
}
