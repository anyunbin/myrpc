package com.ayb.rpc.transport.netty3;

import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.export.Exporter;
import com.ayb.rpc.core.transport.*;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * Created by yunbinan on 16-8-25.
 */
public class Netty3Server extends AbstractServer {
    private static Logger logger = LoggerFactory.getLogger(Netty3Server.class);
    private final static ChannelFactory channelFactory = new NioServerSocketChannelFactory(
            Executors.newSingleThreadExecutor(),
            Executors.newCachedThreadPool());
    private org.jboss.netty.bootstrap.ServerBootstrap bootstrap;
    private Channel channel;

    public Netty3Server(Exporter exporter, RegisterConfig config, ProtocolConfig protocolConfig) {
        super(exporter, config, protocolConfig);
        open();
    }

    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    public Collection<com.ayb.rpc.core.transport.Channel> getChannels() {
        return null;
    }

    @Override
    public com.ayb.rpc.core.transport.Channel getChannel(InetSocketAddress remoteAddress) {
        return null;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public void request(Request request) {

    }

    @Override
    public boolean open() {
        try {
            bootstrap = new ServerBootstrap(channelFactory);
            final Netty3ChannelHandler handler = new Netty3ChannelHandler(exporter.getProvider(), executorService);
            bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                // FrameDecoder非线程安全，每个连接一个 Pipeline
                public ChannelPipeline getPipeline() {
                    ChannelPipeline pipeline = Channels.pipeline();
                    pipeline.addLast("decoder", new Netty3Decoder(DefaultRequest.class, serialization));
                    pipeline.addLast("encoder", new Netty3Encoder(DefaultResponse.class, serialization));
                    pipeline.addLast("handler", handler);
                    return pipeline;
                }
            });
            channel = bootstrap.bind(new InetSocketAddress(config.getServerHost(), config.getServerPort()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void close(int timeout) {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}
