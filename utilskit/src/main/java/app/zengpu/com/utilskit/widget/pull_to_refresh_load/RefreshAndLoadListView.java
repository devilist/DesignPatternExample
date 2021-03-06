package app.zengpu.com.utilskit.widget.pull_to_refresh_load;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import app.zengpu.com.utilskit.widget.pull_to_refresh_load.core.RefreshAndLoadViewBase;


/**
 * 具有下拉刷新和上拉加载功能的ListView;
 * Created by zengpu on 2016/4/23.
 */
public class RefreshAndLoadListView extends RefreshAndLoadViewBase<ListView> {
    public RefreshAndLoadListView(Context context) {
        super(context);
    }

    public RefreshAndLoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshAndLoadListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void setContentViewScrollListener() {
        mContentView = (ListView) getChildAt(2);
        mContentView.setOnScrollListener(this);

    }

    @Override
    protected boolean isTop() {
        return mContentView.getFirstVisiblePosition() == 0
                && getScrollY() <= mHeaderView.getMeasuredHeight();
    }

    @Override
    protected boolean isBottom() {
        return mContentView != null && mContentView.getAdapter() != null
                && mContentView.getLastVisiblePosition() ==
                mContentView.getAdapter().getCount() - 1;
    }

    @Override
    protected boolean iscontentViewCompletelyShow() {
        return mContentView != null && mContentView.getAdapter() != null
                && mContentView.getFirstVisiblePosition() == 0
                && mContentView.getLastVisiblePosition() == mContentView.getAdapter().getCount() - 1;
    }
}
