package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.enums.UserType;
import com.powerrich.office.oa.tools.LoginUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/6/006
 * 版权：
 */
public class CompanyListActivity extends YTBaseActivity {

    @BindView(R.id.llt_content)
    LinearLayout mLltContent;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private List<UserInfo.CompanyInfo> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的企业");
        list = LoginUtils.getInstance().getUserInfo().getDATA().getCOMPANYLIST();
        if(list != null && list.size() > 0){
            addItemView();
        }else{
            mLltContent.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_company_list);
        ButterKnife.bind(this, view);
        return view;
    }


    void addItemView() {
        mLltContent.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        TextView mTvCompanyName;
        TextView mTvCompanyCode;
        TextView mTvCompanyAddress;
        TextView mTvCompanyTime;
        for (int i = 0; i < list.size(); i++) {
            View view = inflateContentView(R.layout.item_company_list);
            mTvCompanyName = (TextView) view.findViewById(R.id.tv_company_name);
            mTvCompanyCode = (TextView) view.findViewById(R.id.tv_company_code);
            mTvCompanyAddress = (TextView) view.findViewById(R.id.tv_company_address);
            mTvCompanyTime = (TextView) view.findViewById(R.id.tv_company_time);
            view.setOnClickListener(new MyOnclick(i));
            UserInfo.CompanyInfo info = list.get(i);
            mTvCompanyName.setText("组织机构名称：" + info.getCOMPANYNAME());
            mTvCompanyCode.setText("统一社会信用代码：" + info.getID());
            mTvCompanyAddress.setText("组织机构地址：" + info.getQYTXDZ());
            mTvCompanyTime.setText("注册时间：" + info.getCREATETIME());
            mLltContent.addView(view);
        }
    }

    class MyOnclick implements View.OnClickListener {

        private int position;

        public MyOnclick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CompanyListActivity.this, UserInfoActivity.class);
            intent.putExtra("type", UserType.企业);
            intent.putExtra("info", list.get(position));
            startActivity(intent);
        }
    }

}
