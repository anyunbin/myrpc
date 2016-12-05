package com.ayb.rpc.core.proxy;

import net.sf.cglib.proxy.InvocationHandler;

public interface ProxyFactory {
    <T> T getProxy(Class<T> clz, InvocationHandler invocationHandler);
}
