package app.zengpu.com.utilskit.utils;

import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Created by zengpu on 16/8/14.
 */
public class StringUtil {

    /**
     * 是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        if (null == str || str.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * 是否为空
     *
     * @param objs
     * @return
     */
    public static boolean isEmpty(@Nullable Object[] objs) {
        if (null == objs || objs.length == 0)
            return true;
        else
            return false;
    }

    /**
     * 是否为空
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(@Nullable String text) {
        if (null == text || text.length() == 0)
            return true;
        else
            return false;
    }

    /**
     * 是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(@Nullable List list) {
        if (null == list || list.size() == 0)
            return true;
        else
            return false;
    }

    /**
     * 是否为空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(@Nullable Map map) {
        if (null == map || map.size() == 0)
            return true;
        else
            return false;
    }

    /**
     * 获取集合数目
     *
     * @param list
     * @return
     */
    public static int getCount(List list) {
        return null == list ? 0 : list.size();
    }

    /**
     * 获取数组数目
     *
     * @param list
     * @return
     */
    public static int getCount(Object[] list) {
        return null == list ? 0 : list.length;
    }

}

