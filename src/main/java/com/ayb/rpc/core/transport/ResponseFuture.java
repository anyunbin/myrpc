package com.ayb.rpc.core.transport;

import com.ayb.rpc.core.exception.RpcException;
import com.ayb.rpc.util.Constant;
import sun.misc.Lock;

import java.util.Map;

/**
 * Created by yunbinan on 16-9-12.
 */
public class ResponseFuture implements Response, Future {
    private volatile FutureState state = FutureState.DOING;
    private Lock lock = new Lock();
    private Request request;
    private Object result;
    private Exception exception = null;
    private int timeout = 0;
    private long processTime = 0;
    private long createTime = System.currentTimeMillis();

    public ResponseFuture(Request request, int timeout) {
        this.request = request;
        this.timeout = timeout;
    }

    public void done(Response response) {
        if (response.getValue() != null)
            this.result = response.getValue();
        if (response.getException() != null)
            this.exception = response.getException();
        this.processTime = response.getProcessTime();
        synchronized (lock) {
            //非doing
            if (!isDoing()) {
                return;
            }
            state = FutureState.DONE; //修改状态
            lock.notifyAll(); //唤醒被lock阻塞的线程(因getValue阻塞)
        }
    }

    @Override
    public Object getValue() {
        synchronized (lock) {
            if (isDoing()) {
                if (timeout <= 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    long waitTime = timeout - (System.currentTimeMillis() - createTime);
                    if (waitTime > 0) {
                        for (; ; ) {
                            try {
                                lock.wait(waitTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (!isDoing()) {
                                break;
                            } else {
                                waitTime = timeout - (System.currentTimeMillis() - createTime);
                                if (waitTime <= 0) {
                                    throw new RpcException();
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean cancel() {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return state.isCancelledState();
    }

    @Override
    public boolean isDone() {
        return state.isDoneState();
    }

    private boolean isDoing() {
        return state.isDoingState();
    }

    @Override
    public boolean isSuccess() {
        return isDone() && (exception == null);
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public long getRequestId() {
        return request.getRequestId();
    }

    @Override
    public long getProcessTime() {
        return processTime;
    }

    @Override
    public void setProcessTime(long time) {
        this.processTime = processTime;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    @Override
    public Map<String, String> getAttachments() {
        return null;
    }

    @Override
    public void setAttachment(String key, String value) {

    }

    @Override
    public void setRpcProtocolVersion(byte rpcProtocolVersion) {

    }

    @Override
    public byte getRpcProtocolVersion() {
        return 0;
    }
}
