package com.ayb.rpc.transport.netty3;

import com.ayb.rpc.core.serialize.Serialization;
import com.ayb.rpc.util.SerializationUtil;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class Netty3Decoder extends FrameDecoder {
    private Class<?> genericClass;
    private Serialization serialization;

    public Netty3Decoder(Class<?> genericClass, Serialization serialization) {
        this.genericClass = genericClass;
        this.serialization = serialization;
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() < 4) {
            return null;
        }
        buffer.markReaderIndex();
        int length = buffer.readInt();
        if (buffer.readableBytes() < length) {
            buffer.resetReaderIndex();
            return null;
        }

        byte[] data = new byte[length];
        buffer.readBytes(data);

        Object obj = serialization.deserialize(data, genericClass);
        return obj;
    }

}
