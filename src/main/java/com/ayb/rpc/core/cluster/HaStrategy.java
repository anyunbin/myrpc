package com.ayb.rpc.core.cluster;

import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.core.transport.Response;

/**
 * Created by yunbinan on 16-9-19.
 */
public interface HaStrategy {
    Response call(Request request, Balance balance, int tryTime);
}
