package com.ayb.rpc.core.proxy;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

public class JdkProxyFactory implements ProxyFactory {

    public <T> T getProxy(Class<T> clz, InvocationHandler invocationHandler) {
        Object object = null;
        try {
            object = Proxy.newProxyInstance(clz.getClassLoader(), new Class<?>[]{clz}, invocationHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }
}
