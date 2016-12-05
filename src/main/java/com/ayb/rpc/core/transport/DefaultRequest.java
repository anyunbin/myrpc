package com.ayb.rpc.core.transport;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yunbinan on 16-8-24.
 */
public class DefaultRequest implements Request {
    private String interfaceName;
    private String methodName;
    private String paramtersDesc;
    private Object[] arguments;
    private Map<String, String> attachments;
    private int retries = 0;

    private long requestId;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParamtersDesc() {
        return paramtersDesc;
    }

    public void setParamtersDesc(String paramtersDesc) {
        this.paramtersDesc = paramtersDesc;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
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

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String toString() {
        return interfaceName + "." + methodName + "(" + paramtersDesc + ") requestId=" + requestId;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    @Override
    public void setRpcProtocolVersion(byte rpcProtocolVersion) {

    }

    @Override
    public byte getRpcProtocolVersion() {
        return 0;
    }

}
