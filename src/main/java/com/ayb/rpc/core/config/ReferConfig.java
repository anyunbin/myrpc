package com.ayb.rpc.core.config;

import com.ayb.rpc.core.cluster.*;
import com.ayb.rpc.core.extension.ExtensionLoader;
import com.ayb.rpc.core.module.URL;
import com.ayb.rpc.core.protocol.DefaultProtocol;
import com.ayb.rpc.core.protocol.Protocol;
import com.ayb.rpc.core.proxy.JdkProxyFactory;
import com.ayb.rpc.core.proxy.ProxyFactory;
import com.ayb.rpc.core.proxy.RefererInvocationHandler;
import com.ayb.rpc.core.refer.Referer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunbinan on 16-8-29.
 */
public class ReferConfig<T> extends AbstractConfig {
    private Class<T> interfaceClass;
    private T ref;    //代理对象
    private RegisterConfig registerConfig;
    private Cluster cluster;
    private ProxyFactory proxyFactory;
    private ProtocolConfig protocolConfig = new ProtocolConfig();  //TODO 暂时New出来


    public void initRef() {
        registerConfig = loadRegistryUrl();  //获取注册配置
        registerConfig.setParentPath(interfaceClass.getName());

        cluster = new DefaultCluster(interfaceClass, registerConfig, protocolConfig);   //已知接口和注册中心协议等信息 获取服务 并封装成集群
        proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getExtension(protocolConfig.getProxy());  //TODO 代理机制选择
        ref = proxyFactory.getProxy(interfaceClass, new RefererInvocationHandler<T>(interfaceClass, cluster));

    }

    public T getRef() {
        if (ref == null) {
            initRef();
        }
        return ref;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
