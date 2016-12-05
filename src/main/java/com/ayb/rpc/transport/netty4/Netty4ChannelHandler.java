package com.ayb.rpc.transport.netty4;

import com.ayb.rpc.core.handler.MessageHandler;
import com.ayb.rpc.core.provider.Provider;
import com.ayb.rpc.core.transport.DefaultResponse;
import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.core.transport.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by yunbinan on 2016/6/26.
 */
public class Netty4ChannelHandler extends SimpleChannelInboundHandler {
    private static Logger logger = LoggerFactory.getLogger(Netty4ChannelHandler.class);
    private ExecutorService executorService;
    private Provider provider;
    private MessageHandler messageHandler;

    public Netty4ChannelHandler(Provider provider, ExecutorService executorService) {
        this.executorService = executorService;
        this.provider = provider;
    }

    public Netty4ChannelHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object message = msg;
        if (message instanceof Request) {
            processRequest(ctx, (Request) message);
        } else if (message instanceof Response) {
            processResponse(ctx, message);
        }
        System.out.println(msg);
    }

    //服务端接收到客户端的请求
    private void processRequest(final ChannelHandlerContext channelHandlerContext, Object msg) {
        final Request request = (Request) msg;
        final long processStartTime = System.currentTimeMillis();
        // 使用线程池方式处理
        try {
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    Object result = provider.invoke(request);

                    DefaultResponse response = null;

                    if (!(result instanceof DefaultResponse)) {
                        response = new DefaultResponse(result);
                    } else {
                        response = (DefaultResponse) result;
                    }

                    response.setRequestId(request.getRequestId());
                    response.setProcessTime(System.currentTimeMillis() - processStartTime);

                    if (channelHandlerContext.channel().isActive()) {
                        channelHandlerContext.channel().write(response);
                    }
                }
            });
        } catch (RejectedExecutionException rejectException) {
            DefaultResponse response = new DefaultResponse();
            response.setRequestId(request.getRequestId());
            response.setProcessTime(System.currentTimeMillis() - processStartTime);
            channelHandlerContext.channel().write(response);
        }
    }

    //客户端收到服务端的回馈
    private void processResponse(ChannelHandlerContext channelHandlerContext, Object msg) {
        messageHandler.handle(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        ctx.channel().close();
    }
}
