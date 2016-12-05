package com.ayb.rpc.core.refer;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.core.transport.Response;
import com.ayb.rpc.core.transport.ResponseFuture;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by yunbinan on 16-8-25.
 */
public interface Referer<T> {
    Response call(Request request);

    DiscoverServiceConfig getConfig();

    ResponseFuture removeResponseFuture(Response response);

    void setTimeout(int timeout);

    void setAsyn(boolean asyn);

}
