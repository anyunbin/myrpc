package com.ayb.rpc.core.provider;

import com.ayb.rpc.core.transport.Request;
import com.ayb.rpc.core.transport.Response;
import com.ayb.rpc.util.ReflectUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yunbinan on 16-8-24.
 */
public abstract class AbstractProvider<T> implements Provider<T> {
    protected Class<T> interfaceClass;  //接口
    protected Map<String, Method> methodMap = new HashMap<String, Method>();   //接口中Method缓存

    //抽离出接口方法
    public AbstractProvider(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
        Method[] methods = interfaceClass.getMethods();
        for (Method method : methods) {
            String methodDesc = ReflectUtil.getMethodDesc(method);
            methodMap.put(methodDesc, method);
        }
    }

    @Override
    public Response invoke(Request request) {
        return doInvoke(request);
    }

    public abstract Response doInvoke(Request request);
}
