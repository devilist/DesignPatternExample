package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.dynamic_proxy;

import java.lang.reflect.Proxy;

/**
 * Created by zengpu on 2017/3/24.
 */

public class Client {

    public static void main() {
        // 构造被代理者
        ILawsuit complainant = new Complainant();
        // 构造动态代理
        DynamicProxy proxy = new DynamicProxy(complainant);
        // 获取被代理类的ClassLoader
        ClassLoader classLoader = Complainant.class.getClassLoader();

        // 动态构造一个代理者律师
        ILawsuit proxyLawyer = (ILawsuit) Proxy.newProxyInstance(classLoader,
                new Class[]{ILawsuit.class}, proxy);

        // 代理者律师去打官司
        proxyLawyer.submit();
        proxyLawyer.burden();
        proxyLawyer.defend();
        proxyLawyer.finish();
    }
}
