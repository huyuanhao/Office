package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.DynamicBean;
import com.powerrich.office.oa.bean.DynamicInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.dynamic.DynamicListener;
import com.powerrich.office.oa.dynamic.EditBox;
import com.powerrich.office.oa.dynamic.MultiCheckBox;
import com.powerrich.office.oa.dynamic.SelectBox;
import com.powerrich.office.oa.dynamic.SpinnerBox;
import com.powerrich.office.oa.dynamic.ViewUtils;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：BaseInformationActivity
 * 描   述：动态表单基本信息界面
 * 作   者：Wangzheng
 * 时   间：2017/11/27
 * 版   权：v1.0
 */
public class BaseInformationActivity extends BaseActivity implements OnClickListener {

    private static final int QUERY_DYNAMIC_FORM_REQ = 0;
    private static final int SAVE_DYNAMIC_FORM_REQ = 1;
    private static final int SUBMIT_ITEM_AUDIT_REQ = 2;
    private static final int QUERY_DYNAMIC_FORM_INFO_REQ = 3;
    private ImageView iv_progress_bar;
    private TextView tv_item_name, tv_item_code;
    private LinearLayout ll_dynamic;
    private Button btn_next;
    private String itemId, itemName, itemCode;
    private String proKeyId;
    private String dynamicForm, fileData;
    private String position;
    private List<DynamicInfo> dynamicInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(R.string.title_activity_base_information, this, null);
        //获取上个界面传递的数据
        itemId = getIntent().getStringExtra("itemId");
        itemName = getIntent().getStringExtra("itemName");
        itemCode = getIntent().getStringExtra("itemCode");
        proKeyId = getIntent().getStringExtra("proKeyId");
        dynamicForm = getIntent().getStringExtra("dynamicForm");
        fileData = getIntent().getStringExtra("fileData");
        position = getIntent().getStringExtra("position");
        //初始化控件
        initView();
        initData();
        if (!BeanUtils.isEmpty(dynamicInfoList)) {
            dynamicInfoList.clear();
        }
        if (!"0".equals(position)) {
            queryItemDynamicFormInfo();
        } else {
            queryItemDynamicForm();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_base_information;
    }

    private void initView() {
        iv_progress_bar = (ImageView) findViewById(R.id.iv_progress_bar);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_item_code = (TextView) findViewById(R.id.tv_item_code);
        ll_dynamic = (LinearLayout) findViewById(R.id.ll_dynamic);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        if ("1".equals(dynamicForm)) {
            iv_progress_bar.setImageResource(R.drawable.per_circle_three);
        }
    }

    private void initData() {
        tv_item_name.setText(itemName);
        tv_item_code.setText(itemCode);
    }

    /**
     * 动态加载事项表单请求
     */
    private void queryItemDynamicForm() {
        ApiRequest request = OAInterface.queryItemDynamicForm(itemId, proKeyId);
        invoke.invokeWidthDialog(request, callBack, QUERY_DYNAMIC_FORM_REQ);
    }

    /**
     * 办件详情-查看该笔件动态表单请求
     */
    private void queryItemDynamicFormInfo() {
        ApiRequest request = OAInterface.queryItemDynamicFormInfo(proKeyId);
        invoke.invokeWidthDialog(request, callBack, QUERY_DYNAMIC_FORM_INFO_REQ);
    }

