package com.ayb.rpc.core.provider;


import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.core.transport.Response;

/**
 * Created by yunbinan on 16-9-9.
 */
public interface Provider<T> {
    Response invoke(Request request);
}
