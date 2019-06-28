package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.tools.FileDownLoadUtils;
import com.powerrich.office.oa.tools.ImageLoad;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.yt.simpleframe.http.bean.entity.CertificateInfo;
import com.yt.simpleframe.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11
 * 版权：
 */

public class CertificateInfoActivity extends YTBaseActivity {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_certificate_num)
    TextView mTvCertificateNum;
    @BindView(R.id.tv_certificate_name)
    TextView mTvCertificateName;

//    @BindView(R.id.tv_certificate_realname)
//    TextView mTvCertificateRealName;

    @BindView(R.id.tv_card_time)
    TextView mTvCardTime;
    @BindView(R.id.tv_office)
    TextView mTvOffice;
    @BindView(R.id.rlt_next)
    RelativeLayout mRltNext;
    @BindView(R.id.webview)
    WebView mWebView;

    @BindView(R.id.tv_webview)
    TextView mTvWebView;

    @BindView(R.id.iv_file)
    ImageView mIvFile;
    @BindView(R.id.tv_file)
    TextView mTvFile;


    private CertificateInfo info;


    String filePath;
    String hdfsfilename;
    String fileName;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_certificate_info);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("证照详情");
        showBack();
        info = (CertificateInfo) getIntent().getExtras().get("data");

        String userName ="";
        String userType = LoginUtils.getInstance().getUserInfo().getDATA().getUSERDUTY();
        //不是企业账号 显示姓名
        if(!userType.equals("1")){
            userName= LoginUtils.getInstance().getUserInfo().getDATA().getREALNAME();
        }

        mTvName.setText(userName + " (已认证)");
        String id = info.getCARD_NO();


        if (id.length() > 0) {
            if (id.length() < 4) {
                id = "****";
            } else if (id.length() >= 4 && id.length() <= 8) {
                id = id.substring(4) + "****";
            } else if (id.length() > 8) {
                id = id.replaceAll("(?<=\\d{4})\\d(?=\\d{4})", "*");
            }
        }
        mTvCertificateNum.setText(id);
        mTvCardTime.setText(info.getENDTIME());
        mTvOffice.setText("鹰潭公安");
        mTvCertificateName.setText(info.getCOMP_FILE_NAME());
//        mTvCertificateRealName.setText(info.getCARD_NAME());

        filePath = info.getCARD_FILE();
        hdfsfilename = info.getHDFSFILENAME();
        fileName = info.getFILENAME();
        final String url = "http://218.87.176.156:80/platform/DownFileServlet?type=1&DOWNPATH=" + filePath +
                "&HDFSFILENAME=" + hdfsfilename + "&FILENAME=" + fileName;

        doPermissionRW("读写", new PermissionCallBack() {
            @Override
            public void accept() {
                String fileType = StringUtil.getLastType(fileName);
                i("fileType:"+fileType+"--fileName:"+fileName+"-url:"+url);
                if ("pdf".equalsIgnoreCase(fileType)) {
                    mIvFile.setVisibility(View.GONE);
                    mTvFile.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                    FileDownLoadUtils.downloadFile(fileName, url, new FileDownLoadUtils.DownLoadCallBack() {
                        @Override
                        public void success(String sdPath) {
                            showPdfView(sdPath);
                        }

                        @Override
                        public void fail() {
                        }
                    });
                } else if (StringUtil.isImg(fileType)) {
                    mIvFile.setVisibility(View.VISIBLE);
                    mTvFile.setVisibility(View.GONE);
                    mWebView.setVisibility(View.GONE);
                    i( "accept-url: "+url);
                    ImageLoad.setUrl(CertificateInfoActivity.this, url, mIvFile);
                    //第三方文件类型
                } else if (StringUtil.isThreeFile(fileType)) {
                    mIvFile.setVisibility(View.GONE);
                    mTvFile.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                    FileDownLoadUtils.downloadFile(fileName, url, new FileDownLoadUtils.DownLoadCallBack() {
                        @Override
                        public void success(String sdPath) {
                            mTvFile.setText(fileName);
                            mTvFile.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                            mTvFile.getPaint().setAntiAlias(true);
                            mTvFile.setTextColor(getResources().getColor(R.color.blue));
                        }

                        @Override
                        public void fail() {
                        }
                    });

                } else {
                    ToastUtils.showMessage(CertificateInfoActivity.this, "不支持类型 " + fileName);
                    mIvFile.setEnabled(false);
                    mTvFile.setEnabled(false);
                    mWebView.setEnabled(false);
                }
            }
        });
    }


    public void showPdfView(String sdPath) {

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.loadUrl("file:///android_asset/index.html?" + sdPath);

//        mPdfvCar1.fromFile(new File(sdPath))
//                .defaultPage(0)
//                .onPageChange(CertificateInfoActivity.this)
//                .enableSwipe(false)
////                                .enableAnnotationRendering(true)
//                .onLoad(CertificateInfoActivity.this)
////                                .scrollHandle(new DefaultScrollHandle(MainActivity.this))
//                .spacing(10) // in dp
//                .onPageError(CertificateInfoActivity.this)
//                .load();
    }



    @OnClick({R.id.webview, R.id.iv_file, R.id.tv_file,R.id.tv_webview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
           case R.id.tv_webview:
            case R.id.iv_file:
                Intent intent = new Intent(this, CertificateImageActivity.class);
                intent.putExtra("CARD_FILE", filePath);
                intent.putExtra("HDFSFILENAME", hdfsfilename);
                intent.putExtra("FILENAME", fileName);
                startActivity(intent);
                break;
            case R.id.tv_file:
                try {
                    intent = StringUtil.getWordFileIntent(CertificateInfoActivity.this, Environment.getExternalStorageDirectory
                            () +

                            "/download/" + fileName);
                    startActivity(intent);
                } catch (Exception e) {
                    //去应用市场打开wps
                    openApplicationMarket("cn.wps.moffice_eng");
                }
                break;
        }
    }

    private void openApplicationMarket(String packageName) {
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            startActivity(localIntent);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "打开应用商店失败", Toast.LENGTH_SHORT).show();
        }
    }

}
