package com.ayb.rpc.core.extension;

import com.ayb.rpc.core.protocol.Protocol;

import java.io.*;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by yunbinan on 16-10-27.
 */
public class ExtensionLoader<T> {
    private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> extensionLoaders = new ConcurrentHashMap<Class<?>, ExtensionLoader<?>>();
    private ConcurrentMap<String, Class<?>> extensionClasses = new ConcurrentHashMap<String, Class<?>>();
    private ConcurrentMap<String, Object> extensionInstances = new ConcurrentHashMap<String, Object>();
    private static final String SERVICES_DIRECTORY = "META-INF/services/";
    private Class<T> type;

    private ExtensionLoader(Class<T> type) {
        this.type = type;
    }

    /**
     * 泛型方法
     * 返回类型前面的<T>声明方法中涉及到的类型
     * Class<T>中的T无需指明，是编译器通过检查type参数类型断定的
     */
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        ExtensionLoader<?> loader = extensionLoaders.get(type);
        if (loader == null) {
            loader = new ExtensionLoader<T>(type);
            extensionLoaders.putIfAbsent(type, loader);
        }
        return (ExtensionLoader<T>) loader;
    }

    public T getExtension(String name) {
        T instance = (T) extensionInstances.get(name);
        if (instance == null) {
            Class<?> clazz = extensionClasses.get(name);
            if (clazz == null) {
                loadExtensionClasses(SERVICES_DIRECTORY);
            }
            clazz = extensionClasses.get(name);
            try {
                extensionInstances.putIfAbsent(name, clazz.newInstance());
                instance = (T) extensionInstances.get(name);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private void loadExtensionClasses(String dir) {
        String fileName = dir + type.getName();
        ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
        URL url = classLoader.getResource(fileName);
        if (url != null) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] info = line.split("=");
                    String name = info[0];
                    String className = info[1];
                    Class<?> clazz = Class.forName(className, true, classLoader);
                    extensionClasses.putIfAbsent(name, clazz);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //SPI
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("default");
    }

}
