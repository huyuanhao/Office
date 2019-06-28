package com.powerrich.office.oa.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.BeanUtils;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.WaterInfoBean;
import com.yt.simpleframe.http.bean.WaterInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.KeyboardUtils;
import com.yt.simpleframe.utils.SharedPreferencesUtils;
import com.yt.simpleframe.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WaterServiceActivity extends BaseActivity {
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_search)
    AutoCompleteTextView tvSearch;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.id)
    TextView id;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.water_quantity)
    TextView waterQuantity;
    @BindView(R.id.this_period)
    TextView thisPeriod;
    @BindView(R.id.last_period)
    TextView lastPeriod;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.back)
    TextView back;
    ArrayAdapter mSearchAdapter;
    int MAX_HISTORY_COUNT = 5;
    String SP_KEY_SEARCH = "water";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_water_service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();

    }

    public void initView() {
        barTitle.setText("供水服务查询");
        String[] mSearchHistoryArray = getHistoryArray();
        mSearchAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                mSearchHistoryArray
        );
        tvSearch.setAdapter(mSearchAdapter);  // 设置适配器
    }

    public void getInfo1(String account) {
        ApiManager.getApi().WaterInfo(RequestBodyUtils.WaterInfo(account))
                .compose(RxSchedulers.<WaterInfoBean>io_main())
                .subscribe(new BaseSubscriber<WaterInfoBean>() {
                    @Override
                    public void result(WaterInfoBean waterInfoBean) {
                        if (BeanUtils.isEmpty(waterInfoBean.getDATA())) {
                            T.showShort(WaterServiceActivity.this,"暂无数据");
                        } else
                            setView(waterInfoBean.getDATA().get(0));
                    }
                });
    }


    private void setView(WaterInfo bean) {
        id.setText(bean.getHH());
        name.setText(replaceString2Star(bean.getHM(),1,1));
        address.setText(replaceString2Star(bean.getDZ(),2,2));
        waterQuantity.setText(bean.getSYSL());
        thisPeriod.setText(bean.getBQXZ());
        lastPeriod.setText(bean.getSQXZ());
        date.setText(bean.getBQCBR());
        type.setText(bean.getSBLX());
    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content
     *            传入的字符串
     * @param frontNum
     *            保留前面字符的位数
     * @param endNum
     *            保留后面字符的位数
     * @return 带星号的字符串
     */
    public static String replaceString2Star(String content, int frontNum, int endNum) {
        if (content == null || content.trim().isEmpty())
            return content;

        int len = content.length();

        if (frontNum >= len || frontNum < 0 || endNum >= len || endNum < 0)
            return content;

        if (frontNum + endNum >= len)
            return content;


        int beginIndex = frontNum;
        int endIndex = len - endNum;
        char[] cardChar = content.toCharArray();

        for (int j = beginIndex; j < endIndex; j++) {
            cardChar[j] = '*';
        }

        return new String(cardChar);
    }

    @OnClick({R.id.bar_back, R.id.search,R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.search:
                saveSearchHistory();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private String[] getHistoryArray() {
        String his = SharedPreferencesUtils.getHistoryFromSharedPreferences(WaterServiceActivity.this, SP_KEY_SEARCH, "");
        String[] array = his.split("\\|");
        if (array.length > MAX_HISTORY_COUNT) {         // 最多只提示最近的50条历史记录
            String[] newArray = new String[MAX_HISTORY_COUNT];
            System.arraycopy(array, 0, newArray, 0, MAX_HISTORY_COUNT); // 实现数组间的内容复制
        }
        return array;
    }

    private void saveSearchHistory() {
        KeyboardUtils.hideKeyboard(WaterServiceActivity.this,tvSearch);
        String text = tvSearch.getText().toString().trim();       // 获取搜索框文本信息
        if (TextUtils.isEmpty(text)) {                      // null or ""
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        getInfo1(text);

        String old_text = SharedPreferencesUtils.getHistoryFromSharedPreferences(WaterServiceActivity.this, SP_KEY_SEARCH, "");// 获取SP中保存的历史记录
        StringBuilder sb;
        if ("".equals(old_text)) {
            sb = new StringBuilder();
        } else {
            sb = new StringBuilder(old_text);
        }
        sb.append(text + "|");      // 使用逗号来分隔每条历史记录

        // 判断搜索内容是否已存在于历史文件中，已存在则不再添加
        if (!old_text.contains(text + "|")) {
            SharedPreferencesUtils.saveHistoryToSharedPreferences(WaterServiceActivity.this, SP_KEY_SEARCH, sb.toString());  // 实时保存历史记录
            mSearchAdapter.add(text);        // 实时更新下拉提示框中的历史记录
//            Toast.makeText(this, "Search saved: " + text, Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "Search existed: " + text, Toast.LENGTH_SHORT).show();
        }
    }

}
