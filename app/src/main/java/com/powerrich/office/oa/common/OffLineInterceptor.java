package com.powerrich.office.oa.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.tools.DialogUtils;

/**
 * Created by Administrator on 2018/1/17.
 * 被挤下线请求拦截
 */

public class OffLineInterceptor implements Interceptor {

    private String errorCode;

    public OffLineInterceptor(String code) {
        this.errorCode = code;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public void doInterceptWork(final Context context) {
        if (null != context) {
            DialogUtils.createConfirmDialog(context, context.getString(R.string.offline_title), context.getString(R.string.offline_message),
                context.getString(R.string.system_dialog_confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("requestCode", 000);//不需要接收从LoginActivity传递过来的参数，可设置一个非  -1  的参数
                            context.startActivity(intent);
                        }
                    }).show();
        }
    }
}
