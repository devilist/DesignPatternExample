package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 * 实现 InvocationHandler 接口创建自己的调用处理器
 * <p>
 * 调用处理器接口InvocationHandler，它自定义了一个 invoke 方法，用于集中处理在动态代理类对象上的方法调用，
 * 通常在该方法中实现对委托类的代理访问。
 * <p>
 * Created by zengpu on 2017/3/24.
 */

public class DynamicHandler implements InvocationHandler {

    private Object obj; // 被代理的类引用

    public DynamicHandler(Object obj) {
        this.obj = obj;
    }

    /**
     * 通过invoke方法来调用具体的被代理方法(真实的方法)
     * 该方法负责集中处理动态代理类上的所有方法调用。第一个参数既是代理类实例，第二个参数是被调用的方法对象
     * 第三个方法是调用参数。调用处理器根据这三个参数进行预处理或分派到委托类实例上发射执行
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 调用被代理类对象的方法
        Object result = method.invoke(obj, args);
        return result;
    }
}
