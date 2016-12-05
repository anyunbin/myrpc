package com.ayb.rpc.transport.netty3;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.refer.AbstractReferer;
import com.ayb.rpc.core.refer.Referer;
import com.ayb.rpc.core.transport.*;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by yunbinan on 16-8-31.
 */
public class Netty3Client extends AbstractClient {

    private static Logger logger = LoggerFactory.getLogger(Netty3Client.class);
    private static final ChannelFactory channelFactory = new NioClientSocketChannelFactory(
            Executors.newCachedThreadPool(),
            Executors.newCachedThreadPool());
    private ClientBootstrap bootstrap;
    private ChannelFuture channel = null;


    public Netty3Client(DiscoverServiceConfig config, Referer referer, ProtocolConfig protocolConfig) {
        super(config, referer, protocolConfig);
        open();
    }

    @Override
    public void heartbeat(Request request) {

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
        channel.getChannel().write(request);
    }

    @Override
    public boolean open() {
        try {
            bootstrap = new ClientBootstrap(channelFactory);
            bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                public ChannelPipeline getPipeline() {
                    ChannelPipeline pipeline = Channels.pipeline();
                    pipeline.addLast("decoder", new Netty3Decoder(DefaultResponse.class, serialization));
                    pipeline.addLast("encoder", new Netty3Encoder(DefaultRequest.class, serialization));
                    pipeline.addLast("handler", new Netty3ChannelHandler(new com.ayb.rpc.core.handler.MessageHandler() {
                        @Override
                        public Object handle(Object message) {
                            Response response = (Response) message;
                            ResponseFuture responseFuture = (ResponseFuture) referer.removeResponseFuture(response);
                            responseFuture.done(response);
                            return null;
                        }
                    }));
                    return pipeline;
                }
            });
            channel = bootstrap.connect(new InetSocketAddress(config.getHost(), config.getPort()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
