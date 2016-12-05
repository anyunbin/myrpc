package com.ayb.rpc.transport.netty4;

import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.export.Exporter;
import com.ayb.rpc.core.transport.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * Created by yunbinan on 16-9-26.
 */
public class Netty4Server extends AbstractServer {
    private io.netty.bootstrap.ServerBootstrap bootstrap;
    private ChannelFuture future = null;

    public Netty4Server(Exporter exporter, RegisterConfig config, ProtocolConfig protocolConfig) {
        super(exporter, config, protocolConfig);
        open();
    }

    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    public Collection<Channel> getChannels() {
        return null;
    }

    @Override
    public Channel getChannel(InetSocketAddress remoteAddress) {
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
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final Netty4ChannelHandler netty4ChannelHandler = new Netty4ChannelHandler(exporter.getProvider(), executorService);
        try {
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast("decoder", new Netty4Decoder(DefaultRequest.class, serialization)) // 将 RPC 请求进行解码（为了处理请求）
                                    .addLast("encoder", new Netty4Encoder(DefaultResponse.class, serialization)) // 将 RPC 响应进行编码（为了返回响应）
                                    .addLast("handler", netty4ChannelHandler); // 处理 RPC 请求
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            future = bootstrap.bind(new InetSocketAddress(config.getServerHost(), config.getServerPort())).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }/* finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }*/
        return true;
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
