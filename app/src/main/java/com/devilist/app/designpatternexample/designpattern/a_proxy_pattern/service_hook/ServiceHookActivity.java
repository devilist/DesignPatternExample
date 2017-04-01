package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.devilist.app.designpatternexample.R;

/**
 * Created by zengpu on 2017/4/1.
 */

public class ServiceHookActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_hook);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    // 这个方法比onCreate调用早; 在这里Hook比较好.
    @Override
    protected void attachBaseContext(Context newBase) {
        HookHelper.hookActivityManager();
        HookHelper.hookPackageManager(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                // 测试AMS HOOK (调用其相关方法)
                Uri uri = Uri.parse("http://wwww.baidu.com");
                Intent t = new Intent(Intent.ACTION_VIEW);
                t.setData(uri);
                startActivity(t);
                break;
            case R.id.btn1:
                // 测试PMS HOOK (调用其相关方法)
                getPackageManager().getSystemSharedLibraryNames();
                break;
            case R.id.btn2:
                // SearchManager
                HookHelper.hookService(Context.SEARCH_SERVICE);
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                searchManager.getSearchablesInGlobalSearch();
                break;
        }
    }
}
