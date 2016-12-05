package com.ayb.rpc.core.export;

import com.ayb.rpc.core.provider.Provider;

/**
 * Created by yunbinan on 16-8-24.
 */
public abstract class AbstractExporter<T> implements Exporter {
    private Provider<T> provider;

    public AbstractExporter(Provider<T> provider) {
        this.provider = provider;
    }

    @Override
    public Provider getProvider() {
        return provider;
    }
}
