package com.ayb.rpc.core.transport;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yunbinan on 16-8-24.
 */
public class DefaultResponse implements Response {
    private Object value;
    private Exception exception;
    private long requestId;
    private long processTime;
    private int timeout;

    private Map<String, String> attachments;// rpc协议版本兼容时可以回传一些额外的信息

    public DefaultResponse() {
    }


    //为实现阻塞 同步调用的构造方法
    public DefaultResponse(Response response) {
        this.value = response.getValue();
        this.exception = response.getException();
        this.requestId = response.getRequestId();
        this.processTime = response.getProcessTime();
        this.timeout = response.getTimeout();
    }

    public DefaultResponse(Object value) {
        this.value = value;
    }

    public DefaultResponse(Object value, long requestId) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long time) {
        this.processTime = time;
    }

    public int getTimeout() {
        return this.timeout;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getAttachments() {
        return attachments != null ? attachments : Collections.EMPTY_MAP;
    }

    public void setAttachment(String key, String value) {
        if (this.attachments == null) {
            this.attachments = new HashMap<String, String>();
        }

        this.attachments.put(key, value);
    }

    @Override
    public void setRpcProtocolVersion(byte rpcProtocolVersion) {

    }

    @Override
    public byte getRpcProtocolVersion() {
        return 0;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }
}
