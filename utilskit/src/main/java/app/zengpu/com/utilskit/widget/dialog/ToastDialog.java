package app.zengpu.com.utilskit.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import app.zengpu.com.utilskit.R;
import app.zengpu.com.utilskit.utils.StringUtil;


/**
 * 提示框
 */
public class ToastDialog {

    public static void showToast(Context context, String text) {
        showToast(context, text, true);
    }

    public static void showToast(Context context, int resId) {
        showToast(context, resId, true);
    }

    public static void showToast(Context context, int resId, boolean isHasToolbar) {
        showToast(context, context.getResources().getString(resId), isHasToolbar);
    }

    public static void showToast(Context context, String text, boolean isHasToolbar) {
        showMyToast(context, text, isHasToolbar);
    }

    /**
     * 显示自定义的Toast
     *
     * @param content
     */
    private static void showMyToast(Context context, String content, boolean isHasToolbar) {
        if (StringUtil.isEmpty(content))
            return;
        View toastView = LayoutInflater.from(context).inflate(R.layout.dialog_toast, null);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv = (TextView) toastView.findViewById(R.id.tv_content);
//        tv.setLayoutParams(params);
        tv.setText(content);
        Toast toast = new Toast(context);

        toast.setGravity(Gravity.CENTER, 0, 0);
        tv.setAlpha(0.9f);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }
}
