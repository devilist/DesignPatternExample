package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.dynamic_proxy;

import app.zengpu.com.utilskit.utils.LogUtil;

/**
 * 动态代理
 * 诉讼方 主题接口的实现
 * Created by zengpu on 2017/3/23.
 */

public class Complainant implements ILawsuit {

    @Override
    public void submit() {
        LogUtil.d("Complainant","申请仲裁");
    }

    @Override
    public void burden() {
        LogUtil.d("Complainant","提交证据");
    }

    @Override
    public void defend() {
        LogUtil.d("Complainant","辩护");
    }

    @Override
    public void finish() {
        LogUtil.d("Complainant","诉讼完了，还钱");
    }
}
