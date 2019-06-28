package com.powerrich.office.oa.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aeye.android.uitls.BitmapUtils;
import com.alibaba.fastjson.JSONObject;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.MyDialog;

/**
 * @author Android05
 *         活体检测结果界面
 */
public class ResultAliveActivity extends BaseActivity implements OnClickListener {
    private ImageView imgAlive;
    private Button btnCompare;
    private TextView msgAlive;

    private String strImage;
    private String sysno;
    private String batchId;
    private final int BACK_END_RECOGNIZE = 111;
    private final int COMPLETE_IDENTIFY = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        parseDisplayData(getIntent());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_result_alive;
    }

    private void initView() {
        initTitleBar(getString(R.string.detection_result), this, null);
        btnCompare = (Button) findViewById(R.id.btnCompare);
        btnCompare.setOnClickListener(this);
        imgAlive = (ImageView) findViewById(R.id.imgAlive);

        msgAlive = (TextView) findViewById(R.id.msgAlive);
    }

    private void parseDisplayData(Intent intent) {
        btnCompare.setEnabled(false);
        if (intent.getIntExtra("VALUE", -1) == 0) {
            sysno = intent.getStringExtra("SYSNO");
            batchId = intent.getStringExtra("BATCHID");
            displayImage(intent.getStringExtra("DATA"));
            btnCompare.setEnabled(true);
        }
        String alive = intent.getStringExtra("ERROR_MSG");
        if (alive != null) {
            msgAlive.setText(alive);
        }
    }

    private void displayImage(String str) {
        try {
            JSONObject json = JSONObject.parseObject(str);
            JSONObject jsonImage = json.getJSONObject("images");
            strImage = jsonImage.getString("0");
            Bitmap bitmap = BitmapUtils.AEYE_Base64Decode(strImage);
            imgAlive.setImageBitmap(bitmap);
            Log.d("ZDX", "strImage:" + strImage);
        } catch (Exception e) {
            msgAlive.setText(R.string.result_no_face_detected);
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.btnCompare:
                //比对
                backendRecognize();
                break;
            default:
                break;
        }
    }

    /**
     * 比对
     */
    private void backendRecognize() {
        ApiRequest request = OAInterface.backendRecognize(sysno, batchId, strImage);
        invoke.invokeWidthDialog(request, callBack, BACK_END_RECOGNIZE);
    }

    /**
     * 完成认证
     */
    private void completeIdentify(int result) {
        ApiRequest request = OAInterface.completeIdentify(sysno, batchId, result);
        invoke.invokeWidthDialog(request, callBack, COMPLETE_IDENTIFY);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            ResultItem data = ((ResultItem) item.get("data"));
            if ("0".equals(data.getString("respCode"))) {
                if (what == BACK_END_RECOGNIZE) {
                    //获取比对结果
                    int result = ((ResultItem) data.get("respData")).getInt("result");
                    completeIdentify(result);
                } else if (what == COMPLETE_IDENTIFY) {
                    String identifyMsg = ((ResultItem) data.get("respData")).getString("identifyMsg");
                    showResultDialog(identifyMsg);
                }
            } else {
                ToastUtils.showMessage(ResultAliveActivity.this, data.getString("respMsg"));
            }
        }
    };

    /**
     * 认证结果对话框
     *
     * @param identifyMsg
     */
    private void showResultDialog(String identifyMsg) {
        MyDialog builder = new MyDialog(this).builder();
        builder.setTitle("提示");
        builder.setMessage(identifyMsg);
        builder.setCancelable(false);
        builder.setPositiveButton(this.getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                //跳转到社保认证界面
                intent.setClass(ResultAliveActivity.this, InsuranceReceiveActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }
}
