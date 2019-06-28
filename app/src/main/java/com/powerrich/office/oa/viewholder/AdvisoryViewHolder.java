package com.powerrich.office.oa.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.enums.AdvisoryType;
import com.yt.simpleframe.http.bean.entity.AdvisoryInfo;
import com.yt.simpleframe.view.recyclerview.viewholder.RecycleviewViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7
 * 版权：
 */


public class AdvisoryViewHolder extends RecycleviewViewHolder {
    @BindView(R.id.cb_process)
    public CheckBox mCbProcess;
    @BindView(R.id.iv_process_img)
    ImageView mIvProcessImg;
    @BindView(R.id.tv_process_content)
    TextView mTvProcessContent;
    @BindView(R.id.tv_process_id)
    TextView mTvProcessId;
    @BindView(R.id.tv_process_time)
    TextView mTvProcessTime;
    @BindView(R.id.iv_next)
    ImageView mIvNext;
    @BindView(R.id.lt_collection)
    LinearLayout mLtCollection;
    @BindView(R.id.tv_process_gui)
    TextView mTvProcessGui;
    @BindView(R.id.tv_process_go)
    TextView mTvPocessGo;

    AdvisoryType type;
    private AdvisoryInfo info;

    public AdvisoryViewHolder(Unbinder unbinder, View view, AdvisoryType type) {
        super(view);
        unbinder = ButterKnife.bind(this, view);
        this.type = type;
    }

    public void bindView(Object object) {
        mCbProcess.setVisibility(View.GONE);
        mLtCollection.setVisibility(View.GONE);

        info = (AdvisoryInfo) object;
        mTvProcessContent.setText(info.getTITLE());
        mTvProcessId.setText("部门: "+info.getSITE_NAME());
        mTvProcessTime.setText("时间: "+info.getCRETTIME());
        if("0".equals(info.getISREVERT())){
            //没有回复
            mIvProcessImg.setBackgroundResource(R.drawable.icon_madvice_yel);
        }else{
            mIvProcessImg.setBackgroundResource(R.drawable.icon_madvice_greenn);
        }

    }

}
