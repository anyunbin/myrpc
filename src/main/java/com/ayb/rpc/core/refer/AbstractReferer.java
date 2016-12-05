package com.ayb.rpc.core.refer;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.transport.DefaultResponse;
import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.core.transport.Response;
import com.ayb.rpc.core.transport.ResponseFuture;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by yunbinan on 16-8-31.
 */
public abstract class AbstractReferer<T> implements Referer {

    protected DiscoverServiceConfig config;

    public AbstractReferer(DiscoverServiceConfig config) {
        this.config = config;
    }

    private ConcurrentMap<Long, ResponseFuture> callbackMap = new ConcurrentHashMap<Long, ResponseFuture>();

    protected int timeout = 0;

    protected boolean isAsyn = false;

    @Override
    public Response call(Request request) {
        Response response = doCall(request);
        return asyncResponse(response, isAsyn);
    }

    protected abstract Response doCall(Request request);


    private Response asyncResponse(Response response, boolean async) {
        //异步调用
        if (async || !(response instanceof ResponseFuture)) {
            return response;
        }
        //同步调用 构造DefaultReponse时调用了getValue方法造成阻塞
        return new DefaultResponse(response);
    }

    public void putResponseFuture(Request request, ResponseFuture response) {
        callbackMap.put(request.getRequestId(), response);
    }

    public ResponseFuture removeResponseFuture(Response response) {
        return callbackMap.remove(response.getRequestId());
    }

    public ConcurrentMap<Long, ResponseFuture> getCallbackMap() {
        return callbackMap;
    }

    public DiscoverServiceConfig getConfig() {
        return config;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setAsyn(boolean asyn) {
        this.isAsyn = asyn;
    }
}
