package com.ayb.rpc.core.register;

import com.ayb.rpc.core.config.RegisterConfig;

/**
 * Created by yunbinan on 16-10-31.
 */
public interface RegisterFactory {
    public Register getRegister(RegisterConfig registerConfig);
}
