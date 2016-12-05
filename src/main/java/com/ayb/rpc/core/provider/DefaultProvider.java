package com.ayb.rpc.core.provider;

import com.ayb.rpc.core.transport.DefaultRequest;
import com.ayb.rpc.core.transport.DefaultResponse;
import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yunbinan on 16825.
 */
public class DefaultProvider<T> extends AbstractProvider {
    private T interfaceImpl;

    public DefaultProvider(Class interfaceClass, T interfaceImpl) {
        super(interfaceClass);
        this.interfaceImpl = interfaceImpl;
    }

    @Override
    public DefaultResponse doInvoke(Request request) {
        DefaultResponse response = new DefaultResponse();
        Method method = lookup(request);
        Object result = null;
        try {
            result = method.invoke(interfaceImpl, request.getArguments());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        response.setValue(result);
        return response;
    }

    protected Method lookup(Request request) {
        String methodDesc = ReflectUtil.getMethodDesc(request.getMethodName(), request.getParamtersDesc());
        return (Method) methodMap.get(methodDesc);
    }

}
