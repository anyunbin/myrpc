package com.ayb.rpc.core.config;

import com.ayb.rpc.core.export.Exporter;
import com.ayb.rpc.core.extension.ExtensionLoader;
import com.ayb.rpc.core.protocol.DefaultProtocol;
import com.ayb.rpc.core.protocol.Protocol;
import com.ayb.rpc.core.provider.AbstractProvider;
import com.ayb.rpc.core.provider.DefaultProvider;
import com.ayb.rpc.core.provider.Provider;
import com.ayb.rpc.core.register.Register;
import com.ayb.rpc.core.register.RegisterFactory;
import com.ayb.rpc.register.zookeeper.ZookeeperRegister;
import com.ayb.rpc.util.NetWorkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by yunbinan on 16-8-24.
 * 一个ServiceConfig只对应一个接口的多个方法
 */
public class ServiceConfig<T> extends AbstractConfig {
    private static Logger logger = LoggerFactory.getLogger(ServiceConfig.class);
    private ProtocolConfig protocolConfig = new ProtocolConfig();  //TODO 暂时New出来
    private Class<T> interfaceClass;  //服务接口
    private Protocol<T> protocol;
    private RegisterConfig registerConfig; //注册中心
    private String hostAddress;
    private int port;   //服务占用地址
    private T ref; //接口实现类的实例

    public void doExport() {
        port = protocolConfig.getPort();
        registerConfig = loadRegistryUrl();  //获取注册配置 目前只支持一个注册中心
        registerConfig.setParentPath(interfaceClass.getName());
        registerConfig.setServerPort(port);
        hostAddress = NetWorkUtil.getHostAddress();

        Provider<T> provider = new DefaultProvider(interfaceClass, ref);  //根据接口 定义服务最终执行者
        protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(protocolConfig.getName());  //协议
        Exporter exporter = protocol.export(provider, registerConfig, protocolConfig);  //选择通信协议 并暴露服务
        RegisterFactory registerFactory = ExtensionLoader.getExtensionLoader(RegisterFactory.class).getExtension(protocolConfig.getRegister()); //TODO 选择注册中心 并注册服务
        Register register = registerFactory.getRegister(registerConfig);
        register.register();
        logger.info("start server port:{}", port);
        LockSupport.park();
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }
}
