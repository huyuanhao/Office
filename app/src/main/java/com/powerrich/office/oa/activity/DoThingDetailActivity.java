package com.powerrich.office.oa.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.DoThingBean;
import com.powerrich.office.oa.bean.DynamicInfo;
import com.powerrich.office.oa.bean.FileListBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.dynamic.EditBox;
import com.powerrich.office.oa.dynamic.MultiCheckBox;
import com.powerrich.office.oa.dynamic.SelectBox;
import com.powerrich.office.oa.dynamic.SpinnerBox;
import com.powerrich.office.oa.dynamic.ViewUtils;
import com.powerrich.office.oa.enums.EvaluationType;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.receiver.IntentReceiver;
import com.powerrich.office.oa.tools.AndroidFileUtil;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.DownloadUtils;
import com.powerrich.office.oa.tools.LogUtils;
import com.powerrich.office.oa.tools.NetWorkUtil;
import com.powerrich.office.oa.tools.StorageUtil;
import com.powerrich.office.oa.tools.progressutils.listener.impl.UIProgressListener;
import com.powerrich.office.oa.view.CircleProgressBar;
import com.powerrich.office.oa.view.EvaluationDialog;
import com.powerrich.office.oa.view.NoScrollListView;
import com.powerrich.office.oa.view.ProgressBarDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：DoThingDetailActivity
 * 描   述：办件详情界面
 * 作   者：Wangzheng
 * 时   间：2018-8-27 14:20:41
 * 版   权：v2.0
 */
public class DoThingDetailActivity extends BaseActivity implements View.OnClickListener, DownloadUtils.InvokeData, IntentReceiver.NetEvent, EvaluationDialog.ICallback {

    private static final int CODE_GET_DETAIL = 0;
    private static final int CODE_GET_RECEIPT_INFO = 1;
    private static final int CODE_GET_DYNAMIC_FORM_INFO = 2;
    private static final int CODE_GET_FILE_LIST = 3;

    private TextView tv_current_progress, tv_reason, tv_item_name, tv_item_code, tv_apply_time, tv_declare_number, tv_proposer_name, tv_lxr_name, tv_lxr_type,tv_lxr_number, tv_certificate_type, tv_id_number, tv_phone, tv_address, tv_agent_name, tv_agent_certificate_type, tv_agent_id_number, tv_agent_phone;
    private LinearLayout ll_form;
    private LinearLayout ll_form_title, ll_materials_title;
    private NoScrollListView lv_materials;
    private LinearLayout ll_send_address;
    private TextView tv_send_address;
    private TextView tv_continue;

