package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by zengpu on 2017/4/1.
 */

public class BaseServiceHookHandler implements InvocationHandler {

    private static final String TAG = "BaseServiceHookHandler";

    protected Object baseService;

    public BaseServiceHookHandler(Object baseService) {
        this.baseService = baseService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "you have hooked method: " + method.getName() + " ,called with args: " + Arrays.toString(args));
        return method.invoke(baseService, args);
    }
}
