package com.powerrich.office.oa.activity.home.service;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.bean.EthnicInfo;
import com.powerrich.office.oa.bean.LossInfo;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.PickUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/1
 * 版权：
 */

public class SocialLossActivity extends BaseActivity {


    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_card_id)
    TextView mTvCardId;
    @BindView(R.id.tv_group)
    TextView mTvGroup;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;


    String groupId = "01";
    String typeId = "21";
    EthnicInfo mEthnicInfo = null;
    LossInfo mLossInfo = null;

    String idCard = "";
    String name = "";
    @BindView(R.id.rlt_group)
    RelativeLayout mRltGroup;
    @BindView(R.id.rlt_type)
    RelativeLayout mRltType;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_social_loss;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTitleBar("社保卡挂失解挂", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, null);

        UserInfo.DATABean data = LoginUtils.getInstance().getUserInfo().getDATA();
        name = data.getREALNAME();
        idCard = data.getIDCARD();
        mTvName.setText(name);
        mTvCardId.setText(idCard);
        mTvGroup.setText("汉族");
        mTvType.setText("临时挂失");

    }


    @OnClick({R.id.rlt_group, R.id.rlt_type, R.id.tv_commit, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlt_group:
                PickUtils.showGroupView(this, mEthnicInfo, new PickUtils.CallBackData<EthnicInfo>() {
                    @Override
                    public void getData(EthnicInfo ethnicInfo) {
                        mEthnicInfo = ethnicInfo;
                        mTvGroup.setText(ethnicInfo.getName());
                        groupId = ethnicInfo.getId() + "";
                    }
                });
                break;
            case R.id.rlt_type:
                PickUtils.showCocialLossType(this, mLossInfo, new PickUtils.CallBackData<LossInfo>() {
                    @Override
                    public void getData(LossInfo info) {
                        mLossInfo = info;
                        typeId = info.getId()+"";
                        mTvType.setText(info.getName());
                    }
                });

                break;
            case R.id.tv_commit:
                commit();
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    private void commit() {
        ApiRequest request = OAInterface.ciBasicInfoGsjg(idCard, name, "", groupId, typeId);
//        ApiRequest request = OAInterface.ciBasicInfoGsjg("340123198209242591", "许勇", "01", "01", typeId);
        invoke.invokeWidthDialog(request, new BaseRequestCallBack() {
            @Override
            public void process(HttpResponse response, int what) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String code = item.getString("code");
                String message = item.getString("message");
                DialogUtils.showToast(SocialLossActivity.this, message);
                if("0".equals(code)){
                    SocialLossActivity.this.finish();
                }

            }
        });
    }


}