    private CommonAdapter<FileListBean> adapter;
    private List<DynamicInfo> dynamicInfoList = new ArrayList<>();
    private List<FileListBean> fileList = new ArrayList<>();
    private IntentReceiver receiver;
    private ProgressBarDialog progressBarDialog;
    private CircleProgressBar circleProgressBar;
    private long fileSize;
    private int mPosition;
    private DoThingBean mDoThingBean;
    private String fileName;
    private String proKeyId;
    private boolean isEvaluate;
    private String processState;
    private String isEms;
    private EvaluationDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(R.string.do_thing_detail, this, null);
        proKeyId = getIntent().getStringExtra("proKeyId");
        isEvaluate = getIntent().getBooleanExtra("isEvaluate", false);
        initView();
        initData();
        getDoThingDetail();
        receiver = new IntentReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_do_thing_detail;
    }

    private void initView() {
        tv_current_progress = (TextView) findViewById(R.id.tv_current_progress);
        tv_reason = (TextView) findViewById(R.id.tv_reason);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_item_code = (TextView) findViewById(R.id.tv_item_code);
        tv_apply_time = (TextView) findViewById(R.id.tv_apply_time);
        tv_declare_number = (TextView) findViewById(R.id.tv_declare_number);
        tv_proposer_name = (TextView) findViewById(R.id.tv_proposer_name);

        tv_lxr_name = (TextView) findViewById(R.id.tv_lxr_name);
        tv_lxr_type = (TextView) findViewById(R.id.tv_lxr_type);
        tv_lxr_number = (TextView) findViewById(R.id.tv_lxr_number);



        tv_certificate_type = (TextView) findViewById(R.id.tv_certificate_type);
        tv_id_number = (TextView) findViewById(R.id.tv_id_number);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_agent_name = (TextView) findViewById(R.id.tv_agent_name);
        tv_agent_certificate_type = (TextView) findViewById(R.id.tv_agent_certificate_type);
        tv_agent_id_number = (TextView) findViewById(R.id.tv_agent_id_number);
        tv_agent_phone = (TextView) findViewById(R.id.tv_agent_phone);
        ll_form = (LinearLayout) findViewById(R.id.ll_form);
        ll_form_title = (LinearLayout) findViewById(R.id.ll_form_title);
        ll_materials_title = (LinearLayout) findViewById(R.id.ll_materials_title);
        lv_materials = (NoScrollListView) findViewById(R.id.lv_materials);
        ll_send_address = (LinearLayout) findViewById(R.id.ll_send_address);
        tv_send_address = (TextView) findViewById(R.id.tv_send_address);
        tv_continue = (TextView) findViewById(R.id.tv_continue);
    }

    private void initData() {
        tv_declare_number.setOnClickListener(this);
        tv_continue.setOnClickListener(this);
        lv_materials.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                fileName = fileList.get(position).getFileName();
                fileSize = fileList.get(position).getByteSize();
                String filePath = fileList.get(position).getFilePath();
                String hdfsFileName = fileList.get(position).getHdfsFileName();
                if (AndroidFileUtil.fileIsExists(fileName)) {
                    fileList.get(position).setFileState("1");
                    if ("1".equals(fileList.get(position).getFileState())) {
                        openFile();
                    }
                } else {
                    final String url = "http://218.87.176.156:80/platform/DownFileServlet?" + "type=1" + "&DOWNPATH=" + filePath + "&HDFSFILENAME=" + hdfsFileName + "&FILENAME=" + fileName;
                    doPermissionRW("存储", new PermissionCallBack() {
                        @Override
                        public void accept() {
                            if (!NetWorkUtil.checkedNetWork(DoThingDetailActivity.this)) {
                                DialogUtils.showToast(DoThingDetailActivity.this, "请检查网络连接是否正常！");
                                return;
                            }
                            if (!StorageUtil.checkFreeSpace()) {
                                DialogUtils.showToast(DoThingDetailActivity.this, "请检查SD卡是否有足够的空间！");
                                return;
                            }
                            DownloadUtils.downloadFile(uiProgressRequestListener, DoThingDetailActivity.this, url, fileName);
                            showProgressBarDialog();

                        }
                    });

                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //长按删除已下载的本地附件
        /*lv_materials.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String status = fileList.get(position).getFileState();
                if ("1".equals(status)) {
                    MyDialog.showDialog(DoThingDetailActivity.this, "提示", "确定删除吗！", new MyDialog.InterfaceClick() {
                        @Override
                        public void click() {
                            try {
                                File file = new File(Environment.getExternalStorageDirectory() + "/download/" + fileName);
                                file.delete();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // 通知adapter 更新
                            fileList.get(position).setFileState("0");
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                return true;
            }
        });*/
    }

    /**
     * 办件详情请求
     */
    private void getDoThingDetail() {
        ApiRequest request = OAInterface.doFileDetail(proKeyId);
        invoke.invokeWidthDialog(request, callBack, CODE_GET_DETAIL);
    }

    /**
     * 审批回执（批注）信息请求
     */
    private void getReceiptInfo(String trackingNumber) {
        ApiRequest request = OAInterface.getReceiptInfo(trackingNumber);
        invoke.invoke(request, callBack, CODE_GET_RECEIPT_INFO);
    }

    /**
     * 办件详情-查看该笔件动态表单请求
     */
    private void queryItemDynamicFormInfo() {
        ApiRequest request = OAInterface.queryItemDynamicFormInfo(proKeyId);
        invoke.invoke(request, callBack, CODE_GET_DYNAMIC_FORM_INFO);
    }

    /**
     * 办件详情-查看该笔件已上传的附件列表请求
     */
    private void getBusinessFileList() {
        ApiRequest request = OAInterface.getBusinessFileList(proKeyId);
        invoke.invoke(request, callBack, CODE_GET_FILE_LIST);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (CODE_GET_DETAIL == what) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    mDoThingBean = gson.fromJson(jsonStr, DoThingBean.class);
                    parseData(mDoThingBean);
                    getReceiptInfo(mDoThingBean.getDATA().getTRACKINGNUMBER());
                } else if (CODE_GET_RECEIPT_INFO == what) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    if (BeanUtils.isEmptyStr(mDoThingBean.getDATA().getTRACKINGNUMBER())) {
                        tv_reason.setVisibility(View.GONE);
                    } else {
                        String reason = result.getString("REASON");
                        if (!BeanUtils.isEmptyStr(reason)) {
                            reason = "说明：" + reason;
                            tv_reason.setVisibility(View.VISIBLE);
                            tv_reason.setText(reason);
                        } else {
                            tv_reason.setVisibility(View.GONE);
                        }
                    }
                    queryItemDynamicFormInfo();
                } else if (CODE_GET_DYNAMIC_FORM_INFO == what) {
                    List<ResultItem> items = item.getItems("DATA");
                    parseDynamicFormInfo(items);
                    getBusinessFileList();
                } else if (CODE_GET_FILE_LIST == what) {
                    List<ResultItem> items = item.getItems("DATA");
                    parseFileList(items);
                    if ("1".equals(processState) || "2".equals(processState) || "4".equals(processState)) {
                        ll_send_address.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                DialogUtils.showToast(DoThingDetailActivity.this, message);
            }
        }

    };

    /**
     * 解析办件详情-查看该办件基本信息数据
     *
     * @param bean
     */
    private void parseData(DoThingBean bean) {
        processState = bean.getDATA().getPROCESS_STATE();
        tv_current_progress.setText(BeanUtils.getProcessState(DoThingDetailActivity.this, processState));
        tv_item_name.setText(bean.getDATA().getSXMC());
        tv_item_code.setText(bean.getDATA().getSXBM());
        if (bean.getDATA().getSQSJ().contains(".")) {
            String[] split = bean.getDATA().getSQSJ().split("\\.");
            tv_apply_time.setText(split[0]);
        } else {
            tv_apply_time.setText(bean.getDATA().getSQSJ());
        }
        tv_declare_number.setText(bean.getDATA().getTRACKINGNUMBER());
        //申请人名称
        tv_proposer_name.setText(bean.getDATA().getNAME());
        //申请人证件号码
        tv_id_number.setText(bean.getDATA().getZJHM());
        //代理人名称
        tv_lxr_name.setText(bean.getDATA().getPERSON_NAME());

        //联系人证件类型
        if ("0".equals(bean.getDATA().getPERSON_TYPE())) {
            tv_lxr_type.setText(getString(R.string.id_card));
        } else {
            tv_lxr_type.setText(getString(R.string.unified_social_credit_code));
        }
        tv_lxr_number.setText(bean.getDATA().getPERSON_ID());

//        if ("0".equals(bean.getDATA().getPERSON_TYPE())) {
//            tv_certificate_type.setText(getString(R.string.id_card));
//        } else {
//            tv_certificate_type.setText(getString(R.string.unified_social_credit_code));
//        }


        //联系人手机号码
        tv_phone.setText(bean.getDATA().getPERSON_PHONE());
        //通讯地址
        tv_address.setText(bean.getDATA().getPERSON_ADDRESS());
        tv_agent_name.setText(bean.getDATA().getPERSON_NAME());
        if ("0".equals(bean.getDATA().getPERSON_TYPE())) {
            tv_agent_certificate_type.setText(getString(R.string.id_card));
        } else {
            tv_agent_certificate_type.setText(getString(R.string.unified_social_credit_code));
        }
        tv_agent_id_number.setText(bean.getDATA().getPERSON_ID());
        tv_agent_phone.setText(bean.getDATA().getPERSON_PHONE());
        isEms = bean.getDATA().getISEMS();
        if ("0".equals(processState) || "5".equals(processState) || "7".equals(processState)) {
            tv_continue.setText("继续办理");
            tv_continue.setVisibility(View.VISIBLE);
        } else if ("1".equals(processState) || "2".equals(processState) || "4".equals(processState)) {
            if ("0".equals(isEms)) {
                tv_continue.setText("快递下单");
            } else {
                tv_continue.setText("查看物流");
            }
            tv_continue.setVisibility(View.VISIBLE);
        } else if ("3".equals(processState)) {
            tv_continue.setText(isEvaluate ? "查看评价" : "评价");
            tv_continue.setVisibility(View.VISIBLE);
        } else {
            tv_continue.setVisibility(View.GONE);
        }
        String sendAddress = bean.getDATA().getDELIVERY_ADDRESS().getZXCKDZ() + "    " + bean.getDATA().getDELIVERY_ADDRESS().getZXMKZXDH() + "    " + bean.getDATA().getDELIVERY_ADDRESS().getZXCKMC();
        tv_send_address.setText(sendAddress);
    }

    /**
     * 解析办件详情-查看该办件动态表单数据
     *
     * @param items
     */
    private void parseDynamicFormInfo(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            ll_form_title.setVisibility(View.GONE);
            return;
        }
        ll_form_title.setVisibility(View.VISIBLE);
        TextView tempTxt = new TextView(this);
        tempTxt.setTextSize(ViewUtils.TEXT_SIZE);
        int maxWidth = 0;
        for (ResultItem result : items) {
            String fieldValue = result.getString("field_value");
            String fieldCName = result.getString("field_cname");
            String fieldEName = result.getString("field_ename");
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
            dynamicInfo.setDefaultValue(fieldValue);
            dynamicInfo.setFieldEName(fieldEName);
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
        int textShowWidth = getWindowManager().getDefaultDisplay().getWidth() - ll_form.getPaddingLeft() - ll_form.getPaddingRight();
        textShowWidth /= 3;
        maxWidth = Math.min(maxWidth, textShowWidth);
        LogUtils.e("BaseInformation", "maxWidth:" + maxWidth);
        for (int i = 0; i < dynamicInfoList.size(); i++) {
            ArrayList<String> checkboxList = new ArrayList<>();
            String styleId = dynamicInfoList.get(i).getStyleId();
            if (styleId.startsWith("001") || styleId.equals("002001") || styleId.equals("002002") || styleId.equals("002004") || styleId.startsWith("003")) {
                EditBox testEdit = new EditBox(this);
                testEdit.createView(maxWidth, dynamicInfoList.get(i), false);
                ll_form.addView(testEdit);
            } else if (styleId.equals("002006") || styleId.equals("004001") || styleId.equals("004002")) {
                SpinnerBox spinnerBox = new SpinnerBox(this);
                spinnerBox.createView(maxWidth, dynamicInfoList.get(i), false);
                ll_form.addView(spinnerBox);
            } else if (styleId.equals("005002") || styleId.equals("005004")) {
                SelectBox selectBox = new SelectBox(this);
                selectBox.createView(maxWidth, dynamicInfoList.get(i), false);
                ll_form.addView(selectBox);
            } else if (styleId.equals("005003")) {
                List<DynamicInfo.SourceInfo> sourceList = dynamicInfoList.get(i).getSourceInfoList();
                String value = dynamicInfoList.get(i).getDefaultValue();
                if (!BeanUtils.isNullOrEmpty(value)) {
                    if (!value.contains(",")) {
                        if (!BeanUtils.isEmpty(sourceList)) {
                            for (int j = 0; j < sourceList.size(); j++) {
                                if (value.equals(sourceList.get(j).getId())) {
                                    checkboxList.add(sourceList.get(j).getVal());
                                }
                            }
                        }

                    } else {
                        String[] values = value.split(",");
                        for (int j = 0; j < values.length; j++) {
                            String str = values[j];
                            for (int k = 0; k < sourceList.size(); k++) {
                                if (str.equals(sourceList.get(k).getId())) {
                                    checkboxList.add(sourceList.get(k).getVal());
                                }
                            }
                        }
                    }
                }
                MultiCheckBox check = new MultiCheckBox(this);
                check.createView(maxWidth, dynamicInfoList.get(i), checkboxList, false);
                ll_form.addView(check);
                /*if (!BeanUtils.isEmpty(sourceList)) {
                    for (int j = 0; j < sourceList.size(); j++) {
						checkboxList.add(sourceList.get(j).getVal());
					}
				}
				MultiCheckBox check = new MultiCheckBox(this);
				check.createView(maxWidth, dynamicInfoList.get(i), checkboxList, false);
				ll_form.addView(check);*/
            } /*else if (styleId.equals("005002")) {
                RadioBox radioBox = new RadioBox(this);
                radioBox.createView(maxWidth, 0, dynamicInfoList.get(i));
                ll_dynamic.addView(radioBox);
            }*/
        }
    }

    /**
     * 解析办件详情-查看该办件已上传的附件列表数据
     *
     * @param items
     */
    private void parseFileList(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            ll_materials_title.setVisibility(View.GONE);
            return;
        }
        ll_materials_title.setVisibility(View.VISIBLE);
        for (ResultItem item : items) {
            FileListBean bean = new FileListBean();
            bean.setCompFileName(item.getString("COMP_FILE_NAME"));
            bean.setFileId(item.getString("FILEID"));
            bean.setFileName(item.getString("FILENAME"));
            bean.setFilePath(item.getString("FILEPATH"));
            bean.setFileSize(item.getString("FILESIZE"));
            bean.setByteSize(item.getString("FILESIZE"));
            bean.setHdfsFileName(item.getString("HDFSFILENAME"));
            bean.setProkeyId(item.getString("PROKEYID"));
            if (AndroidFileUtil.fileIsExists(item.getString("FILENAME"))) {
                //"1"为文件存在
                bean.setFileState("1");
            }
            fileList.add(bean);
        }
        if (null == adapter) {
            adapter = new CommonAdapter<FileListBean>(DoThingDetailActivity.this, fileList, R.layout.attachment_list_item) {
                @Override
                public void convert(ViewHolder holder, FileListBean item) {
                    holder.setTextView(R.id.tv_comp_file_name, item.getCompFileName());
                    holder.setTextView(R.id.tv_file_name, item.getFileName());
                    //"1"为文件存在，否则为不存在
                    if ("1".equals(item.getFileState())) {
                        holder.setImageResource(R.id.iv_download, R.drawable.fj_icon_05);
                    } else {
                        holder.setImageResource(R.id.iv_download, R.drawable.fj_icon_02);
                    }
                }
            };
            lv_materials.setAdapter(adapter);
        } else {
            adapter.setData(fileList);
        }
    }

    private void showProgressBarDialog() {
        progressBarDialog = ProgressBarDialog.createDialog(DoThingDetailActivity.this);
        circleProgressBar = (CircleProgressBar) progressBarDialog.findViewById(R.id.progressBar);
        circleProgressBar.setCurProgress(0);
        if (progressBarDialog != null) {
            progressBarDialog.show();
        }
    }

    //这个是ui线程回调，可直接操作UI
    final UIProgressListener uiProgressRequestListener = new UIProgressListener() {
        @Override
        public void onUIProgress(long bytesWrite, long contentLength, boolean done) {
            //ui层回调
            int progressSize = (int) ((100 * bytesWrite) / fileSize);
            circleProgressBar.setCurProgress(progressSize > 100 ? 100 : progressSize);
        }

        @Override
        public void onUIStart(long bytesWrite, long contentLength, boolean done) {
            super.onUIStart(bytesWrite, contentLength, done);
        }

        @Override
        public void onUIFinish(long bytesWrite, long contentLength, boolean done) {
            super.onUIFinish(bytesWrite, contentLength, done);
            DialogUtils.showToast(DoThingDetailActivity.this, "下载成功");
            //"1"为文件存在
            fileList.get(mPosition).setFileState("1");
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            if (progressBarDialog != null) {
                progressBarDialog.cancel();
            }
        }
    };

    private void openFile() {
        doPermissionRW("存储", new PermissionCallBack() {
            @Override
            public void accept() {
                File file = new File(Environment.getExternalStorageDirectory() + "/download/" + fileName);
                AndroidFileUtil.openFile(DoThingDetailActivity.this, file);
            }
        });
    }

    @Override
    public void success(String result) {

    }

    @Override
    public void failed() {
        if (progressBarDialog != null) {
            progressBarDialog.dismiss();
        }
        Toast.makeText(this, "下载失败,请重新下载", Toast.LENGTH_SHORT).show();
        String name = fileList.get(mPosition).getFileName();
        AndroidFileUtil.deleteFile(Environment.getExternalStorageDirectory() + "/download/" + name);
    }

    @Override
    public void onNetChange() {
        if (progressBarDialog != null) {
            progressBarDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                DoThingDetailActivity.this.finish();
                break;
            case R.id.tv_declare_number:
                String text = tv_declare_number.getText().toString();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(text);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(text, text);
                    clipboard.setPrimaryClip(clip);
                }
                if (!BeanUtils.isEmptyStr(text)) {
                    DialogUtils.showToast(DoThingDetailActivity.this, "申报编号复制成功！");
                }
                break;
            case R.id.tv_continue:
                if ("0".equals(processState) || "5".equals(processState) || "7".equals(processState)) {
                    if (!"0".equals(mDoThingBean.getDATA().getPOSITION())) {
                        Intent intent = new Intent(DoThingDetailActivity.this, BasicInformationActivity.class);
                        intent.putExtra("type", "0".equals(mDoThingBean.getDATA().getUSER_TYPE()) ?  Constants.PERSONAL_WORK_TYPE : Constants.COMPANY_WORK_TYPE);
                        intent.putExtra("item_id", mDoThingBean.getDATA().getSXID());
                        intent.putExtra("itemName", mDoThingBean.getDATA().getREGISTER_REMARK());
                        intent.putExtra("proKeyId", mDoThingBean.getDATA().getPROKEYID());
                        intent.putExtra("dynamicForm", mDoThingBean.getDATA().getDYNAMICFORM());
                        intent.putExtra("fileData", mDoThingBean.getDATA().getFILEDATA());
                        intent.putExtra("position", mDoThingBean.getDATA().getPOSITION());
                        startActivity(intent);
                    } else {
                        DialogUtils.showToast(DoThingDetailActivity.this, "该办件不能继续办理");
                    }
                } else if ("1".equals(processState) || "2".equals(processState) || "4".equals(processState)) {
                    if ("0".equals(isEms)) {
                        Intent intent = new Intent(DoThingDetailActivity.this, MaterialSendActivity.class);
                        intent.putExtra("itemName", mDoThingBean.getDATA().getREGISTER_REMARK());
                        intent.putExtra("proKeyId", mDoThingBean.getDATA().getPROKEYID());
                        intent.putExtra("addressBean", mDoThingBean.getDATA().getDELIVERY_ADDRESS());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(DoThingDetailActivity.this, LogisticsDetailActivity.class);
                        intent.putExtra("id", mDoThingBean.getDATA().getORDER_NUM());
                        startActivity(intent);
                    }

                } else if ("3".equals(processState)) {
                    if (dialog != null && dialog.isShowing()) {
                        return;
                    }
                    dialog = new EvaluationDialog(DoThingDetailActivity.this, mDoThingBean.getDATA().getPROKEYID(), isEvaluate ? EvaluationType.查看评价 : EvaluationType.去评价, this);
                    dialog.show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void success() {
        isEvaluate = true;
        tv_continue.setText("查看评价");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


}
