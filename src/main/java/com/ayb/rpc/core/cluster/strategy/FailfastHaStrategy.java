package com.ayb.rpc.core.cluster.strategy;

import com.ayb.rpc.core.cluster.AbstractHaStrategy;
import com.ayb.rpc.core.cluster.Balance;
import com.ayb.rpc.core.refer.Referer;
import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.core.transport.Response;
import com.ayb.rpc.core.exception.RpcException;

/**
 * Created by yunbinan on 16-9-19.
 */
public class FailfastHaStrategy extends AbstractHaStrategy {

    @Override
    public Response call(Request request, Balance balance, int attempts) {
        Referer referer = balance.select(request);
        if (referer == null) {
            throw new RpcException();
        }
        return referer.call(request);
    }
}
