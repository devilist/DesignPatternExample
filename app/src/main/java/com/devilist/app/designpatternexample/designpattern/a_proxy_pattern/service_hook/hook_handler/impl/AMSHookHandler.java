package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.impl;

import android.util.Log;
import android.widget.Toast;

import com.devilist.app.designpatternexample.MyApplication;
import com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.BaseServiceHookHandler;

import java.lang.reflect.Method;

/**
 * AMS
 * Created by zengpu on 2017/4/1.
 */

public class AMSHookHandler extends BaseServiceHookHandler {

    private static final String TAG = "AMSHookHandler";

    public AMSHookHandler(Object baseService) {
        super(baseService);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Log.d(TAG, "you have hooked ActivityManagerService !");
        Log.d(TAG, "hooked method: " + method.getName());

        if ("startActivity".equals(method.getName())) {

            Toast.makeText(MyApplication.getContext(),
                    "you have hooked ActivityManagerService ! \n hooked method: " + method.getName(),
                    Toast.LENGTH_SHORT).show();
        }
        return method.invoke(baseService, args);
    }
}
