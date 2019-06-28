package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.Interaction.OnMultiClickListener;
import com.powerrich.office.oa.bean.SearchItem;


import java.util.List;

/**
 * 搜索的适配器
 */
public class SearchRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ///标题
    private final int TITLE_TYPE = 1;
    //标题内容
    private final int ITEM_CONTENT_TYPE = 2;
    //服务内容
    private final int ITEM_SERVICE_TYPE = 3;
    //更多
    private final int ITEM_MORE_TYPE = 4;
    //线
    private final int ITEM_LINE_TYPE = 5;

    private IClickListener iClickListener;
    private Context context;

    private List<SearchItem> searchItemList;

    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    public void setData(List<SearchItem> searchItemList) {
        this.searchItemList = searchItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int type = searchItemList.get(position).getType();
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        context = parent.getContext();
        View itemView;
        if (viewType == TITLE_TYPE) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_title, parent, false);
            viewHolder = new TitleViewHolder(itemView);
        } else if (viewType == ITEM_SERVICE_TYPE) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_service_content, parent, false);
            viewHolder = new ServiceViewHolder(itemView);
        } else if (viewType == ITEM_MORE_TYPE) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_more, parent, false);
            viewHolder = new MoreViewHolder(itemView);
        } else if (viewType == ITEM_LINE_TYPE) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_item_layout, parent, false);
            viewHolder = new LineViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_content_item, parent, false);
            viewHolder = new ContentViewHolder(itemView);
        }

        return viewHolder;
    }

    public interface IClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        SearchItem searchItem = searchItemList.get(position);
        holder.itemView.setTag(searchItem);
        if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).tv_search_title.setText(searchItem.getName());
        } else if (holder instanceof ServiceViewHolder) {
            // ((ServiceViewHolder) holder).tv_service.setText("服务：" + position);
            ServiceViewHolder serviceViewHolder = ((ServiceViewHolder) holder);
            SearchServiceAdapter searchApplicationAdapter = new SearchServiceAdapter();// mList.get(position).getApplicationList(), listener
            serviceViewHolder.rv_application.setAdapter(searchApplicationAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
            //  gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            serviceViewHolder.rv_application.setLayoutManager(gridLayoutManager);
            searchApplicationAdapter.setData(searchItem.getArrayList());
        } else if (holder instanceof MoreViewHolder) {
            ((MoreViewHolder) holder).tv_more.setText("查看更多");
        } else if (holder instanceof LineViewHolder) {

        } else {
            ((ContentViewHolder) holder).tv_departName.setText(searchItem.getName());
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "View", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
        holder.itemView.setOnClickListener(new OnMultiClickListener() {
            @Override
            protected void onMultiClick(View v) {
                if (iClickListener != null) {
                    iClickListener.onItemClick(holder.itemView, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchItemList != null ? searchItemList.size() : 0;
    }


    class MoreViewHolder extends RecyclerView.ViewHolder {


        TextView tv_more;

        public MoreViewHolder(View itemView) {
            super(itemView);
            tv_more = (TextView) itemView.findViewById(R.id.tv_more);
        }

    }


    class ServiceViewHolder extends RecyclerView.ViewHolder {


        RecyclerView rv_application;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            rv_application = (RecyclerView) itemView.findViewById(R.id.rv_application);

        }

    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        TextView tv_search_title;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tv_search_title = (TextView) itemView.findViewById(R.id.tv_search_title);
        }

    }


    class LineViewHolder extends RecyclerView.ViewHolder {


        public LineViewHolder(View itemView) {
            super(itemView);

        }

    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        TextView tv_departName;

        public ContentViewHolder(View itemView) {
            super(itemView);
            tv_departName = (TextView) itemView.findViewById(R.id.tv_departName);
        }

    }
}







