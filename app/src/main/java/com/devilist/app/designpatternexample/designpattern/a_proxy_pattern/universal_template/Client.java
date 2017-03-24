package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.universal_template;

/**
 * 代理模式
 * 客户类
 * Created by zengpu on 2017/3/23.
 */

public class Client {

    public static void main() {
        // 构造一个真实主题对象
        RealSubject real= new RealSubject();
        // 构造一个代理对象
        ProxySubject proxy = new ProxySubject(real);
        // 调用代理的相关方法
        proxy.visit();
    }
}
