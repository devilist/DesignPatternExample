package com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.IInterface;

import com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.impl.AMSHookHandler;
import com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.BaseServiceHookHandler;
import com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.BinderProxyHookHandler;
import com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.impl.PMSHookHandler;
import com.devilist.app.designpatternexample.designpattern.a_proxy_pattern.service_hook.hook_handler.impl.SearchMangerHookHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 *
 *
 */
public final class HookHelper {

    public static final String _Stub = "$Stub";
    public static final String IInterface_IClipboard = "android.content.IClipboard";
    public static final String IInterface_ISearchManager = "android.app.ISearchManager";
    public static final String IInterface_IPackageManager = "android.content.pm.IPackageManager";

    public static void hookActivityManager() {
        try {
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");

            // 获取 gDefault 这个字段, 想办法替换它
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);

            Object gDefault = gDefaultField.get(null);

            // 4.x以上的gDefault是一个 android.util.Singleton对象; 我们取出这个单例里面的字段
            Class<?> singleton = Class.forName("android.util.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            // ActivityManagerNative 的gDefault对象里面原始的 IActivityManager对象
            Object rawIActivityManager = mInstanceField.get(gDefault);

            // 创建一个这个对象的代理对象, 然后替换这个字段, 让我们的代理对象帮忙干活
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iActivityManagerInterface},
                    new AMSHookHandler(rawIActivityManager));
            mInstanceField.set(gDefault, proxy);

        } catch (Exception e) {
            throw new RuntimeException("Hook Failed", e);
        }

    }

    public static void hookPackageManager(Context context) {
        try {
            // 获取全局的ActivityThread对象
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // 获取ActivityThread里面原始的 sPackageManager
            Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            Object sPackageManager = sPackageManagerField.get(currentActivityThread);

            // 准备好代理对象, 用来替换原始的对象
            Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(iPackageManagerInterface.getClassLoader(),
                    new Class<?>[]{iPackageManagerInterface},
                    new PMSHookHandler(sPackageManager));

            // 1. 替换掉ActivityThread里面的 sPackageManager 字段
//            sPackageManagerField.set(currentActivityThread, proxy);

            // 2. 替换 ApplicationPackageManager里面的 mPm对象
            PackageManager pm = context.getPackageManager();
            Field mPmField = pm.getClass().getDeclaredField("mPM");
            mPmField.setAccessible(true);
            mPmField.set(pm, proxy);
        } catch (Exception e) {
            throw new RuntimeException("hook failed", e);
        }
    }

    /**
     * hook 系统服务
     *
     * @param _SERVICE
     */
    public static void hookService(final String _SERVICE) {
        try {
            // 反射获取 ServiceManager
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getService = serviceManager.getDeclaredMethod("getService", String.class);
            // 获取IBinder对象
            IBinder rawBinder = (IBinder) getService.invoke(null, Context.SEARCH_SERVICE);
            // 反射类IXXInterface$Stub
            Class<?> stubClass = Class.forName(getIInterfaceName(_SERVICE) + _Stub);
            // 反射类IXXInterface
            Class<?> iinterface = Class.forName(getIInterfaceName(_SERVICE));
            // 反射类IXXInterface$Stub的asInterface方法
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            // 获取相应的服务
            IInterface rawService = (IInterface) asInterfaceMethod.invoke(null, rawBinder);

            // 创建服务代理
            IInterface serviceProxy = (IInterface) Proxy.newProxyInstance(rawService.getClass().getClassLoader(),
                    new Class[]{iinterface},
                    getServiceHookHandler(_SERVICE, rawService));

            // 创建IBinder代理
            IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),
                    new Class<?>[]{IBinder.class},
                    new BinderProxyHookHandler(rawBinder, serviceProxy));

            Field cacheField = null;
            cacheField = serviceManager.getDeclaredField("sCache");
            cacheField.setAccessible(true);
            Map<String, IBinder> cache = (Map) cacheField.get(null);
            cache.put(_SERVICE, hookedBinder);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getIInterfaceName(final String _SERVICE) {

        if ("search".equals(_SERVICE))
            return IInterface_ISearchManager;

        else if ("clipboard".equals(_SERVICE))
            return IInterface_IClipboard;

        return "";
    }

    private static InvocationHandler getServiceHookHandler(final String _SERVICE, IInterface rawService) {

        if ("search".equals(_SERVICE))
            return new SearchMangerHookHandler(rawService);

        else if ("clipboard".equals(_SERVICE))
            return new BaseServiceHookHandler(rawService);

        return new BaseServiceHookHandler(rawService);
    }
}
