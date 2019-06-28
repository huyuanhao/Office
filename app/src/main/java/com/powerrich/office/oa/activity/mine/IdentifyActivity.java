package com.powerrich.office.oa.activity.mine;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.enums.IdentifyType;
import com.powerrich.office.oa.fragment.mine.IdentifyFragment;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11
 * 版权：
 */

public class IdentifyActivity extends YTBaseActivity {


    private IdentifyFragment fragment1, fragment2, fragment3;
    public static boolean faceBoo = false;
    public static boolean card1 = false;
    public static boolean card2 = false;

    public static Bitmap card1Bitmap, card2Bitmap, faceBitmap;
    public static String name = "";
    public static String idNumber = "";
    public static String selectCity = "";
    public static String group = "";
    public static String email = "";
    public static String address = "";
    public static String sex = "";


    protected View onCreateContentView() {
        fragment1 = IdentifyFragment.getInstance(IdentifyType.页面1);
        fragment2 = IdentifyFragment.getInstance(IdentifyType.页面2);
        fragment3 = IdentifyFragment.getInstance(IdentifyType.页面3);
        replaceFragment(R.id.root_flt_content, fragment1);
        return null;
    }

    public void replaceFragment(IdentifyType type) {
        FragmentManager fm = getFragmentManager();
        if (type == IdentifyType.页面1) {
            replaceFragmentAnimation(R.id.root_flt_content, fragment1);
        } else if (type == IdentifyType.页面2) {
            replaceFragmentAnimation(R.id.root_flt_content, fragment2);
        } else {
            replaceFragmentAnimation(R.id.root_flt_content, fragment3);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("身份验证", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish();
                }
//                getSupportFragmentManager().popBackStackImmediate();
            }
        });
        showBack();
        close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        close();
    }

    void close() {
        faceBoo = false;
        card1 = false;
        card2 = false;
        card1Bitmap = null;
        card2Bitmap = null;
        faceBitmap = null;
        String name = "";
        String idNumber = "";
        String selectCity = "";
        String group = "";
        String email = "";
        String address = "";
        String sex = "";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean b = super.onKeyDown(keyCode, event);
        return b;
    }
}
