package com.powerrich.office.oa.activity.things;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.tools.ToastUtils;
import com.yt.simpleframe.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author MingPeng
 * @date 2018/6/22
 */

public class TravelActivity extends BaseActivity {

    private int[] icons = {R.drawable.icon_out_on, R.drawable.icon_out_tr,
            R.drawable.icon_out_fo, R.drawable.icon_out_fv};
    private String[] names = {"天气", "公交站", "加油站", "实时路况"};
    //    "实时公交",R.drawable.icon_out_tw,
    private String city;
    private String lat;
    private String lng;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_travel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        city = (String) SharedPreferencesUtils.getParam(this, "city", "鹰潭市");
        lat = (String) SharedPreferencesUtils.getParam(this, "lat", "28.27");
        lng = (String) SharedPreferencesUtils.getParam(this, "lng", "117.07");
        if (city.contains("市")) {
            city = city.replace("市", "");
        }

        initTitleBar(getString(R.string.travel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, null);
        initTravelGridView();
    }

    protected void initTravelGridView() {
        GridView gridView = (GridView) findViewById(R.id.gv_travel);
        gridView.setAdapter(new CommonAdapter<Map<String, Object>>(this, getData(icons, names), R.layout.service_gv_item) {

            @Override
            public void convert(ViewHolder holder, Map<String, Object> item) {
                holder.setImageResource(R.id.image, ((int) item.get("image")));
                holder.setTextView(R.id.text, ((String) item.get("text")));
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://天气
                        startActivity(new Intent().setClass(TravelActivity.this, WebNearActivity.class)
                                .putExtra("type", "19"));
                        break;
//                    case 1:
//                        ToastUtils.showMessage(TravelActivity.this, getString(R.string.developing));
//                        break;
                    case 1://公交站
                        startActivity(new Intent()
                                .setClass(TravelActivity.this, WebNearActivity.class)
                                .putExtra("type", "20"));
                        break;
                    case 2://加油站
                        startActivity(new Intent()
                                .setClass(TravelActivity.this, WebNearActivity.class)
                                .putExtra("type", "5"));
                        break;
                    case 3://实时路况
                        startActivity(new Intent()
                                .setClass(TravelActivity.this, WebNearActivity.class)
                                .putExtra("type", "21"));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public List<Map<String, Object>> getData(int[] iconIds, String[] iconNames) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < iconNames.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", iconIds[i]);
            map.put("text", iconNames[i]);
            dataList.add(map);
        }
        return dataList;
    }
}
