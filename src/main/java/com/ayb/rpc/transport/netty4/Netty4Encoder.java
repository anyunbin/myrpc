package com.ayb.rpc.transport.netty4;

import com.ayb.rpc.core.serialize.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by yunbinan on 2016/6/26.
 */
public class Netty4Encoder extends MessageToByteEncoder {
    private Class<?> genericClass;
    private Serialization serialization;

    public Netty4Encoder(Class<?> genericClass, Serialization serialization) {
        this.genericClass = genericClass;
        this.serialization = serialization;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = serialization.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
