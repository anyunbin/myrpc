package com.ayb.rpc.core.config;

/**
 * Created by yunbinan on 16-10-26.
 */
public class ProtocolConfig {

    // 服务协议
    private String name = "default";

    // 序列化方式
    private String serialization = "protostuff";

    // 协议编码
    private String codec;

    // 请求超时
    protected Integer requestTimeout = 1000;

    // loadbalance 方式
    protected String loadbalance = "random";

    // high available strategy
    protected String haStrategy = "failover";

    // proxy type, like jdk or javassist
    protected String proxy = "jdk";

    protected String register = "zookeeper";

    protected int attempts = 3;

    protected String transport = "netty4";

    protected int minWorkerThread = 20;

    protected int maxWorkerThread = 800;

    protected int workerQueueSize = 1;

    protected boolean isAsyn = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialization() {
        return serialization;
    }

    public void setSerialization(String serialization) {
        this.serialization = serialization;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public Integer getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(Integer requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public String getHaStrategy() {
        return haStrategy;
    }

    public void setHaStrategy(String haStrategy) {
        this.haStrategy = haStrategy;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getMinWorkerThread() {
        return minWorkerThread;
    }

    public void setMinWorkerThread(int minWorkerThread) {
        this.minWorkerThread = minWorkerThread;
    }

    public int getMaxWorkerThread() {
        return maxWorkerThread;
    }

    public void setMaxWorkerThread(int maxWorkerThread) {
        this.maxWorkerThread = maxWorkerThread;
    }

    public int getWorkerQueueSize() {
        return workerQueueSize;
    }

    public void setWorkerQueueSize(int workerQueueSize) {
        this.workerQueueSize = workerQueueSize;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public boolean isAsyn() {
        return isAsyn;
    }

    public void setAsyn(boolean isAsyn) {
        this.isAsyn = isAsyn;
    }
}
