package app.zengpu.com.utilskit.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取设备信息  品牌，OS版本，分辨率，唯一标识
 * Created by zengpu on 2016/4/28.
 */
public class DeviceInfoUtil {



    /**
     * 获得手机品牌
     * @return
     */
    public static String getPhoneBrand() {
        return Build.MODEL;
    }
    /**
     * 获得os版本
     * @return
     */
    public static String getOSVersion() {
        return "android:" + Build.VERSION.RELEASE;
    }

    /**
     * 获得设备分辨率
     * @param context
     * @return
     */
    public static String getDeviceDisplay(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        android.view.Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        return height + "*" + width;

    }

    /**
     * 获取唯一标识 （综合五个设备信息后合成）
     * @param context
     * @return
     */
    public static String getUniqueDevId(Context context) {
        String mLongID = getIMEI(context) +
                            getUniqueIdByDeviceHardwareInfo() +
                            getAndroidId(context) +
                            getWlanMacAddress(context) +
                            getBTMacAddress();

        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(mLongID.getBytes(),0,mLongID.length());
        // get md5 bytes
        byte p_md5Data[] = m.digest();
        // create a hex string
        String
            mUniqueID = new String();
        for (int i=0;i<p_md5Data.length;i++) {
            int b =  (0xFF & p_md5Data[i]);
        // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF) mUniqueID+="0";
        // add number to string
            mUniqueID+=Integer.toHexString(b);
        }   // hex string to uppercase
        String uniqueDevId =
            mUniqueID.toUpperCase();

        return uniqueDevId;
    }

    /**
     * 获取唯一标识 （IMEI）
     * @param context
     * @return
     */
    public static String getUniqueDevIdByIMEI(Context context) {
        String mLongID = getIMEI(context);
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(mLongID.getBytes(),0,mLongID.length());
        // get md5 bytes
        byte p_md5Data[] = m.digest();
        // create a hex string
        String
                mUniqueID = new String();
        for (int i=0;i<p_md5Data.length;i++) {
            int b =  (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF) mUniqueID+="0";
            // add number to string
            mUniqueID+=Integer.toHexString(b);
        }   // hex string to uppercase
        String uniqueDevId =
                mUniqueID.toUpperCase();

        return uniqueDevId;
    }

    /**
     * 获取手机DeviceId,需要权限
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String mImei = telephonyMgr.getDeviceId();
        return mImei;
    }

    /**
     * 一些特殊的情况，一些如平板电脑的设置没有通话功能，
     * 或者你不愿加入READ_PHONE_STATE许可,
     * 而你仍然想获得唯一序列号之类的东西。
     * 这时你可以通过取出ROM版本、制造商、CPU型号、
     * 以及其他硬件信息来实现这一点
     * @return
     */
    private static String getUniqueIdByDeviceHardwareInfo() {
        String mDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length()%10 +
                Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 +
                Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 +
                Build.HOST.length()%10 +
                Build.ID.length()%10 +
                Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 +
                Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 +
                Build.TYPE.length()%10 +
                Build.USER.length()%10 ; //13 digits
        return  mDevIDShort;
    }

    /**
     * 在设备首次启动时，系统会随机生成一个64位的数字，
     * 并把这个数字以16进制字符串的形式保存下来，
     * 这个16进制的字符串就是ANDROID_ID，当设备被wipe后该值会被重置。
     * @param context
     * @return
     */
    private static String getAndroidId(Context context) {
        String mAndroidID = Settings.Secure.getString(context.getContentResolver(),
                                                                Settings.Secure.ANDROID_ID);
        return mAndroidID;

    }

    /**
     * 获取wifi物理地址 需要权限
     * @param context
     * @return
     */
    private static String getWlanMacAddress(Context context) {
        WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        String mWlanMac = wm.getConnectionInfo().getMacAddress();
        return mWlanMac;
    }

    /**
     * 获取蓝牙物理地址 需要权限
     * @return
     */
    private static String getBTMacAddress() {
        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String mBTMac = m_BluetoothAdapter.getAddress();

        return mBTMac;
    }

}
