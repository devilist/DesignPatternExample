package app.zengpu.com.utilskit.widget.pull_to_refresh_load;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;

import app.zengpu.com.utilskit.widget.pull_to_refresh_load.core.RefreshAndLoadViewBase;


/**
 * Created by zengpu on 16/7/21.
 */
public class RefreshAndLoadCustomView extends RefreshAndLoadViewBase<ViewGroup> {


    public RefreshAndLoadCustomView(Context context) {
        super(context);
    }

    public RefreshAndLoadCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshAndLoadCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void setContentViewScrollListener() {
        mContentView = (ViewGroup) getChildAt(2);
        mContentView.setClickable(true);

    }

    @Override
    protected boolean isTop() {
        return true;
    }

    @Override
    protected boolean isBottom() {
        return true;
    }

    @Override
    protected boolean iscontentViewCompletelyShow() {
        return false;
    }

    /**
     * 配置头和尾
     */
    @Override
    public Builder initHeaderAndFooter() {

        return new Builder()
                .headerBgColor(Color.TRANSPARENT)                   // header背景色
                .headerTipTextColor(Color.parseColor("#808080"))    // 刷新提示文字颜色
                .headerTipTextSize(13)                              // 刷新提示文字大小
                .footerBgColor(Color.TRANSPARENT)                   // footer背景色
                .footerTipTextColor(Color.parseColor("#808080"))    // 加载提示文字颜色
                .footerTipTextSize(13)                              // 加载提示文字大小
                .refreshFailureTip("刷新失败，点击重新获取")          // 刷新失败提示
                .refreshNoDataTip("数据已最新")                     // 刷新没有更多提示
                .loadFailureTip("加载失败，点击重新获取")            // 上拉加载失败提示
                .loadNoDataTip("没有更多了");                      // 上拉加载没有更多提示
    }
}
