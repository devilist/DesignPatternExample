package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.impl;

import android.util.Log;
import android.widget.Toast;

import com.devilist.app.designpatternexample.MyApplication;
import com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.BaseServiceHookHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * PMS
 * Created by zengpu on 2017/4/1.
 */

public class PMSHookHandler extends BaseServiceHookHandler {

    private static final String TAG = "PMSHookHandler";

    public PMSHookHandler(Object baseService) {
        super(baseService);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Toast.makeText(MyApplication.getContext(),
                "you have hooked PackageManagerService ! \n hooked method: " + method.getName(),
                Toast.LENGTH_SHORT).show();

        Log.d(TAG, "you have hooked PackageManagerService !");

        Log.d(TAG, "hooked method: " + method.getName() + " ,called with args: " + Arrays.toString(args));

        return method.invoke(baseService, args);
    }
}
