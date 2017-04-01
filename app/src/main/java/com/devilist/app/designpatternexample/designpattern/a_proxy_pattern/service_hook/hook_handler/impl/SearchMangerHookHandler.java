package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.impl;

import android.util.Log;
import android.widget.Toast;

import com.devilist.app.designpatternexample.MyApplication;
import com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.BaseServiceHookHandler;

import java.lang.reflect.Method;

/**
 * SearchMangerService
 * Created by zengpu on 2017/4/1.
 */

public class SearchMangerHookHandler extends BaseServiceHookHandler {

    private static final String TAG = "SearchMangerHookHandler";

    public SearchMangerHookHandler(Object baseService) {
        super(baseService);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Toast.makeText(MyApplication.getContext(),
                "you have hooked SearchMangerService ! \n hooked method: " + method.getName(),
                Toast.LENGTH_SHORT).show();
        Log.d(TAG, "you have hooked SearchMangerService !");
        Log.d(TAG, "hooked method: " + method.getName());
        return method.invoke(baseService,args);
    }
}
