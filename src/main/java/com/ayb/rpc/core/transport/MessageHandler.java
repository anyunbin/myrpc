package com.ayb.rpc.core.transport;

/**
 * Created by yunbinan on 16-8-31.
 */
public interface MessageHandler {
    Object handle(Object message);
}
