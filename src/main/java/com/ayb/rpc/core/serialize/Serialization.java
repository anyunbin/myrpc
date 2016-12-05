package com.ayb.rpc.core.serialize;

import java.io.IOException;

/**
 * Created by yunbinan on 16-11-2.
 */
public interface Serialization {
    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException;
}
