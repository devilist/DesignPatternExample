package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler;

import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zengpu on 2017/4/1.
 */

public class BinderProxyHookHandler implements InvocationHandler {

    private static final String TAG = "BinderProxyHookHandler";

    // 通过ServiceManager.getService()方法获取的IBinder
    private IBinder rawBinder;
    private IInterface serviceProxy;

    public BinderProxyHookHandler(IBinder rawBinder, IInterface serviceProxy) {
        this.rawBinder = rawBinder;
        this.serviceProxy = serviceProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在这里代理 rawBinder的方法
        //hook queryLocalInterface方法
        if ("queryLocalInterface".equals(method.getName())) {
            Log.d(TAG, "hook queryLocalInterface");
            return serviceProxy;
        }
        Log.d(TAG, "method:" + method.getName());
        return method.invoke(rawBinder, args);
    }
}
