package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.Interaction.OnMultiClickListener;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.MaterialsInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Cc：在线申请
 * 文 件 名：DeclareNoticeActivity
 * 描   述：个人、企业办事申报须知
 * 作   者：Wangzheng
 * 时   间：2018-6-12 10:53:06
 * 版   权：v1.0
 */
public class DeclareNoticeActivity extends BaseActivity implements View.OnClickListener {

    private static final int GET_ITEM_WORK_INFO_CODE = 0;
    private static final int READER_ITEM_CODE = 1;
    private ImageView iv_progress_bar;
    private TextView tv_item_name, tv_item_code;
    private TextView tv_transact_condition;
    private NoScrollListView lv_transact_materials;
    private CheckBox cb_agree;
    private Button btn_next;
    //  type类型1.个人办事  type类型2.企业办事
    private String type;
    private String itemId, itemName, itemCode;
    private String proKeyId;
    private String dynamicForm, fileData;
    private String position;
    private String companyId;
    private String companyAdress;
    private CommonAdapter<MaterialsInfo> adapter;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        itemId = getIntent().getStringExtra("item_id");
        proKeyId = getIntent().getStringExtra("proKeyId");
        dynamicForm = getIntent().getStringExtra("dynamicForm");
        fileData = getIntent().getStringExtra("fileData");
        position = getIntent().getStringExtra("position");
        companyId = getIntent().getStringExtra("companyId");
        companyAdress = getIntent().getStringExtra("companyAdress");
        i(this+"-companyAdress:"+companyAdress);
        initView();
        initData();
        if ("0".equals(position) || "1".equals(position)) {
            getItemWorkInfo();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_declare_notice;
    }

    private void initView() {
        initTitleBar(R.string.declare_notice, this, null);
        iv_progress_bar = (ImageView) findViewById(R.id.iv_progress_bar);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_item_code = (TextView) findViewById(R.id.tv_item_code);
        tv_transact_condition = (TextView) findViewById(R.id.tv_transact_condition);
        lv_transact_materials = (NoScrollListView) findViewById(R.id.lv_transact_materials);
        cb_agree = (CheckBox) findViewById(R.id.cb_agree);
        btn_next = (Button) findViewById(R.id.btn_next);
    }

    private void initData() {

        btn_next.setOnClickListener(new OnMultiClickListener() {
            @Override
            protected void onMultiClick(View v) {
                if (!cb_agree.isChecked()) {
                    DialogUtils.showToast(DeclareNoticeActivity.this, "请同意并阅读该申报须知");
                    return;
                }
                Intent intent = new Intent(DeclareNoticeActivity.this, BasicInformationActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("item_id", itemId);
                intent.putExtra("proKeyId", proKeyId);
                intent.putExtra("dynamicForm", dynamicForm);
                intent.putExtra("fileData", fileData);
                intent.putExtra("position", position);
                intent.putExtra("companyId", companyId);
                intent.putExtra("companyAdress", companyAdress);
                startActivity(intent);
            }
        });
        cb_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_agree.setChecked(true);
                } else {
                    cb_agree.setChecked(false);
                }
            }
        });
    }

    /**
     * 获取事项浏览接口
     */
    private void readerItem() {
        ApiRequest request = OAInterface.readerItem(itemId, type);
        invoke.invoke(request, callBack, READER_ITEM_CODE);
    }

    /**
     * 获取申报须知事项信息
     */
    private void getItemWorkInfo() {
        ApiRequest request = OAInterface.getItemWorkInfo(itemId);
        invoke.invokeWidthDialog(request, callBack, GET_ITEM_WORK_INFO_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == GET_ITEM_WORK_INFO_CODE) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    parseData(result);
                    readerItem();
                }
            } else {
                DialogUtils.showToast(DeclareNoticeActivity.this, message);
            }
        }

    };

    /**
     * 解析申报须知事项信息数据
     *
     * @param result
     */
    private void parseData(ResultItem result) {
        if (null == result) {
            return;
        }
        itemId = result.getString("ITEMID");
        itemName = result.getString("ITEMNAME");
        itemCode = result.getString("SXBM");
        String transactCondition = result.getString("TRANSACT_CONDITION");
        dynamicForm = result.getString("DYNAMICFORM");
        tv_item_name.setText(itemName);
        tv_item_code.setText(itemCode);
        tv_transact_condition.setText(transactCondition);
        if ("0".equals(dynamicForm)) {
            iv_progress_bar.setImageResource(R.drawable.per_circle_one_1);
        } else {
            iv_progress_bar.setImageResource(R.drawable.per_circle_one);
        }
        List<ResultItem> items = result.getItems("FILELIST");
        List<MaterialsInfo> materialInfoList = new ArrayList<>();
        if (BeanUtils.isEmpty(items)) {
            return;
        }
        for (ResultItem item : items) {
            index++;
            MaterialsInfo materials = new MaterialsInfo();
            materials.setMaterialName(index + "、" + item.getString("CLNAME"));
            materials.setMaterialId(item.getString("FILEID"));
            materialInfoList.add(materials);
        }
        if (null == adapter) {
            adapter = new CommonAdapter<MaterialsInfo>(DeclareNoticeActivity.this, materialInfoList, R.layout.materials_list_item) {

                @Override
                public void convert(ViewHolder holder, MaterialsInfo item) {
                    holder.setTextView(R.id.tv_material_name, item.getMaterialName());
                }
            };
            lv_transact_materials.setAdapter(adapter);
        } else {
            adapter.setData(materialInfoList);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                DeclareNoticeActivity.this.finish();
                break;
        }
    }
}
