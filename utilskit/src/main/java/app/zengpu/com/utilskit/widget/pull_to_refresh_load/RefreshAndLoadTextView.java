package app.zengpu.com.utilskit.widget.pull_to_refresh_load;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import app.zengpu.com.utilskit.widget.pull_to_refresh_load.core.RefreshAndLoadViewBase;


/**
 * Created by zengpu on 2016/7/21.
 */
public class RefreshAndLoadTextView extends RefreshAndLoadViewBase<TextView> {


    public RefreshAndLoadTextView(Context context) {
        super(context);
    }

    public RefreshAndLoadTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshAndLoadTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void setContentViewScrollListener() {
        mContentView = (TextView) getChildAt(2);
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
}
