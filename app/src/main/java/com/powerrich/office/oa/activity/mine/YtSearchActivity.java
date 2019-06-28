package com.powerrich.office.oa.activity.mine;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.enums.SearchActivityType;
import com.powerrich.office.oa.fragment.mine.ActivityTypeFragment;
import com.powerrich.office.oa.notify.NotifyKey;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.MyDialog;
import com.yt.simpleframe.http.bean.entity.ProcessInfo;
import com.yt.simpleframe.notification.NotificationCenter;
import com.yt.simpleframe.notification.NotificationListener;
import com.yt.simpleframe.utils.KeyboardUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/6/006
 * 版权：
 */
public class YtSearchActivity extends YTBaseActivity implements NotificationListener {

    @BindView(R.id.et_search_txt)
    EditText mEtSearchTxt;
    @BindView(R.id.bt_search)
    Button mBtSearch;
    @BindView(R.id.flt_process_content)
    FrameLayout mFltProcessContent;
    @BindView(R.id.cb_select_all)
    CheckBox mCbSelectAll;
    @BindView(R.id.tv_select_count)
    TextView mTvSelectCount;
    @BindView(R.id.tv_select)
    TextView mTvSelect;
    @BindView(R.id.bt_del)
    Button mBtDel;
    @BindView(R.id.lt_frame)
    LinearLayout mLtFrame;
    @BindView(R.id.tv_add)
    TextView mTvAdd;

    private SearchActivityType type;
    private boolean clickLongBooo = false;

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_yt_search);
        ButterKnife.bind(this, view);
        type = (SearchActivityType) getIntent().getExtras().get("mineType");
        ActivityTypeFragment fragment = ActivityTypeFragment.getInstance(type);
        replaceFragment(R.id.flt_process_content, fragment);
        return view;
    }


    @Override
    protected void onResume() {
        super.onResume();
        cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvAdd.setVisibility(View.GONE);
        setTitle("我的办件");
        showBack();
        mLtFrame.setVisibility(View.GONE);
        NotificationCenter.defaultCenter.addListener(NotifyKey.SELECT_KEY, this);
        NotificationCenter.defaultCenter.addListener(NotifyKey.PROCESS_LONG_CLICK_KEY, this);
        NotificationCenter.defaultCenter.addListener(NotifyKey.HIDE_KEY, this);
    }

    public void showBottom(int index) {
        //0的时候显示  其他时候隐藏
        if (index != 0) {
            mLtFrame.setVisibility(View.GONE);
        } else if (index == 0 && clickLongBooo) {
            mLtFrame.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.bt_search, R.id.cb_select_all, R.id.bt_del, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                String searchText = mEtSearchTxt.getText().toString();
                if (!TextUtils.isEmpty(searchText)){
                    NotificationCenter.defaultCenter.postNotification(NotifyKey.SEARCH_KEY, searchText);
                }else{
                    ToastUtils.showMessage(this,"请输入搜索内容");
                }
                KeyboardUtils.hideSoftInput(this);
                break;
            case R.id.cb_select_all:
                mTvSelect.setText(mCbSelectAll.isChecked() ? "全取消" : "全选");
                NotificationCenter.defaultCenter.postNotification(mCbSelectAll.isChecked() ? NotifyKey.SELECT_ALL_KEY :
                        NotifyKey.UNSELECT_ALL_KEY);
                break;
            case R.id.bt_del:
                MyDialog.showDialog(this, "提示", "确定要删除所选数据吗？", new MyDialog.InterfaceClick() {
                    @Override
                    public void click() {
                        NotificationCenter.defaultCenter.postNotification(NotifyKey.DELETE_KEY);
                        clickLongBooo = false;
                    }
                });
                break;
            case R.id.tv_add:
                break;
        }
    }

    @Override
    public boolean onNotification(Notification notification) {
        if (NotifyKey.SELECT_KEY.equals(notification.key)) {
            int selectCount = 0;
            ArrayList<ProcessInfo> list = (ArrayList<ProcessInfo>) notification.object;
            for (ProcessInfo info :
                    list) {
                if (info.checkBoo)
                    selectCount++;
            }
            mTvSelectCount.setText(Html.fromHtml("共选择<font color='#ea7805'><big>" + selectCount + "</big></font>条数据"));
        } else if (NotifyKey.PROCESS_LONG_CLICK_KEY.equals(notification.key)) {
            //长按item 显示
            mLtFrame.setVisibility(View.VISIBLE);
            clickLongBooo = true;
        } else if (NotifyKey.HIDE_KEY.equals(notification.key)) {
//            mLtFrame.setVisibility(View.GONE);
            cancel();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationCenter.defaultCenter.removeListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mLtFrame.getVisibility() == View.VISIBLE) {
                cancel();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    void cancel(){
        mLtFrame.setVisibility(View.GONE);
        mTvSelectCount.setText(Html.fromHtml("共选择<font color='#ea7805'><big>" + 0 + "</big></font>条数据"));
        NotificationCenter.defaultCenter.postNotification(NotifyKey.CANCEL_KEY);
        clickLongBooo = false;
        mCbSelectAll.setChecked(false);
    }
}
