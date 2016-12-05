package com.ayb.rpc.core.handler;

/**
 * Created by yunbinan on 16-8-24.
 */
public interface MessageHandler {
    Object handle(Object message);
}
