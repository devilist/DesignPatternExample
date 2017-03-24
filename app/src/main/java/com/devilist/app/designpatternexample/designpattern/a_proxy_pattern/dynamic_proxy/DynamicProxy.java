package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 * 实现android提供的动态代理接口InvocationHandler
 * Created by zengpu on 2017/3/24.
 */

public class DynamicProxy implements InvocationHandler {

    private Object obj; // 被代理的类引用

    public DynamicProxy(Object obj) {
        this.obj = obj;
    }

    /**
     * 通过invoke方法来调用具体的被代理方法(真实的方法)
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 调用被代理类对象的方法
        Object result = method.invoke(obj, args);
        return result;
    }
}
