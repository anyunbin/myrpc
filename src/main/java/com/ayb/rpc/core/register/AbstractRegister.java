package com.ayb.rpc.core.register;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ReferConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.module.URL;

import java.util.List;

/**
 * Created by yunbinan on 16-9-7.
 */
public abstract class AbstractRegister implements Register {
    private RegisterConfig registerConfig;

    public AbstractRegister(RegisterConfig registerConfig) {
        this.registerConfig = registerConfig;
    }

    @Override
    public void register() {
        doRegister(registerConfig);
    }

    @Override
    public void subscribe(final RegisterConfig registerConfig, NotifyListener listener) {
        listener.notify(doDiscover(registerConfig));
        doSubscribe(registerConfig, listener);
    }

    @Override
    public List<DiscoverServiceConfig> discover() {
        return null;
    }


    public abstract void doRegister(RegisterConfig registerConfig);

    public abstract void doSubscribe(RegisterConfig registerConfigo, NotifyListener listener);

    public abstract List<DiscoverServiceConfig> doDiscover(RegisterConfig registerConfig);
}
