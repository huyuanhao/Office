package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.fragment.mine.MySpaceFragment;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.NoEmojiEditText;

import butterknife.ButterKnife;

/**
 * 我的空间
 */
public class MySpaceActivity extends YTBaseActivity implements View.OnClickListener{

    private NoEmojiEditText etSearchTxt;
    private Button btSearch;
    private FrameLayout fltProcessContent;
    private  MySpaceFragment fragment;
    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_my_space);
        ButterKnife.bind(this, view);
        fragment = MySpaceFragment.getInstance(null);
        replaceFragment(R.id.flt_process_content, fragment);
        return view;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        btSearch.setOnClickListener(this);
        etSearchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0){
                    fragment.activityChangeFragment(etSearchTxt.getText().toString());
                }

            }
        });
    }

    private void initData() {

    }

    private void initView() {
        setTitle("我的空间");


        etSearchTxt = (NoEmojiEditText) findViewById(R.id.et_search_txt);
        btSearch = (Button) findViewById(R.id.bt_search);
        fltProcessContent = (FrameLayout) findViewById(R.id.flt_process_content);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_my_space;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search:
                String s = etSearchTxt.getText().toString();
                if(!TextUtils.isEmpty(s)){
                    fragment.activityChangeFragment(s);
                }else{
                    DialogUtils.showToast(this,"搜索不能为空.");
                }



                break;
        }
    }
}
