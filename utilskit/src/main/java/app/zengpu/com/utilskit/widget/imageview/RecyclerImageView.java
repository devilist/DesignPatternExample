package app.zengpu.com.utilskit.widget.imageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 加快回收的ImageView
 * Created by guodx on 16/5/15.
 */
public class RecyclerImageView extends ImageView {
    public RecyclerImageView(Context context) {
        super(context);
    }

    public RecyclerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @see ImageView#setImageDrawable(Drawable)
     */
    @Override
    public void setImageDrawable(Drawable drawable) {
        // 得到上次显示的Drawable
        final Drawable previousDrawable = getDrawable();

        // 设置新的Drawable
        super.setImageDrawable(drawable);

        // 通知新的Drawable已经显示
        notifyDrawable(drawable, true);

        // 通知旧的Drawable不再显示
        notifyDrawable(previousDrawable, false);
    }

    /**
     * 通知Drawable的显示状态
     * <p/>
     * Notifies the drawable that it's displayed state has changed.
     *
     * @param drawable
     * @param isDisplayed
     */
    private static void notifyDrawable(Drawable drawable, final boolean isDisplayed) {
        if (drawable instanceof LayerDrawable) {
            // The drawable is a LayerDrawable, so recurse on each layer
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            for (int i = 0, z = layerDrawable.getNumberOfLayers(); i < z; i++) {
                notifyDrawable(layerDrawable.getDrawable(i), isDisplayed);
            }
        }
    }
}
