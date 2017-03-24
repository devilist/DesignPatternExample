package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.universal_template;

import app.zengpu.com.utilskit.utils.LogUtil;

/**
 * 代理模式
 * 真实主题类 实现了抽象主题
 * Created by zengpu on 2017/3/23.
 */

public class RealSubject extends Subject {

    @Override
    public void visit() {
        // 真实主题类中的具体逻辑
        LogUtil.d("RealSubject","真实主题类中的具体逻辑");
    }
}
