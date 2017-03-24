package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.universal_template;

/**
 * 代理模式 静态代理
 * 代理类 实现抽象主题Subject，并持有真是主题RealSubject的引用
 * Created by zengpu on 2017/3/23.
 */

public class ProxySubject extends Subject {

    private RealSubject mRealSubject; // 持有真实主题的引用

    public ProxySubject(RealSubject realSubject) {
        this.mRealSubject = realSubject;
    }

    @Override
    public void visit() {
        // 通过真实主题引用的对象调用真是主题中的逻辑方法
        mRealSubject.visit();
    }
}
