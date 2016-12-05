package com.ayb.rpc.core.register;

import com.ayb.rpc.core.config.RegisterConfig;

/**
 * Created by yunbinan on 16-10-31.
 */
public abstract class AbstractRegisterFactory implements RegisterFactory {
    @Override
    public Register getRegister(RegisterConfig registerConfig) {
        return createRegister(registerConfig);
    }

    public abstract Register createRegister(RegisterConfig registerConfig);
}
