package com.powerrich.office.oa.activity.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.tools.ChooseImgUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.ImageLoad;
import com.powerrich.office.oa.tools.LoginUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.UserInfoBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/26
 * 版权：
 */

public class ChooseHeadImgActivity extends YTBaseActivity {

    @BindView(R.id.civ)
    ImageView mCiv;
    @BindView(R.id.tv_choose_imgs)
    TextView mTvChooseImgs;
    @BindView(R.id.tv_tacking)
    TextView mTvTacking;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_choose_img_head);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置头像");
        showBack();
//        doPremisionType(3);
        UserInfo.DATABean info = LoginUtils.getInstance().getUserInfo().getDATA();
        String filePath = info.getHEADPORTRAIT_DOWNPATH();
        final String hdfsFileName = info.getHEADPORTRAIT_FILENAME();
        String url = "http://218.87.176.156:80/platform/DownFileServlet?" + "type=1" + "&DOWNPATH=" + filePath +
                "&HDFSFILENAME=" + hdfsFileName + "&FILENAME=" + "head.jpg";
        ImageLoad.setCircleUrl(this, url, mCiv, R.drawable.pic_mine_head);
//        if (!StringUtil.isEmpty(hdfsFileName)) {
//            ImageLoad.setUrl(this, url, mCiv, R.drawable.pic_mine_head);
//        } else {
//            mCiv.setImageBitmap(BitmapFactory.decodeResource(ChooseHeadImgActivity.this.getResources(), R.drawable
//                    .pic_mine_head));
//        }
    }


    //定义图片的Uri
    private Uri photoUri;
    //图片文件路径
    private String picPath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //从相册取图片，有些手机有异常情况，请注意
            if (requestCode == ChooseImgUtils.SELECT_PIC_CODE) {
                if (null != data && null != data.getData()) {
                    photoUri = data.getData();
                    picPath = ChooseImgUtils.uriToFilePath(photoUri, this);
//                    startPhotoZoom(photoUri, PHOTO_CROP_CODE);

                    ImageLoad.setCircleUrl(this, picPath, mCiv, R.drawable.pic_mine_head);

//                    Bitmap bitmap = BitmapFactory.decodeFile(picPath);
//                    if (bitmap != null) {
//                        //这里可以把图片进行上传到服务器操作
//                        mCiv.setImageBitmap(bitmap);
//                    }
                } else {
                    Toast.makeText(this, "图片选择失败", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == ChooseImgUtils.TAKE_PHOTO_CODE) {
                String[] pojo = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(imgUtils.photoUri, pojo, null, null, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                    cursor.moveToFirst();
                    picPath = cursor.getString(columnIndex);
                    File file = new File(picPath);
                    String fileName = file.getName();
                    System.out.println("picPath=" + picPath + "\nfileName=" + fileName);
                    if (Build.VERSION.SDK_INT < 14) {
                        cursor.close();
                    }
                }
                if (picPath != null) {
                    photoUri = Uri.fromFile(new File(picPath));

                    ImageLoad.setCircleUrl(this, picPath, mCiv, R.drawable.pic_mine_head);

//                    startPhotoZoom(photoUri, PHOTO_CROP_CODE);
//                    Bitmap bitmap = BitmapFactory.decodeFile(picPath);
//                    if (bitmap != null) {
//                        //这里可以把图片进行上传到服务器操作
//                        mCiv.setImageBitmap(bitmap);
//                    }
                } else {
                    Toast.makeText(this, "图片选择失败", Toast.LENGTH_LONG).show();
                }


            }
            if (picPath != null && imgUtils != null) {
//                String saveDir = new File(Environment.getExternalStorageDirectory() + "/download").getPath();

                //压缩
//                BitmapUtils.compressAndSavePicture(picPath, 600, saveDir, "head.jpg");

                File file = null;
                try {
                    file = Luban.with(ChooseHeadImgActivity.this).get(picPath);
                    String userName = LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME();
                    imgUtils.upload(new ChooseImgUtils.ExeCallBack() {
                        @Override
                        public void exeCallBack() {
                            //重新获取用户信息
                            queryUser(LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME());
                        }
                    }, file.getName(), file.getAbsolutePath(), userName, Constants.UPLOAD_HEAD_IMG_URL);
                } catch (IOException e) {
                    e.printStackTrace();
                }


//                String uploadPaht = saveDir + "/head.jpg";
//                File file = new File(picPath);
//                String fileName = file.getName();
//                String userName = LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME();

            }

        }
    }


    ChooseImgUtils imgUtils;

    @OnClick({R.id.tv_choose_imgs, R.id.tv_tacking})
    public void onViewClicked(View view) {
        if (imgUtils == null)
            imgUtils = new ChooseImgUtils(this, 1);
        switch (view.getId()) {
            case R.id.tv_choose_imgs:
                doPremisionType(1);
                break;
            case R.id.tv_tacking:
                doPremisionType(2);
                break;
        }
    }

    void doPremisionType(final int type) {
        doPermission("读写、相机", new PermissionCallBack() {
                    @Override
                    public void accept() {
                        if (type == 1) {
                            imgUtils.pickPhoto();
                        } else if (type == 2) {
                            imgUtils.picTyTakePhoto();
                        }
//                        UserInfo.DATABean info = LoginUtils.getInstance().getUserInfo().getDATA();
//                        ChooseImgUtils.showImage(ChooseHeadImgActivity.this, mCiv, info);
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA);
    }


    //检查手机号码是否有效
    private void queryUser(final String phone) {
        ApiManager.getApi().queryUser(RequestBodyUtils.queryUser(phone))
                .compose(RxSchedulers.<UserInfoBean>io_main())
                .subscribe(new BaseSubscriber<UserInfoBean>() {
                    @Override
                    public void result(UserInfoBean baseBean) {
                        UserInfo.DATABean data = LoginUtils.getInstance().getUserInfo().getDATA();
                        data.setHEADPORTRAIT_DOWNPATH(baseBean.getDATA().getHEADPORTRAIT_DOWNPATH());
                        data.setHEADPORTRAIT_FILENAME(baseBean.getDATA().getHEADPORTRAIT_FILENAME());
                        LoginUtils.getInstance().getUserInfo().setDATA(data);
                        ChooseImgUtils.showImage(ChooseHeadImgActivity.this, null, data);
                    }
                });
    }


}
