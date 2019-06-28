package com.powerrich.office.oa.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.powerrich.office.oa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class VerifyWorkDialog extends Dialog {

    @BindView(R.id.tv_1)
    TextView mTv1;
    @BindView(R.id.tv_normal)
    TextView mTvNormal;
    @BindView(R.id.tv_eid)
    TextView mTvEid;
    @BindView(R.id.tv_cencel)
    TextView mTvCencel;
    private Context context;

    private ClickListenerInterface clickListenerInterface;

    @OnClick({R.id.tv_normal, R.id.tv_eid, R.id.tv_cencel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_normal:
                clickListenerInterface.verifyNomal();
                break;
            case R.id.tv_eid:
                clickListenerInterface.verifyEid();
                break;
            case R.id.tv_cencel:
                clickListenerInterface.cancel();
                break;
        }
    }

    public interface ClickListenerInterface {
        void cancel();
        void verifyNomal();
        void verifyEid();
    }

    public VerifyWorkDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_verify_work_layout, null);
        ButterKnife.bind(this,view);
        setContentView(view);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }


    public void setTitle(String titleStr){
        if(mTv1!=null&&!TextUtils.isEmpty(titleStr)){
            mTv1.setText(titleStr);
        }

    }

    public void setClickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }


}
