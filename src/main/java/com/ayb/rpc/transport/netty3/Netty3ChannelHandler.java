package com.ayb.rpc.transport.netty3;

import com.ayb.rpc.core.handler.MessageHandler;
import com.ayb.rpc.core.provider.Provider;
import com.ayb.rpc.core.transport.DefaultResponse;
import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.core.transport.Response;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

public class Netty3ChannelHandler extends SimpleChannelHandler {
    private static Logger logger = LoggerFactory.getLogger(Netty3ChannelHandler.class);
    private ExecutorService executorService;
    private MessageHandler messageHandler;
    private Provider provider;

    public Netty3ChannelHandler(Provider provider, ExecutorService executorService) {
        this.provider = provider;
        this.executorService = executorService;
    }

    public Netty3ChannelHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.info("NettyChannelHandler channelConnected: remote=" + ctx.getChannel().getRemoteAddress()
                + " local=" + ctx.getChannel().getLocalAddress() + " event=" + e.getClass().getSimpleName());
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.info("NettyChannelHandler channelDisconnected: remote=" + ctx.getChannel().getRemoteAddress()
                + " local=" + ctx.getChannel().getLocalAddress() + " event=" + e.getClass().getSimpleName());
    }

    /*
    * 客户端收消息Response
    * 服务端收消息Request
    * */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Object message = e.getMessage();
        if (message instanceof Request) {
            processRequest(ctx, e);
        } else if (message instanceof Response) {
            processResponse(ctx, e);
        } else {
            logger.error("NettyChannelHandler messageReceived type not support: class=" + message.getClass());
        }
    }

    /**
     * <pre>
     *  request process: 主要来自于client的请求，需要使用threadPoolExecutor进行处理，避免service message处理比较慢导致iothread被阻塞
     * </pre>
     *
     * @param ctx
     * @param e
     */
    private void processRequest(final ChannelHandlerContext ctx, MessageEvent e) {
        final Request request = (Request) e.getMessage();

        final long processStartTime = System.currentTimeMillis();
        // 使用线程池方式处理
        try {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    processRequest(ctx, request, processStartTime);
                }
            });
        } catch (RejectedExecutionException rejectException) {
            DefaultResponse response = new DefaultResponse();
            response.setRequestId(request.getRequestId());
            response.setProcessTime(System.currentTimeMillis() - processStartTime);
            e.getChannel().write(response);
        }
    }

    private void processRequest(ChannelHandlerContext ctx, Request request, long processStartTime) {
        Object result = provider.invoke(request);

        DefaultResponse response = null;

        if (!(result instanceof DefaultResponse)) {
            response = new DefaultResponse(result);
        } else {
            response = (DefaultResponse) result;
        }

        response.setRequestId(request.getRequestId());
        response.setProcessTime(System.currentTimeMillis() - processStartTime);

        if (ctx.getChannel().isConnected()) {
            ctx.getChannel().write(response);
        }
        //logger.info("remote:{} response:{}", ctx.getChannel().getRemoteAddress(), response);
    }

    private void processResponse(ChannelHandlerContext ctx, MessageEvent e) {
        messageHandler.handle(e.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        logger.error("NettyChannelHandler exceptionCaught: remote=" + ctx.getChannel().getRemoteAddress()
                + " local=" + ctx.getChannel().getLocalAddress() + " event=" + e.getCause(), e.getCause());
        ctx.getChannel().close();
    }
}
