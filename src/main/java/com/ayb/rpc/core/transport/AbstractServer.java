package com.ayb.rpc.core.transport;

import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.export.Exporter;
import com.ayb.rpc.core.extension.ExtensionLoader;
import com.ayb.rpc.core.serialize.Serialization;
import org.jboss.netty.channel.*;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yunbinan on 16-11-2.
 */
public abstract class AbstractServer implements Server {
    protected Serialization serialization;
    protected ThreadPoolExecutor executorService;
    protected Exporter exporter;
    protected RegisterConfig config;


    public AbstractServer(Exporter exporter, RegisterConfig config, ProtocolConfig protocolConfig) {
        this.exporter = exporter;
        this.config = config;
        serialization = ExtensionLoader.getExtensionLoader(Serialization.class).getExtension(protocolConfig.getSerialization());
        executorService = new ThreadPoolExecutor(protocolConfig.getMinWorkerThread(), protocolConfig.getMaxWorkerThread(), 1, TimeUnit.MINUTES, new LinkedBlockingQueue(protocolConfig.getWorkerQueueSize()), new ThreadPoolExecutor.CallerRunsPolicy()); //TODO 暂时选用该线程池
    }


}
