package com.devilist.app.designpatternexample.aidl_demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.devilist.app.designpatternexample.IBookMangerInterface;
import com.devilist.app.designpatternexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 跨进程通信
 * 客户端
 * Created by zengpu on 2017/3/30.
 */

public class AIDLActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = AIDLActivity.class.getSimpleName();

    private List<Book> mBooks;
    private IBookMangerInterface mBookManager;
    private boolean hasBoundService = false;

    private Button addBook, getBook;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        initView();

    }

    private void initView() {
        addBook = (Button) findViewById(R.id.btn_add_book);
        getBook = (Button) findViewById(R.id.btn_get_book);
        addBook.setOnClickListener(this);
        getBook.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!hasBoundService) {
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (hasBoundService)
            unbindService(mServiceConnection);
        hasBoundService = false;
    }

    @Override
    public void onClick(View v) {

        if (!hasBoundService) {
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            attemptToBindService();
            return;
        }

        switch (v.getId()) {
            case R.id.btn_add_book:
                this.addBook();
                break;
            case R.id.btn_get_book:
                this.getBook();
                break;

        }
    }

    private void addBook() {
        if (mBookManager == null)
            return;
        Book book = new Book();
        book.setName("AIDL");
        book.setPrice(30);
        Log.e(TAG, "after add book: " + book.toString());
        try {
            mBookManager.addBook(book);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private List<Book> getBook() {
        List<Book> get_books = new ArrayList<>();
        if (mBookManager != null) {
            try {
                get_books = mBookManager.getBooks();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        Log.e(TAG, "get_books: " + get_books.toString());
        return get_books;
    }

    private void attemptToBindService() {
        Intent intent = new Intent(this, AIDLService.class);
//        Intent intent = new Intent();
        intent.setAction("com.devilist.app.designpatternexample.aidl_demo.aidlservice");
        intent.setPackage("com.devilist.app.designpatternexample.aidl_demo");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "service connected");
            mBookManager = IBookMangerInterface.Stub.asInterface(service);
            hasBoundService = true;

            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBooks();
                    Log.e(getLocalClassName(), mBooks.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "service disconnected");
            hasBoundService = false;
        }
    };

}
