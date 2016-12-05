package com.ayb.rpc.demo;

import com.ayb.rpc.core.config.ServiceConfig;

/**
 * Created by yunbinan on 16-9-9.
 */
public class ServerDemo {
    public static void main(String[] args) {
        ServiceConfig<SmsService> serviceConfig = new ServiceConfig<SmsService>();
        serviceConfig.setInterfaceClass(SmsService.class);
        serviceConfig.setRef(new SmsServiceImpl());
        serviceConfig.doExport();
    }
}
