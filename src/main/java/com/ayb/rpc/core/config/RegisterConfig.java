package com.ayb.rpc.core.config;

/**
 * Created by yunbinan on 16-9-7.
 */
public class RegisterConfig {
    private String name = "zookeeper";    //注册中心协议
    private String registerHost;                  //注册中心地址
    private int registerPort;                     //注册中心端口
    private String serverHost;                    //服务地址
    private int serverPort;                       //服务端口
    private String parentPath;                    //接口名称

    public RegisterConfig() {
    }

    public RegisterConfig(String registerHost, int registerPort) {
        this.registerHost = registerHost;
        this.registerPort = registerPort;
    }

    public RegisterConfig(String registerHost, int registerPort, String serverHost) {
        this.registerHost = registerHost;
        this.registerPort = registerPort;
        this.serverHost = serverHost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterHost() {
        return registerHost;
    }

    public void setRegisterHost(String registerHost) {
        this.registerHost = registerHost;
    }

    public int getRegisterPort() {
        return registerPort;
    }

    public void setRegisterPort(int registerPort) {
        this.registerPort = registerPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    @Override
    public String toString() {
        return "RegisterConfig{" +
                "name='" + name + '\'' +
                ", registerHost='" + registerHost + '\'' +
                ", registerPort=" + registerPort +
                ", serverHost='" + serverHost + '\'' +
                ", serverPort=" + serverPort +
                ", parentPath='" + parentPath + '\'' +
                '}';
    }
}
