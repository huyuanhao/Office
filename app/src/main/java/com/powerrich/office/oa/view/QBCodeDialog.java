package com.powerrich.office.oa.view;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.tools.AutoUtils;
import com.zxinglibrary.common.ZxingUtils;


public class QBCodeDialog extends Dialog {

    private Context context;

    private String mContent;

    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {

        void doConfirm();

        void doCancel();
    }

    public QBCodeDialog(Context context,String mContent) {
        super(context, R.style.dialog);
        this.mContent = mContent;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.qb_code_dialog_layout, null);
        AutoUtils.auto(view);
        setContentView(view);
        ImageView iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        ImageView iv_content = (ImageView) findViewById(R.id.iv_content);
        iv_cancel.setOnClickListener(new ClickListener());
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

        setCancelable(false);

        Bitmap success = ZxingUtils.createQRImage(mContent, 500, 500);

        iv_content.setImageBitmap(success);

    }

    public void setClickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.iv_cancel:
                    clickListenerInterface.doCancel();
                    dismiss();
                    break;
            }
        }
    }

}