    /**
     * 网上办事-事项申报提交审核请求
     */
    private void submitItemAudit() {
        ApiRequest request = OAInterface.submitItemAudit(itemId, proKeyId);
        invoke.invokeWidthDialog(request, callBack, SUBMIT_ITEM_AUDIT_REQ);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == QUERY_DYNAMIC_FORM_REQ || what == QUERY_DYNAMIC_FORM_INFO_REQ) {
                    List<ResultItem> data = item.getItems("DATA");
                    parseData(data);
                } else if (what == SAVE_DYNAMIC_FORM_REQ) {
                    if ("1".equals(fileData)) {
                        //跳转到相关材料界面
                        Intent intent = new Intent(BaseInformationActivity.this, DeclareMaterialsActivity.class);
                        intent.putExtra("itemId", itemId);
                        intent.putExtra("itemName", itemName);
                        intent.putExtra("itemCode", itemCode);
                        intent.putExtra("proKeyId", proKeyId);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    } else {
                        submitItemAudit();
                    }
                } else if (what == SUBMIT_ITEM_AUDIT_REQ) {
                    ResultItem data = (ResultItem) item.get("DATA");
                    String trackId = data.getString("TRACKID");
                    if ("0".equals(fileData)) {
                        Intent intent = new Intent(BaseInformationActivity.this, DeclareNotifyActivity.class);
                        intent.putExtra("itemName", itemName);
                        intent.putExtra("itemCode", itemCode);
                        intent.putExtra("proKeyId", proKeyId);
                        intent.putExtra("trackId", trackId);
                        startActivity(intent);
                    }
                }

            } else {
                DialogUtils.showToast(BaseInformationActivity.this, message);
            }
        }
    };

    /**
     * 动态加载事项表单数据解析
     *
     * @param items
     */
    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            return;
        }
        TextView tempTxt = new TextView(this);
        tempTxt.setTextSize(ViewUtils.TEXT_SIZE);
        int maxWidth = 0;
        for (ResultItem result : items) {
            String defaultValue = !"0".equals(position) ? result.getString("field_value") : result.getString("default_value");
            String fieldCName = result.getString("field_cname");
            String fieldDesc = result.getString("field_desc", "");
            String fieldEName = result.getString("field_ename");
            String fieldLength = result.getString("field_length", "");
            String fieldSort = result.getString("field_sort", "");
            String fieldType = result.getString("field_type", "");
            String isNull = result.getString("is_null", "");
            List<ResultItem> sources = result.getItems("source");
            String styleId = result.getString("style_id");
            String tableEName = result.getString("table_ename");
            List<DynamicInfo.SourceInfo> sourceInfoList = new ArrayList<>();
            if (!BeanUtils.isEmpty(sources)) {
                for (ResultItem resultItem : sources) {
                    DynamicInfo.SourceInfo sourceInfo = new DynamicInfo.SourceInfo();
                    String id = resultItem.getString("s_id");
                    String val = resultItem.getString("s_val");
                    sourceInfo.setId(id);
                    sourceInfo.setVal(val);
                    sourceInfoList.add(sourceInfo);
                }
            }
            DynamicInfo dynamicInfo = new DynamicInfo();
            dynamicInfo.setFieldCName(fieldCName);
            dynamicInfo.setDefaultValue(defaultValue);
            dynamicInfo.setFieldDesc(fieldDesc);
            dynamicInfo.setFieldEName(fieldEName);
            dynamicInfo.setFieldLength(fieldLength);
            dynamicInfo.setFieldSort(fieldSort);
            dynamicInfo.setFieldType(fieldType);
            dynamicInfo.setIsNull(isNull);
            dynamicInfo.setTableEName(tableEName);
            dynamicInfo.setStyleId(styleId);
            dynamicInfo.setSourceInfoList(sourceInfoList);
            dynamicInfoList.add(dynamicInfo);
            if (styleId.equals("002003") || BeanUtils.isNullOrEmpty(styleId)) {
                continue;
            }
            int width = (int) ViewUtils.getCharacterWidth(dynamicInfo.getFieldCName(), tempTxt);
            LogUtils.e("BaseInformation", "width:" + width);
            maxWidth = maxWidth > width ? maxWidth : width;
        }
        int textShowWidth = getWindowManager().getDefaultDisplay().getWidth() - ll_dynamic.getPaddingLeft() - ll_dynamic.getPaddingRight();
        textShowWidth /= 3;
        maxWidth = Math.min(maxWidth, textShowWidth);
        LogUtils.e("BaseInformation", "maxWidth:" + maxWidth);
        for (int i = 0; i < dynamicInfoList.size(); i++) {
            ArrayList<String> checkboxList = new ArrayList<>();
            String styleId = dynamicInfoList.get(i).getStyleId();
            if (styleId.startsWith("001") || styleId.equals("002001") || styleId.equals("002002") || styleId.equals("002004") || styleId.startsWith("003")) {
                EditBox testEdit = new EditBox(this);
                testEdit.createView(maxWidth, dynamicInfoList.get(i), true);
                ll_dynamic.addView(testEdit);
            } else if (styleId.equals("002006") || styleId.equals("004001") || styleId.equals("004002")) {
                SpinnerBox spinnerBox = new SpinnerBox(this);
                spinnerBox.createView(maxWidth, dynamicInfoList.get(i), true);
                ll_dynamic.addView(spinnerBox);
            } else if (styleId.equals("005002") || styleId.equals("005004")) {
                SelectBox selectBox = new SelectBox(this);
                selectBox.createView(maxWidth, dynamicInfoList.get(i), true);
                ll_dynamic.addView(selectBox);
            } else if (styleId.equals("005003")) {
                List<DynamicInfo.SourceInfo> sourceList = dynamicInfoList.get(i).getSourceInfoList();
                if (!BeanUtils.isEmpty(sourceList)) {
                    for (int j = 0; j < sourceList.size(); j++) {
                        checkboxList.add(sourceList.get(j).getVal());
                    }
                }
                MultiCheckBox check = new MultiCheckBox(this);
                check.createView(maxWidth, dynamicInfoList.get(i), checkboxList, true);
                ll_dynamic.addView(check);
            } /*else if (styleId.equals("005002")) {
                RadioBox radioBox = new RadioBox(this);
                radioBox.createView(maxWidth, 0, dynamicInfoList.get(i));
                ll_dynamic.addView(radioBox);
            }*/
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.btn_next:
                getDynamicValues();
                break;
            default:
                break;
        }
    }

    /**
     * 保存动态表单的信息请求
     */
    private void getDynamicValues() {
        DynamicBean bean = new DynamicBean();
        if (!BeanUtils.isEmpty(dynamicInfoList)) {
            bean.setTable(dynamicInfoList.get(0).getTableEName());
        }
        bean.setItemId(itemId);
        bean.setProKeyId(proKeyId);
        bean.addParam(new DynamicBean.ParamMap("id", proKeyId));
        for (int i = 0; i < ll_dynamic.getChildCount(); i++) {
            View childView = ll_dynamic.getChildAt(i);
            DynamicListener listener = null;
            if (childView instanceof DynamicListener) {
                listener = (DynamicListener) childView;
            }
            if (null != listener) {
                DynamicBean.ParamMap param = listener.getValue();
                if (null == param) {
                    return;
                }
                bean.addParam(param);
            }
        }
        LogUtils.i("params=" + bean.getParam());
        invoke.invokeWidthDialog(OAInterface.saveItemDynamicForm(bean.getParam()), callBack, SAVE_DYNAMIC_FORM_REQ);
    }
}
