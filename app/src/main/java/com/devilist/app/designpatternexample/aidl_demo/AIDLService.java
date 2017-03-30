package com.devilist.app.designpatternexample.aidl_demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.devilist.app.designpatternexample.IBookMangerInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 跨进程通信 服务端
 * Created by zenpu on 2017/3/30.
 */

public class AIDLService extends Service {

    public static final String TAG = AIDLService.class.getSimpleName();

    private List<Book> mBook = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book();
        book.setName("Android Interface Definition Language ");
        book.setPrice(100);
        mBook.add(book);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, String.format("on bind,intent = %s", intent.toString()));
        return mBookManager;
    }

    private IBookMangerInterface.Stub mBookManager = new IBookMangerInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                               float aFloat, double aDouble, String aString) throws RemoteException {
            // do nothing
            Log.e(TAG, "invoking basicTypes() method");
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                Log.e(TAG, "invoking getBooks() method , now the list is : " + mBook.toString());
                if (mBook != null) {
                    return mBook;
                }
                return new ArrayList<>();
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (this) {
                if (mBook == null) {
                    mBook = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null in In");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                book.setPrice(233);
                if (!mBook.contains(book)) {
                    mBook.add(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBook.toString());
            }
        }
    };
}
