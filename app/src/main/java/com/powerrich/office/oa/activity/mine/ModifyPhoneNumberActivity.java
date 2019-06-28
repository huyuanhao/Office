package com.powerrich.office.oa.activity.mine;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.enums.ModifyPhoneType;
import com.powerrich.office.oa.fragment.mine.ModifyPhoneFragment;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11
 * 版权：
 */

public class ModifyPhoneNumberActivity extends YTBaseActivity {

    private ModifyPhoneFragment fragment1,fragment2,fragment3;

    @Override
    protected View onCreateContentView() {
        fragment1 = ModifyPhoneFragment.getInstance(ModifyPhoneType.页面1);
        fragment2 = ModifyPhoneFragment.getInstance(ModifyPhoneType.页面2);
        fragment3 = ModifyPhoneFragment.getInstance(ModifyPhoneType.页面3);
        replaceFragment(R.id.root_flt_content,fragment1);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("修改手机号");
        showBack();
    }


    public void replaceFragment(ModifyPhoneType type){
        FragmentManager fm = getFragmentManager();
        if(type == ModifyPhoneType.页面1){
            replaceFragmentAnimation(R.id.root_flt_content, fragment1);
        }else if(type == ModifyPhoneType.页面2){
            replaceFragmentAnimation(R.id.root_flt_content, fragment2);
        }else{
            replaceFragmentAnimation(R.id.root_flt_content, fragment3);
        }
    }
}
