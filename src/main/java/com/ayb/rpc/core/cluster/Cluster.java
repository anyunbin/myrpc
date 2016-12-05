package com.ayb.rpc.core.cluster;

import com.ayb.rpc.core.refer.Referer;
import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.core.transport.Response;

import java.util.List;

/**
 * Created by yunbinan on 16-8-31.
 */
public interface Cluster<T> {
    public Response call(Request request);

    void setBalance(Balance balance);

    void onRefresh(List<Referer<T>> referers);
}
