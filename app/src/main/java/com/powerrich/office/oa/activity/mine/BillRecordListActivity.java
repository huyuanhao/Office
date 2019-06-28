package com.powerrich.office.oa.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.BaseRefreshActivity;
import com.yt.simpleframe.view.recyclerview.viewholder.RecycleviewViewHolder;

import butterknife.ButterKnife;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11/011
 * 版权：
 */
public class BillRecordListActivity extends BaseRefreshActivity<String> {
    @Override
    public void loadListData() {
        setListData(null);
        finishLoad();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("账单记录");
        onRefresh();
    }



    @Override
    public RecyclerView.ViewHolder getViewHolder(int viewType) {
        View view = inflateContentView(R.layout.bill_record_info);
        ButterKnife.bind(this,view);
        return new MessageVH(view);
    }

    @Override
    public void BindViewHolder(RecyclerView.ViewHolder viewHolder, int viewType, int position, Object data) {

    }

    class MessageVH extends RecycleviewViewHolder{

        public MessageVH(View view) {
            super(view);
        }
    }

}
