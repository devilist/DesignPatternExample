package app.zengpu.com.utilskit.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 获取网络连接工具类
 * Created by zengpu on 2016/4/28.
 */
public class IntenetUtil {
    //没有网络连接
    public static final String NETWORN_NONE = "network disconnect";
    //wifi连接
    public static final String NETWORN_WIFI = "WIFI";
    //手机网络数据连接类型
    public static final String NETWORN_2G = "2G";
    public static final String NETWORN_3G = "3G";
    public static final String NETWORN_4G = "4G";
    public static final String NETWORN_MOBILE = "unknown";

    //没有网络连接
    public static final int NETWORN_NONE_0 = 0;
    //wifi连接
    public static final int NETWORN_WIFI_1 = 1;
    //手机网络数据连接类型
    public static final int NETWORN_2G_2 = 2;
    public static final int NETWORN_3G_3 = 3;
    public static final int NETWORN_4G_4 = 4;
    public static final int NETWORN_MOBILE_5 = 5;

    /**
     * 获取当前网络连接类型
     * @param context
     * @return
     */
    public static int getNetworkStateInt(Context context) {
        int net_type_int = 0;
        String net_type = getNetworkState(context);
        switch (net_type) {
            case NETWORN_NONE:
                net_type_int = NETWORN_NONE_0;
            break;
            case NETWORN_WIFI:
                net_type_int = NETWORN_WIFI_1;
            break;
            case NETWORN_2G:
                net_type_int = NETWORN_2G_2;
            break;
            case NETWORN_3G:
                net_type_int = NETWORN_3G_3;
            break;
            case NETWORN_4G:
                net_type_int = NETWORN_4G_4;
            break;
            case NETWORN_MOBILE:
                net_type_int = NETWORN_MOBILE_5;
            break;
        }
       return net_type_int;
    }

    /**
     * 获取当前网络连接类型
     * @param context
     * @return
     */
    public static String getNetworkState(Context context) {
        //获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context.
                                                    getSystemService(Context.CONNECTIVITY_SERVICE);

        //如果当前没有网络
        if (null == connManager)
            return NETWORN_NONE;
        //获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORN_NONE;
        }
        // 判断连接的是不是wifi
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORN_WIFI;
                }
        }
        // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        //如果是2g类型
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORN_2G;

                        //如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORN_3G;

                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORN_4G;
                        default:
                            //中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA")
                                    || strSubTypeName.equalsIgnoreCase("WCDMA")
                                    || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return NETWORN_3G;
                            } else {
                                return NETWORN_MOBILE;
                            }
                    }
                }
        }
        return NETWORN_NONE;
    }


}
