package com.powerrich.office.oa.activity.mine.base;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.enums.IdentifyType;
import com.powerrich.office.oa.fragment.mine.IdentifyCompanyFragment;
import com.powerrich.office.oa.tools.LoginUtils;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11
 * 版权：
 */

public class IdentifyCompanyActivity extends YTBaseActivity {


    private IdentifyCompanyFragment fragment1, fragment2, fragment3;
    public static boolean card1 = false;
    public static boolean card2 = false;
    public static boolean companyBoo = false;
    public static Bitmap cardBitmap;
    public static String ogName = "";
    public static String ogCode = "";
    public static String ogAddress = "";
    public static String cardName = "";
    public static String idCard = "";
    public static String cardAddress = "";
    public static String phone = LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM();
    public static String companyType = "0";


    protected View onCreateContentView() {
        fragment1 = IdentifyCompanyFragment.getInstance(IdentifyType.页面1);
//        fragment2 = IdentifyCompanyFragment.getInstance(IdentifyType.页面2);
//        fragment3 = IdentifyCompanyFragment.getInstance(IdentifyType.页面3);
        replaceFragment(R.id.root_flt_content, fragment1);
        return null;
    }

    public void replaceFragment(IdentifyType type) {
        FragmentManager fm = getFragmentManager();
        if (type == IdentifyType.页面1) {
            replaceFragmentAnimation(R.id.root_flt_content, fragment1);
        }
//        else if(type == IdentifyType.页面2){
//            replaceFragmentAnimation(R.id.root_flt_content, fragment2);
//        }else{
//            replaceFragmentAnimation(R.id.root_flt_content, fragment3);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("企业验证");
        showBack();
        close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        close();
    }

    void close() {
        companyBoo = false;
        card1 = false;
        card2 = false;
        cardBitmap = null;
        ogName = "";
        ogCode = "";
        ogAddress = "";
        cardName = "";
        idCard = "";
        cardAddress = "";
        companyType = "0";
    }
}
