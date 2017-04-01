package com.devilist.app.designpatternexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.devilist.app.designpatternexample.aidl_demo.AIDLActivity;
import com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.ServiceHookActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MainRecyclerViewAdapter mAdapter;
    private List<String[]> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainRecyclerViewAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MainRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Class<?> activityClazz = Class.forName(list.get(position)[1]);
                    Intent intent = new Intent(MainActivity.this, activityClazz);
                    startActivity(intent);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initData() {
        list.add(new String[]{"AIDL", AIDLActivity.class.getName()});
        list.add(new String[]{"Service Hook", ServiceHookActivity.class.getName()});
    }
}

