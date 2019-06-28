package com.yt.simpleframe.view.loading;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.yt.simpleframe.R;


/**
 * Created by wfpb on 15/6/25.
 * <p>
 * Loading对话框管理
 */
public class LoadingHandler {


    private static LoadingDialog _loadingDialog;
    private Context _context;
    private static LoadingHandler loading;


    private LoadingHandler(Context context) {
        _context = context;
    }

    public static LoadingHandler getInstance(Context context) {
        if (loading == null)
            loading = new LoadingHandler(context);
        return loading;
    }


    public void showLoading() {
        showLoading(_context.getString(R.string.loading), true);
    }

    public void showLoading(boolean isCancle) {
        showLoading(_context.getString(R.string.loading), isCancle);
    }


    public void showLoading(final String message, final boolean isCancle) {

        if (_loadingDialog == null) {
            _loadingDialog = new LoadingDialog(_context, message);
        }
        _loadingDialog.setMessage(message);
        _loadingDialog.setCanceledOnTouchOutside(isCancle);
        FragmentActivity activity = (FragmentActivity) _context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.getFragmentManager().isDestroyed()) return;
        } else {
            if (activity.isFinishing()) return;
        }
        _loadingDialog.show();
        _loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                _loadingDialog = null;
                Log.d("Loading", "showLoading 关闭-------------------------------------");

            }
        });
    }



    public void onDestory() {
        if (_loadingDialog != null)
            _loadingDialog = null;
    }

    public void updateLoading(String message) {
        if (_loadingDialog != null)
            _loadingDialog.setMessage(message);
    }

    public void hideLoading() {
        Log.d("Loading", "hideLoading 关闭-------------------------------------");
        if (_loadingDialog != null) {
            if (_loadingDialog.isShowing())
                _loadingDialog.cancel();
        }


    }
}
