package com.ayb.rpc.transport.netty4;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.handler.*;
import com.ayb.rpc.core.handler.MessageHandler;
import com.ayb.rpc.core.refer.Referer;
import com.ayb.rpc.core.transport.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by yunbinan on 16-9-26.
 */
public class Netty4Client extends AbstractClient {
    private Bootstrap bootstrap = null;
    private EventLoopGroup group = null;
    private ChannelFuture channel = null;

    public Netty4Client(DiscoverServiceConfig config, Referer referer, ProtocolConfig protocolConfig) {
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
        channel.channel().writeAndFlush(request);
    }

    @Override
    public boolean open() {
        try {
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast("encoder", new Netty4Encoder(DefaultRequest.class, serialization)) // 将 RPC 请求进行编码（为了发送请求）
                                    .addLast("decoder", new Netty4Decoder(DefaultResponse.class, serialization)) // 将 RPC 响应进行解码（为了处理响应）
                                    .addLast("handler", new Netty4ChannelHandler(new MessageHandler() {
                                        @Override
                                        public Object handle(Object message) {
                                            Response response = (Response) message;
                                            ResponseFuture responseFuture = (ResponseFuture) referer.removeResponseFuture(response);
                                            responseFuture.done(response);
                                            return null;
                                        }
                                    })); // 使用 RpcClient 发送 RPC 请求
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
            channel = bootstrap.connect(config.getHost(), config.getPort()).sync();
            channel.channel().write(new DefaultRequest());
            System.out.println(channel.channel().isOpen());
            return true;
        } catch (InterruptedException e) {
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
