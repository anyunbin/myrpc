package com.ayb.rpc.core.export;

import com.ayb.rpc.core.provider.AbstractProvider;
import com.ayb.rpc.core.provider.Provider;

/**
 * Created by yunbinan on 16-8-25.
 */
public interface Exporter<T> {
    Provider<T> getProvider();
}
