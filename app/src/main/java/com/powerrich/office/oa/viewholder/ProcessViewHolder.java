package com.powerrich.office.oa.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.enums.SearchFragmentType;
import com.yt.simpleframe.http.bean.entity.CollectionInfo;
import com.yt.simpleframe.http.bean.entity.ProcessInfo;
import com.yt.simpleframe.http.bean.entity.ReservationInfo;
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


public class ProcessViewHolder extends RecycleviewViewHolder {
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
    public TextView mTvProcessGui;
    @BindView(R.id.tv_process_go)
    public TextView mTvPocessGo;

    SearchFragmentType type;
    private ProcessInfo info;
    private CollectionInfo mCollectionInfo;
    private ReservationInfo mReservationInfo;

    public ProcessViewHolder(Unbinder unbinder, View view, SearchFragmentType type) {
        super(view);
        unbinder = ButterKnife.bind(this, view);
        this.type = type;
    }

    public void bindView(Object object) {

        if (type == SearchFragmentType.收藏 || type == SearchFragmentType.预约) {
            mIvProcessImg.setVisibility(View.GONE);
            mIvNext.setVisibility(View.VISIBLE);
            mLtCollection.setVisibility(View.GONE);
        }


        if (type == SearchFragmentType.暂存 || type == SearchFragmentType.已办理 || type == SearchFragmentType.已完结) {

            mIvNext.setVisibility(View.VISIBLE);
            mIvProcessImg.setVisibility(View.GONE);
            if(type == SearchFragmentType.暂存){
                mLtCollection.setVisibility(View.GONE);
            }else if(type == SearchFragmentType.已办理){
                mTvPocessGo.setVisibility(View.VISIBLE);
                mTvProcessGui.setVisibility(View.GONE);
            }else if(type == SearchFragmentType.已完结){
                mTvPocessGo.setVisibility(View.VISIBLE);
                mLtCollection.setVisibility(View.GONE);
//                mTvPocessGo.setText("评价");
//                mTvProcessGui.setText("查看详情");
            }

            info = (ProcessInfo) object;
//            if(type == SearchFragmentType.已完结 && info.isSFPJ()){
//                mTvPocessGo.setText("查看评价");
//            }
            mTvProcessContent.setText(info.getITEMNAME());
            if(type == SearchFragmentType.暂存){
                mTvProcessTime.setText("办件时间: "+info.getMODIFY_TIME());
            }else if(type == SearchFragmentType.已办理){
                mTvProcessTime.setText("申请时间: "+info.getDESDATE());
            }else if(type == SearchFragmentType.已完结){
                mTvProcessTime.setText("办结时间: "+info.getTBJDATE());
            }
            mTvProcessId.setText("办理部门: "+info.getNORMACCEPTDEPART());
            mCbProcess.setChecked(info.checkBoo);
        }else if(type == SearchFragmentType.收藏){
            mCollectionInfo = (CollectionInfo) object;
            mTvProcessContent.setText(mCollectionInfo.getITEMNAME());
            mTvProcessId.setText("办理部门: "+mCollectionInfo.getNORMACCEPTDEPART());
            mTvProcessTime.setText("收藏时间: "+mCollectionInfo.getSCTIME());
        }else if(type == SearchFragmentType.预约){
            mReservationInfo = (ReservationInfo) object;
            mTvProcessContent.setText(mReservationInfo.getITEMNAME());
            mTvProcessId.setText("办理部门: "+mReservationInfo.getDEPTNAME());
            mTvProcessTime.setText("办理时间: "+mReservationInfo.getORDER_DATE());
        }


    }


}
