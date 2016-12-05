package com.ayb.rpc.core.cluster;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.extension.ExtensionLoader;
import com.ayb.rpc.core.protocol.Protocol;
import com.ayb.rpc.core.refer.Referer;
import com.ayb.rpc.core.register.NotifyListener;
import com.ayb.rpc.core.register.Register;
import com.ayb.rpc.core.register.RegisterFactory;
import com.ayb.rpc.core.transport.DefaultResponse;
import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.core.transport.Response;
import com.ayb.rpc.core.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunbinan on 16-8-30.
 */
public abstract class AbstractCluster<T> implements Cluster<T>, NotifyListener {
    private static Logger logger = LoggerFactory.getLogger(AbstractCluster.class);
    private Class<T> interfaceClass;
    private Balance<T> balance;
    private HaStrategy haStrategy;
    private Protocol<T> protocol;
    private RegisterFactory registerFactory;
    private ProtocolConfig protocolConfig;


    public AbstractCluster(Class<T> interfaceClass, RegisterConfig registerConfig, ProtocolConfig protocolConfig) {
        this.interfaceClass = interfaceClass;
        this.protocolConfig = protocolConfig;
        protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(protocolConfig.getName());  //TODO 选择协议
        haStrategy = ExtensionLoader.getExtensionLoader(HaStrategy.class).getExtension(protocolConfig.getHaStrategy());  //TODO 容错机制
        balance = ExtensionLoader.getExtensionLoader(Balance.class).getExtension(protocolConfig.getLoadbalance()); //TODO 选择轮询机制
        registerFactory = ExtensionLoader.getExtensionLoader(RegisterFactory.class).getExtension(protocolConfig.getRegister()); //TODO 选择注册中心 并注册服务
        Register register = registerFactory.getRegister(registerConfig); //TODO 注册服务、发现服务、监听服务 协议选择
        register.subscribe(registerConfig, this);
    }

    public Response call(Request request) {
        try {
            return haStrategy.call(request, balance, protocolConfig.getAttempts());
        } catch (Exception e) {
            e.printStackTrace();
            return buildErrorReponse(request, e);
        }
    }

    public Response buildErrorReponse(Request request, Exception e) {
        if (e instanceof BizException) {
            throw (RuntimeException) e;
        }
        //TODO 考虑其余异常是否抛出
        DefaultResponse response = new DefaultResponse();
        response.setRequestId(request.getRequestId());
        response.setException(e);
        return response;
    }

    public void onRefresh(List<Referer<T>> referers) {
        balance.onRefresh(referers);
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    @Override
    public void notify(List<DiscoverServiceConfig> configs) {
        List<Referer<T>> referers = new ArrayList<Referer<T>>();
        for (DiscoverServiceConfig config : configs) {
            Referer referer = balance.getExistingReferer(config);
            logger.info("get referer:{}", referer);
            if (referer == null) {
                Referer ref = protocol.refer(interfaceClass, config, protocolConfig);
                ref.setTimeout(protocolConfig.getRequestTimeout());
                ref.setAsyn(protocolConfig.isAsyn());
                referers.add(ref);
            } else {
                referers.add(referer);
            }
        }
        onRefresh(referers);
    }

}
