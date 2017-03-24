package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.dynamic_proxy;

/**
 * 动态代理
 * 主题接口
 * Created by zengpu on 2017/3/23.
 */

public interface ILawsuit {

    void submit(); // 提交申请

    void burden(); // 进行举证

    void defend(); // 开始辩护

    void finish(); // 诉讼完成
}
