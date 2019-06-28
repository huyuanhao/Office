package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.FileDownLoadUtils;
import com.powerrich.office.oa.tools.ImageLoad;
import com.yt.simpleframe.utils.StringUtil;
import com.yt.simpleframe.view.PinchImageView;

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

public class CertificateImageActivity extends YTBaseActivity  {


    String path;
    String hdpath;
    String fileName;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.iv_file)
    PinchImageView mIvFile;
    @BindView(R.id.tv_file)
    TextView mTvFile;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_certificate_img);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("证件照详情");
        showBack();
        path = getIntent().getExtras().getString("CARD_FILE");
        hdpath = getIntent().getExtras().getString("HDFSFILENAME");
        fileName = getIntent().getExtras().getString("FILENAME");
        final String url = "http://218.87.176.156:80/platform/DownFileServlet?type=1&DOWNPATH=" + path +
                "&HDFSFILENAME=" + hdpath + "&FILENAME=" + fileName;
       i("url:"+url);

        String fileType = StringUtil.getLastType(fileName);
        if ("pdf".equalsIgnoreCase(fileType)) {
            mIvFile.setVisibility(View.GONE);
            mTvFile.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
            doPermissionRW("读写", new PermissionCallBack() {
                @Override
                public void accept() {
                    FileDownLoadUtils.downloadFile(fileName, url, new FileDownLoadUtils.DownLoadCallBack() {
                        @Override
                        public void success(String sdPath) {
                            i("fileName:"+fileName+"-url:"+url+"-sdPath:"+sdPath);
                            showPdfView(sdPath);
                        }

                        @Override
                        public void fail() {
                        }
                    });
                }
            });

        }
        //图片类型
        else if (StringUtil.isImg(fileType)) {
            mIvFile.setVisibility(View.VISIBLE);
            mTvFile.setVisibility(View.GONE);
            mWebView.setVisibility(View.GONE);
            ImageLoad.setUrlImgCc(this, url, mIvFile, new ImageLoad.InterfaceImageListener() {
                @Override
                public void onError() {
                    DialogUtils.showToast(CertificateImageActivity.this,"图片格式有误。");
                }
            });
            //第三方文件类型
        } else if (StringUtil.isThreeFile(fileType)) {
            mIvFile.setVisibility(View.GONE);
            mTvFile.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            doPermissionRW("读、写", new PermissionCallBack() {
                @Override
                public void accept() {
                    FileDownLoadUtils.downloadFile(fileName, url, new FileDownLoadUtils.DownLoadCallBack() {
                        @Override
                        public void success(String sdPath) {
                            Intent intent = StringUtil.getWordFileIntent(CertificateImageActivity.this, sdPath);
                            startActivity(intent);
                        }
                        @Override
                        public void fail() {
                        }
                    });
                }
            });


        }

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
        i("webUrl:"+"file:///android_asset/index.html?" + sdPath);
        mWebView.loadUrl("file:///android_asset/index.html?" + sdPath);
//        mPdfvCar1.fromFile(new File(sdPath))
//                .defaultPage(0)
//                .onPageChange(CertificateImageActivity.this)
//                .enableSwipe(true)
////                                .enableAnnotationRendering(true)
//                .onLoad(CertificateImageActivity.this)
////                                .scrollHandle(new DefaultScrollHandle(MainActivity.this))
//                .spacing(10) // in dp
//                .onPageError(CertificateImageActivity.this)
//                .load();
    }



    @OnClick({R.id.webview, R.id.iv_file, R.id.tv_file})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.webview:
                break;
            case R.id.iv_file:
                break;
            case R.id.tv_file:
                break;
        }
    }
}
