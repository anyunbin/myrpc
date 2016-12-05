package com.ayb.rpc.core.cluster;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.refer.Referer;
import com.ayb.rpc.core.transport.Request;

import java.util.List;

/**
 * Created by yunbinan on 16-8-31.
 */
public interface Balance<T> {

    void onRefresh(List<Referer<T>> referers);

    Referer<T> select(Request request);

    Referer<T> getExistingReferer(DiscoverServiceConfig discoverServiceConfig);

    List<Referer<T>> getReferers();
}
