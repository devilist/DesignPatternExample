package app.zengpu.com.utilskit.widget.pull_to_refresh_load;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import app.zengpu.com.utilskit.widget.pull_to_refresh_load.core.RefreshAndLoadViewBase;


/**
 * 具有下拉刷新和上拉加载功能的RecyclerView;
 * Created by tao on 2016/4/22.
 */
public class RefreshAndLoadRecyclerView extends RefreshAndLoadViewBase<RecyclerView> {


    public RefreshAndLoadRecyclerView(Context context) {
        super(context);
    }

    public RefreshAndLoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshAndLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void setContentViewScrollListener() {
        mContentView = (RecyclerView) getChildAt(2);
        mContentView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    protected boolean isTop() {

        /*
         *  判断滑到顶部为true有以下几种情况：
         *  1.recyclerview为空，或adapter为空时
         *  2.ItemCount = 0 时
         *  3.lm.findFirstCompletelyVisibleItemPosition() == 0时
         *  4. 当前滚动位置getScrollY() 小于 mHeaderView高度时
         *  5.一些机型上，第三种情况会失效，可采用这种方法判断：获取到recyclerview的第一个child，然后判断child.getTop() == ly.topMargin
         *
         */

        GridLayoutManager lm = (GridLayoutManager) mContentView.getLayoutManager();

        if (mContentView.getAdapter() == null)
            return true;
        else if (mContentView.getAdapter().getItemCount() == 0)
            return true;
        else {
//            View child = mContentView.getChildAt(0);
//            ViewGroup.MarginLayoutParams ly = (MarginLayoutParams) child.getLayoutParams();

            return lm.findFirstCompletelyVisibleItemPosition() == 0
                    && getScrollY() <= mHeaderView.getMeasuredHeight();
//            return child.getTop() == ly.topMargin ||
//                    (lm.findFirstCompletelyVisibleItemPosition() == 0
//                    && getScrollY() <= mHeaderView.getMeasuredHeight());
        }
    }

    @Override
    protected boolean isBottom() {
        GridLayoutManager lm = (GridLayoutManager) mContentView.getLayoutManager();

        if (mContentView.getAdapter() == null)
            return false;
        else if (mContentView.getAdapter().getItemCount() == 0)
            return false;
        else
            return mContentView != null && mContentView.getAdapter() != null
                    && lm.findLastCompletelyVisibleItemPosition() ==
                    mContentView.getAdapter().getItemCount() - 1;
    }

    @Override
    protected boolean iscontentViewCompletelyShow() {
        GridLayoutManager lm = (GridLayoutManager) mContentView.getLayoutManager();
        return mContentView != null && mContentView.getAdapter() != null
                && lm.findFirstCompletelyVisibleItemPosition() == 0
                && lm.findLastCompletelyVisibleItemPosition() == mContentView.getAdapter().getItemCount() - 1;
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
