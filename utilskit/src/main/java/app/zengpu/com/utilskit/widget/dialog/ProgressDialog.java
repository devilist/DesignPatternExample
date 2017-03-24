package app.zengpu.com.utilskit.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import app.zengpu.com.utilskit.R;


/**
 * 一个半透明窗口,包含一个Progressbar 和 Message部分. 其中Message部分可选. 可单独使用,也可以使用
 * {@link ProgressMaker} 进行相关窗口显示.
 *
 * @author Qijun
 */
public class ProgressDialog extends Dialog {
    private Context mContext;

    private String mMessage;

    private int mLayoutId;

    private TextView message;

    public ProgressDialog(Context context, int style, int layout) {
        super(context, style);
        mContext = context;
        LayoutParams Params = getWindow().getAttributes();
        Params.width = LayoutParams.FILL_PARENT;
        Params.height = LayoutParams.FILL_PARENT;
        getWindow().setAttributes(Params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        mLayoutId = layout;
    }

    public ProgressDialog(Context context, int layout, String msg) {
        this(context, 0, layout);
        setMessage(msg);
    }

    public ProgressDialog(Context context, String msg) {
        this(context, 0, R.layout.dialog_progress);
        setMessage(msg);
    }

    public ProgressDialog(Context context) {
        this(context, 0, R.layout.dialog_progress);
    }

    public void setMessage(String msg) {
        mMessage = msg;
    }

    public void updateLoadingMessage(String msg) {
        mMessage = msg;
        showMessage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutId);
        message = (TextView) findViewById(R.id.easy_progress_dialog_message);
        showMessage();
    }

    private void showMessage() {
        if (message != null && !TextUtils.isEmpty(mMessage)) {
            message.setVisibility(View.VISIBLE);
            message.setText(mMessage);
        }
    }
}