package com.ayb.rpc.transport.netty3;

import com.ayb.rpc.core.serialize.Serialization;
import com.ayb.rpc.util.SerializationUtil;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class Netty3Encoder extends OneToOneEncoder {
    private Class<?> genericClass;
    private Serialization serialization;

    public Netty3Encoder(Class<?> genericClass, Serialization serialization) {
        this.genericClass = genericClass;
        this.serialization = serialization;
    }

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel nettyChannel, Object message) throws Exception {
        if (genericClass.isInstance(message)) {
            byte[] data = serialization.serialize(message);
            int dataLength = data.length;
            ChannelBuffer buf = ChannelBuffers.dynamicBuffer();
            buf.writeInt(dataLength);
            buf.writeBytes(data);
            return buf;
        }
        return null;
    }
}
