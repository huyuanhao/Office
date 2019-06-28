package com.powerrich.office.oa.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.MaterialsAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.BookingDetailBean;
import com.powerrich.office.oa.bean.MaterialsInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约详情
 */
public class OnlineBookingDetailActivity extends BaseActivity implements View.OnClickListener{
    /** 获取材料列表*/
    private final int GET_ITEM_REQ = 000;
    /** 根据id查询预约具体的信息详情*/
    private final int GET_MYAPPOINTMENTBYID = 111;
    /** 取消已经预约事项（在预约时间24h内不可取消）*/
    private final int CANCEL_APPOINTMENTTIMEITEM = 222;
    private NoScrollListView lv_material_form;
    private String itemId;
    private String a_id;
    private BookingDetailBean bookingDetailBean;
    private TextView tv_yyh;
    private TextView tv_date;
    private TextView tv_booking_name;
    private TextView tv_item_name;
    private TextView tv_item_detail;
    private Button cancel;
    private MaterialsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_online_booking_detail);
        itemId = getIntent().getStringExtra("itemId");
        a_id = getIntent().getStringExtra("a_id");//从我的预约列表传过来的
        bookingDetailBean = (BookingDetailBean) getIntent().getSerializableExtra("BookingDetailBean");
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_online_booking_detail;
    }

    private void initView() {
        if (BeanUtils.isEmptyStr(a_id)) {
            initTitleBar("预约详情", this, this);
        } else {
            initTitleBar("预约详情", this, null);
            cancel = (Button) findViewById(R.id.cancel);
        }
        tv_yyh = (TextView) findViewById(R.id.tv_yyh);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_booking_name = (TextView) findViewById(R.id.tv_booking_name);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_item_detail = (TextView) findViewById(R.id.tv_item_detail);
        lv_material_form = (NoScrollListView) findViewById(R.id.lv_material_form);
        findViewById(R.id.tv_to_guide).setOnClickListener(this);
        if (BeanUtils.isEmptyStr(a_id)) {
            initData();
        } else {
            getMyAppointmentById();
        }
    }

    private void initData() {
        if (!BeanUtils.isEmpty(bookingDetailBean)) {
            String tel = BeanUtils.isEmptyStr(bookingDetailBean.getZxmkzxdh()) ? "" : "(咨询电话:" + bookingDetailBean.getZxmkzxdh() + ")";
            tv_yyh.setText("预约号:" + bookingDetailBean.getOrder_no());
            tv_date.setText(bookingDetailBean.getOrder_date());
            tv_booking_name.setText("尊敬的" + bookingDetailBean.getOrder_name() + "(先生/女士):");
            String itemNameText = "&#8195;&#8195;您申请的<font color='red'>【" + bookingDetailBean.getOrder_item() +"】</font>事项已经预约成功！";
            tv_item_name.setText(Html.fromHtml(itemNameText));
            String detailText = "&#8195;&#8195;请您提前准备好相关材料(详见下表材料清单及办事指南详情)并于 <font color='red'>" +
                    bookingDetailBean.getOrder_date() + "</font> 前往 <font color='red'>" + bookingDetailBean.getBlwindow()+ "</font>" + tel + "办理。";
            tv_item_detail.setText(Html.fromHtml(detailText));
            if ("1".equals(bookingDetailBean.getOrder_state())) {//预约状态(1:预约中，2:取消预约，3：已完成)
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(this);
            } else if ("2".equals(bookingDetailBean.getOrder_state())) {
                cancel.setVisibility(View.VISIBLE);
                cancel.setText("已取消");
                cancel.setTextColor(getResources().getColor(R.color.gray));
                cancel.setEnabled(false);
            } else if ("3".equals(bookingDetailBean.getOrder_state())) {
                cancel.setVisibility(View.GONE);
            }
        }
        if (!BeanUtils.isEmptyStr(itemId)) {
            getDetail();
        }
    }

    /**
     * 列表详情请求
     */
    private void getDetail() {
        ApiRequest request = OAInterface.getItem(itemId);
        invoke.invokeWidthDialog(request, callBack, GET_ITEM_REQ);
    }
    /**
     * 根据id查询预约具体的信息详情
     */
    private void getMyAppointmentById() {
        ApiRequest request = OAInterface.getMyAppointmentById(a_id);
        invoke.invoke(request, callBack, GET_MYAPPOINTMENTBYID);
    }
    /**
     * 取消已经预约事项（在预约时间24h内不可取消）
     */
    private void cancelAppointmentTimeItem(String cancelReason) {
        ApiRequest request = OAInterface.cancelAppointmentTimeItem(a_id, cancelReason);
        invoke.invoke(request, callBack, CANCEL_APPOINTMENTTIMEITEM);
    }


    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            if (!BeanUtils.isEmpty(response)) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String code = item.getString("code");
                String message = item.getString("message");
                if (Constants.SUCCESS_CODE.equals(code)) {
                    if (what == GET_ITEM_REQ) {
                        List<ResultItem> items = item.getItems("FILE_DATA");
                        showMaterial(items);
                    } else if (what == GET_MYAPPOINTMENTBYID) {
                        ResultItem data = (ResultItem) item.get("DATA");
                        if (!BeanUtils.isEmpty(data)) {
                            bookingDetailBean = new BookingDetailBean();
                            bookingDetailBean.setOrder_no(data.getString("ORDER_NO"));
                            bookingDetailBean.setOrder_date(data.getString("ORDER_DATE"));
                            bookingDetailBean.setOrder_name(data.getString("REAL_NAME"));
                            bookingDetailBean.setBlwindow(data.getString("DEPTNAME"));
                            bookingDetailBean.setOrder_item(data.getString("ITEMNAME"));
                            bookingDetailBean.setOrder_state(data.getString("ORDER_STATE"));
                            itemId = data.getString("ITEMID");
                            initData();
                        }
                    } else if (what == CANCEL_APPOINTMENTTIMEITEM) {
                        DialogUtils.showToast(OnlineBookingDetailActivity.this, message);
                        OnlineBookingDetailActivity.this.finish();
                    }

                } else {
                    DialogUtils.showToast(OnlineBookingDetailActivity.this, message);
                }
            }
        }
    };

    private void showMaterial(List<ResultItem> items) {
        List<MaterialsInfo> materialsInfoList = new ArrayList<>();
        if (!BeanUtils.isEmpty(items)) {
            View view = getLayoutInflater().inflate(R.layout.booking_detail_list_header, null);
            AutoUtils.auto(view);
            lv_material_form.addHeaderView(view);
            for (ResultItem resultItem : items) {
                String materialNecessity = resultItem.getString("BYX");
//			String materialId = resultItem.getString("CL_ID");
                String materialDescribe = resultItem.getString("CLDESCRIBE");
                String materialFormat = resultItem.getString("CLFORMAT");
                String materialCopies = resultItem.getString("CLFS");
                String materialName = resultItem.getString("CLNAME");
                String materialSize = resultItem.getString("CLSIZE");
                String materialForm = resultItem.getString("CLXS");
                String materialType = resultItem.getString("TYPE");
                MaterialsInfo materialsInfo = new MaterialsInfo();
                materialsInfo.setMaterialNecessity(materialNecessity);
//			materialsInfo.setMaterialId(materialId);
                materialsInfo.setMaterialDescribe(materialDescribe);
                materialsInfo.setMaterialFormat(materialFormat);
                materialsInfo.setMaterialName(materialName);
                materialsInfo.setMaterialCopies(materialCopies);
                materialsInfo.setMaterialSize(materialSize);
                materialsInfo.setMaterialForm(materialForm);
                materialsInfo.setMaterialType(materialType);
                materialsInfoList.add(materialsInfo);
            }
        }
        if (null == adapter) {
            adapter = new MaterialsAdapter(OnlineBookingDetailActivity.this, materialsInfoList, R.layout.material_form_item);
            lv_material_form.setAdapter(adapter);
        } else {
            adapter.setData(materialsInfoList);
        }
    }

    /**
     * 返回主界面
     */
    private void toMain() {
        Intent intent= new Intent(OnlineBookingDetailActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }


    // 可输入文本的提示框
    private void showCauseDialog() {
        View view = LayoutInflater.from(OnlineBookingDetailActivity.this).inflate(R.layout.cancel_dialog, null);
        AutoUtils.auto(view);
        final EditText edit = (EditText) view.findViewById(R.id.edit);
        edit.clearFocus();
        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cancelAppointmentTimeItem(edit.getText().toString().trim());
                        hideKeyboard(edit);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hideKeyboard(edit);
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();

    }


    private void hideKeyboard(EditText edit) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(),0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.btn_top_right:
                toMain();
                break;
            case R.id.cancel:
                showCauseDialog();
                break;
            case R.id.tv_to_guide:
                Intent intent = new Intent(OnlineBookingDetailActivity.this, WorkGuideNewActivity.class);
                intent.putExtra("isAppointed", true);
                intent.putExtra("item_id", itemId);
                intent.putExtra("item_name", bookingDetailBean.getOrder_item());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
