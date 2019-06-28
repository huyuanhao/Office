package com.powerrich.office.oa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.Interaction.OnMultiClickListener;
import com.powerrich.office.oa.bean.SearchBean;
import com.powerrich.office.oa.bean.WorkNew;
import com.powerrich.office.oa.tools.AutoUtils;

import java.util.List;

/**
 * 搜索更多的适配器
 */
public class WorkRecycleAdapter extends RecyclerView.Adapter<WorkRecycleAdapter.SearchMoreViewHolder> {
    private List<WorkNew> arrayList;
    private IOnClick iOnClick;

    public void setiOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }

    @Override
    public SearchMoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_more_item, parent, false);

        return new SearchMoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchMoreViewHolder holder, final int position) {
        WorkNew searchBean = arrayList.get(position);
        holder.tv_name.setText(searchBean.getITEMNAME());
        holder.tv_unit.setText(searchBean.getNORMACCEPTDEPART());
        holder.itemView.setOnClickListener(new OnMultiClickListener() {
            @Override
            protected void onMultiClick(View v) {
                if (iOnClick != null) {
                    iOnClick.onPosition(position);
                }
            }
        });
    }

    public void setData(List<WorkNew> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }



    public interface  IOnClick{
        void onPosition(int position);
    }

    class SearchMoreViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_unit;


        public SearchMoreViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_unit = (TextView) itemView.findViewById(R.id.tv_unit);
        }

    }
}