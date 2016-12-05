package com.ayb.rpc.core.proxy;

import com.ayb.rpc.core.cluster.Cluster;
import com.ayb.rpc.core.transport.DefaultRequest;
import com.ayb.rpc.core.transport.Response;
import com.ayb.rpc.util.ReflectUtil;
import com.ayb.rpc.util.RequestIdGenerator;
import net.sf.cglib.proxy.InvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * Created by yunbinan on 16-8-31.
 */
public class RefererInvocationHandler<T> implements InvocationHandler {
    private static Logger logger = LoggerFactory.getLogger(RefererInvocationHandler.class);
    private Class<T> clz;
    private Cluster<T> cluster;

    public RefererInvocationHandler(Class<T> clz, Cluster<T> cluster) {
        this.clz = clz;
        this.cluster = cluster;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        DefaultRequest request = new DefaultRequest();
        request.setRequestId(RequestIdGenerator.getRequestId());
        request.setInterfaceName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setArguments(args);
        request.setParamtersDesc(ReflectUtil.getMethodParamDesc(method));
        Response response;
        //System.out.println(System.nanoTime() - start);
        response = cluster.call(request);
        //System.out.println(System.nanoTime() - start);
        Object result = response.getValue();
        //System.out.println(System.nanoTime() - start);
        return result;
    }
}
