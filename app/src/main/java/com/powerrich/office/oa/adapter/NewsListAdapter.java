package com.powerrich.office.oa.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.NewsInfo;
import com.powerrich.office.oa.tools.BeanUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ch on 2016/6/14.
 */
public class NewsListAdapter extends BaseAdapter {

    private List<NewsInfo> infoList;
    private Context context;
    
    public NewsListAdapter(Context context) {
    	this.context = context;
    }
    
    public NewsListAdapter(Context context, List<NewsInfo> infoList) {
        this.context = context;
        this.infoList = infoList;
    }
    
    public void setData(List<NewsInfo> info) {
    	if (null == infoList) {
    		infoList = new ArrayList<NewsInfo>(); 
    	}
    	infoList.clear();
    	if (!BeanUtils.isEmpty(info)) {
    		infoList.addAll(info);
    	}
    	notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return infoList == null ? 0 : infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.news_list_item, null);
            holder = new ViewHolder();
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tv_news_title = (TextView) convertView.findViewById(R.id.tv_news_title);
            holder.tv_data = (TextView) convertView.findViewById(R.id.tv_policy_data);
            holder.typeTxt = (TextView) convertView.findViewById(R.id.tv_policy_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewsInfo newsInfo = infoList.get(position);
        if (!BeanUtils.isNullOrEmpty(newsInfo.getImgUrl())) {
        	Picasso.with(context).load(newsInfo.getImgUrl()).placeholder(newsInfo.getIcon()).into(holder.iv_icon);
        } else if (newsInfo.getIcon() > 0) {
        	holder.iv_icon.setImageResource(newsInfo.getIcon());
        }
        holder.tv_news_title.setText(newsInfo.getNewsTitle());
        holder.typeTxt.setText(newsInfo.getNewsType());
        holder.tv_data.setText(newsInfo.getDate());
        return convertView;
    }


    class ViewHolder {
        private ImageView iv_icon;
        private TextView tv_news_title;
      //private TextView tv_news_content;
        private TextView tv_data;
        private TextView typeTxt;
    }
}

