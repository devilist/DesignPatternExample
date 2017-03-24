package app.zengpu.com.utilskit.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;


import java.io.IOException;
import java.util.List;

/**
 * 定位工具类
 * Created by guodx on 16/5/15.
 */
public class LocationUtil {
    private static final String TAG = "Location";

    /**
     * 获取某个经纬度所在城市
     *
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public static String getLocationCity(Context context, double latitude, double longitude) {
        Geocoder ge = new Geocoder(context);
        try {
            List<Address> addList = ge.getFromLocation(latitude, longitude, 1);
            if (null != addList && addList.size() > 0) {
                Address ad = addList.get(0);
                return ad.getLocality();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;
        }
        return null;
    }


    /**
     * 获得当前省份，城市
     *
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public static String[] getLocationProviceAndCity(Context context, double latitude, double longitude) {
        Geocoder ge = new Geocoder(context);
        try {
            List<Address> addList = ge.getFromLocation(latitude, longitude, 1);
            if (null != addList && addList.size() > 0) {
                Address ad = addList.get(0);
                String province = ad.getAdminArea();
                String city = ad.getLocality();
                if (province.contains("新疆")) {
                    province = "新疆";
                } else if (province.contains("西藏")) {
                    province = "西藏";
                } else if (province.contains("香港")) {
                    province = "香港";
                } else if (province.contains("澳门")) {
                    province = "澳门";
                } else if (province.contains("内蒙古")) {
                    province = "内蒙古";
                } else if (province.contains("宁夏")) {
                    province = "宁夏";
                } else if (province.contains("广西")) {
                    province = "广西";
                } else {
                    province = province.replace("省", "").replace("市", "");
                }
                city = city.replace("市", "");

                return new String[]{province, city};
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;
        }
        return null;
    }

    /**
     * 获取当前所在城市
     *
     * @param context
     * @return
     */
    public static String getLocationCity(Context context) {
        Double[] location = getCurrentLocation(context);
        if (location[0] == 0 && location[1] == 0) {
            return null;
        } else {
            return getLocationCity(context, location[0], location[1]);
        }
    }

    /**
     * 获取当前所在省份，城市
     *
     * @param context
     * @return
     */
    public static String[] getLocationProviceAndCity(Context context) {
        Double[] location = getCurrentLocation(context);
        if (location[0] == 0 && location[1] == 0) {
            return null;
        } else {
            return getLocationProviceAndCity(context, location[0], location[1]);
        }
    }

    /**
     * 获取当前所在位置 经纬度
     *
     * @param context
     * @return
     */
    public static Double[] getCurrentLocation(Context context) {
        //获取地理位置管理器
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return new Double[]{0.0, 0.0};
        }
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        // 位置对象
        Location location = null;
        String locationProvider;

        if (providers.contains(LocationManager.GPS_PROVIDER) && isGPSEnabled(context)) {
            // 如果是GPS
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (null == location && providers.contains(LocationManager.NETWORK_PROVIDER)) {
            // 网络判断
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (null == location) {
            LogUtil.e(TAG, "没有可用的位置提供器");
            return new Double[]{0.0, 0.0};
        } else {
            //不为空,显示地理位置经纬度
            return new Double[]{location.getLatitude(), location.getLongitude()};
        }
    }

    /**
     * GPS是否开启
     *
     * @param context
     * @return
     */
    private static boolean isGPSEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
