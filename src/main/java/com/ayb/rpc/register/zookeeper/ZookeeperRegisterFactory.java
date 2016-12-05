package com.ayb.rpc.register.zookeeper;

import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.register.AbstractRegisterFactory;
import com.ayb.rpc.core.register.Register;

/**
 * Created by yunbinan on 16-10-31.
 */
public class ZookeeperRegisterFactory extends AbstractRegisterFactory {
    @Override
    public Register createRegister(RegisterConfig registerConfig) {
        return new ZookeeperRegister(registerConfig);
    }
}
