package com.powerrich.office.oa.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.chad.library.adapter.base.BaseQuickAdapter;
import com.powerrich.office.oa.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.RoundBackgroundColorSpan;
import com.yt.simpleframe.http.bean.entity.NewsInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/6/6.
 */

public class InteractAdapter extends BaseQuickAdapter<NewsInfo,BaseViewHolder> {
    public InteractAdapter(int layoutResId, @Nullable List<NewsInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsInfo item) {
//        helper.setText(R.id.tv_title,item.getTITLE());
        helper.setText(R.id.tv_name,item.getSOURCE());
        helper.setText(R.id.tv_time, DateUtils.getDateStr(item.getCREATE_DATE(),null));

        String str1 = "热点";
        if(item.isHOTSPOT()){
            helper.setGone(R.id.tv_hot,false);
            str1 = "热点";
        }else {
            helper.setGone(R.id.tv_hot,false);
            str1 = "";
        }
        String str2 = item.getTITLE();
        String str = str1 + str2;
        SpannableString spannableString = new SpannableString(str);
        int bgColor = Color.parseColor("#FF2525");
        int textColor = Color.parseColor("#FF2525");
        RoundBackgroundColorSpan span = new RoundBackgroundColorSpan(bgColor, textColor, 5);
        spannableString.setSpan(span, 0, str1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        TextView  tvTitle = helper.getView(R.id.tv_title);
        tvTitle.setText(spannableString);
//        tvTitle.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
