package com.powerrich.office.oa.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.BaseRefreshActivity;
import com.powerrich.office.oa.enums.AddressType;
import com.powerrich.office.oa.tools.LoginUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.AddressListBean;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.entity.AddressInfo;
import com.yt.simpleframe.http.bean.xmlentity.XmlAddressInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.view.StatusView;
import com.yt.simpleframe.view.recyclerview.viewholder.RecycleviewViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11/011
 * 版权：
 */
public class AddressManagerListActivity extends BaseRefreshActivity<AddressInfo> {


    @BindView(R.id.tv_add_address)
    TextView mTvAddAddress;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_address_manager);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        statusView = (StatusView) view.findViewById(R.id.multiplestatusview);
        mTvAddAddress = (TextView) view.findViewById(R.id.tv_add_address);
        mTvAddAddress = (TextView) view.findViewById(R.id.tv_add_address);
        mTvAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AddAddressActivity.class);
                intent.putExtra("type", AddressType.添加);
                startActivity(intent);
            }
        });
//        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void loadListData() {
        getData();
    }

    public void getData() {
        ApiManager.getApi().getAddressManager(RequestBodyUtils.getAddressManager(LoginUtils.getInstance().getUserInfo()
                .getAuthtoken()))
                .compose(RxSchedulers.<AddressListBean>io_main())
                .subscribe(new BaseSubscriber<AddressListBean>() {
                    @Override
                    public void result(AddressListBean baseBean) {
                        ArrayList<AddressInfo> data = baseBean.getDATA();
                        //将默认地址 放到首位
                        if (data != null && data.size() >= 2) {
                            for (int i = 0; i < data.size(); i++) {
                                if ("1".equals(data.get(i).getISDEFAULT())){
                                    data.add(0,data.remove(i));
                                }
                            }
                        }
                        setListData(data);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的地址");
        showBack();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        refreshLayout.autoRefresh(0);
//        onre
        onRefresh();
    }


    @Override
    public RecyclerView.ViewHolder getViewHolder(int viewType) {
        View view = inflateContentView(R.layout.item_address_manager);
        return new AddressVH(view);
    }

    @Override
    public void BindViewHolder(RecyclerView.ViewHolder viewHolder, int viewType, int position, Object data) {
        final AddressInfo info = (AddressInfo) data;
        AddressVH vh = (AddressVH) viewHolder;
        if(!TextUtils.isEmpty(info.getCITY())){
            vh.mTvAddressName.setText(info.getPROV()+ " "+info.getCITY()+ " " + info.getADDRESS());
        }else{
            vh.mTvAddressName.setText(info.getADDRESS());
        }

        vh.mTvPhone.setText(info.getHANDSET());
        vh.mTvName.setText(info.getSJRXM());

        vh.mRbDefault.setChecked(!info.getISDEFAULT().equals("0"));

        vh.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AddAddressActivity.class);
                intent.putExtra("type", AddressType.修改);
                intent.putExtra("data", info);
                startActivity(intent);
            }
        });

        if (!vh.mRbDefault.isChecked()) {
            vh.mRbDefault.setEnabled(true);
            vh.mRbDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modifyAddress(info, true);
                }
            });
        } else {
            vh.mRbDefault.setEnabled(false);
        }
    }

    @OnClick({R.id.rb_default, R.id.tv_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_default:
                break;
            case R.id.tv_add_address:
                break;
        }
    }

    class AddressVH extends RecycleviewViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        @BindView(R.id.tv_address_name)
        TextView mTvAddressName;
        @BindView(R.id.rb_default)
        RadioButton mRbDefault;

        public AddressVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void modifyAddress(AddressInfo data, boolean check) {
        String isDeault = "1";
        XmlAddressInfo info = new XmlAddressInfo(data.getADDRESS(), data.getCOMPANY_NAME(), data.getHANDSET(), isDeault, data
                .getSJRXM(), data.getTEL_NO(), data.getYZBM(), data.getPROV(), data.getCITY());
        ApiManager.getApi().exeNormal(RequestBodyUtils.modifyAddress(LoginUtils.getInstance().getUserInfo()
                .getAuthtoken(), data.getADDRESSID(), info))
                .compose(this.<BaseBean>loadingDialog())
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean baseBean) {
//                        refreshLayout.autoRefresh(0);

                    }
                });
    }

}
