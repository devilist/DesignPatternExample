package app.zengpu.com.utilskit.widget.dialog;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;

import app.zengpu.com.utilskit.utils.LogUtil;


public class ProgressMaker {
    private static ProgressDialog progressDialog;

    public static ProgressDialog showProgressDialog(Context context) {
        return showProgressDialog(context, null);
    }

    private static ProgressDialog showProgressDialog(Context context, int messageRes) {
        return showProgressDialog(context, context.getResources().getString(messageRes));
    }

    private static ProgressDialog showProgressDialog(Context context, String message) {
        return showProgressDialog(context, null, message, false, null);
    }


    private static ProgressDialog showProgressDialog(Context context, int messageRes, boolean cancelable) {
        return showProgressDialog(context, context.getResources().getString(messageRes), cancelable);
    }

    private static ProgressDialog showProgressDialog(Context context, String message, boolean cancelable) {
        return showProgressDialog(context, null, message, cancelable, null);
    }

    private static ProgressDialog showProgressDialog(Context context,
                                                    int titleRes, int messageRes, boolean canCancelable,
                                                    OnCancelListener listener) {
        return showProgressDialog(context, context.getResources().getString(titleRes),
                context.getResources().getString(messageRes), canCancelable, listener);
    }

    @Deprecated
    private static ProgressDialog showProgressDialog(Context context,
                                                    String title, String message, boolean canCancelable,
                                                    OnCancelListener listener) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context, message);
        } else if (progressDialog.getContext() != context) {
            // maybe existing dialog is running in a destroyed activity cotext
            // we should recreate one
            LogUtil.e("dialog", "there is a leaked window here,orign context: "
                    + progressDialog.getContext() + " now: " + context);
            dismissProgressDialog();
            progressDialog = new ProgressDialog(context, message);
        }

        progressDialog.setCancelable(canCancelable);
        progressDialog.setOnCancelListener(listener);

        progressDialog.show();

        return progressDialog;
    }

    public static void dismissProgressDialog() {
        if (null == progressDialog) {
            return;
        }
        if (progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
                progressDialog = null;
            } catch (Exception e) {
                // maybe we catch IllegalArgumentException here.
            }

        }

    }

    public static void setMessage(String message) {
        if (null != progressDialog && progressDialog.isShowing()
                && !TextUtils.isEmpty(message)) {
            progressDialog.setMessage(message);
        }
    }

    public static void updateLoadingMessage(String message) {
        if (null != progressDialog && progressDialog.isShowing()
                && !TextUtils.isEmpty(message)) {
            progressDialog.updateLoadingMessage(message);
        }
    }

    public static boolean isShowing() {
        return (progressDialog != null && progressDialog.isShowing());
    }
}
