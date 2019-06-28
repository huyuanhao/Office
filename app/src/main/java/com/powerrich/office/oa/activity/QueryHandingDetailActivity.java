package com.powerrich.office.oa.activity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.adapter.CommonViewPagerHelper;
import com.powerrich.office.oa.adapter.MaterialsAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.DoThingBean;
import com.powerrich.office.oa.bean.DynamicInfo;
import com.powerrich.office.oa.bean.FileListBean;
import com.powerrich.office.oa.bean.MaterialsInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.dynamic.EditBox;
import com.powerrich.office.oa.dynamic.MultiCheckBox;
import com.powerrich.office.oa.dynamic.SelectBox;
import com.powerrich.office.oa.dynamic.SpinnerBox;
import com.powerrich.office.oa.dynamic.ViewUtils;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.receiver.IntentReceiver;
import com.powerrich.office.oa.tools.AndroidFileUtil;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.DownloadUtils;
import com.powerrich.office.oa.tools.DownloadUtils.InvokeData;
import com.powerrich.office.oa.tools.LogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.NetWorkUtil;
import com.powerrich.office.oa.tools.progressutils.listener.impl.UIProgressListener;
import com.powerrich.office.oa.view.CircleProgressBar;
import com.powerrich.office.oa.view.MyDialog;
import com.powerrich.office.oa.view.NoScrollListView;
import com.powerrich.office.oa.view.ProgressBarDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.powerrich.office.oa.tools.DialogUtils.showToast;

/**
 * 文 件 名：QueryHandingDetailActivity
 * 描   述：办件详情界面
 * 作   者：Wangzheng
 * 时   间：2018/2/7
 * 版   权：v1.0
 */
public class QueryHandingDetailActivity extends BaseActivity implements OnClickListener, InvokeData, IntentReceiver.NetEvent {

    private static final int CODE_GET_DETAIL = 0;
    private static final int CODE_GET_DYNAMIC_FORM_INFO = 1;
    private static final int CODE_GET_MATERIALS_LIST = 2;
    private static final int CODE_GET_FILE_LIST = 3;
    private List<CommonViewPagerHelper.PagerModel> modelList = new ArrayList<>();
    private CommonViewPagerHelper helper;
    private LayoutInflater layoutInflater;
    private LinearLayout layout;
    private View base_info, form_info, materials_info, attachment_info;
    private LinearLayout ll_form;
    private LinearLayout ll_head;
    private NoScrollListView lv_materials_info;
    private ListView lv_attachment;
    private TextView tv_no_data_form, tv_no_data_materials, tv_no_data_attachment;
    private String fileName;
    private int mPosition;
    private EditText et_bj_linkman_id_num;
    private EditText et_bj_linkman_id_type;
    private EditText et_bj_apply_person_name;
    private EditText et_bj_apply_time;
    private EditText et_bj_event_name;
    private EditText et_bj_event_num;
    private EditText et_bj_link_address;
    private EditText et_bj_linkman_name;
    private EditText et_bj_linkman_mobile_phone;
    private EditText et_bj_object_name;
    private EditText et_bj_object_num;
    private TextView tv_bj_object_num;

    private String itemId, proKeyId;
    private boolean zan_cun;
    private TextView tv_see_form;
    private TextView tv_see_file;
    private TextView tv_continue;
    private TextView tv_redo;
    private View v_continue;
    private View v_redo;
    private DoThingBean mDoThingBean;
    private CommonAdapter<FileListBean> commonAdapter;

    private List<DynamicInfo> dynamicInfoList = new ArrayList<>();
    private List<MaterialsInfo> materialsInfoList = new ArrayList<>();
    private List<FileListBean> fileList = new ArrayList<>();

    private long fileSize;

    private int currentIndex = 0;
    private IntentReceiver receiver;

    /**
     * 判断是否第一次加载数据
     */
    private boolean isFirst1 = true, isFirst2 = true, isFirst3 = true;
    private MaterialsAdapter adapter;
    private ProgressBarDialog progressBarDialog;
    private CircleProgressBar circleProgressBar;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(R.string.do_thing_detail, this, null);
        String userType = LoginUtils.getInstance().getUserInfo().getUserType();
        if ("0".equals(userType)) {
            type = Constants.PERSONAL_WORK_TYPE;
        } else {
            type = Constants.COMPANY_WORK_TYPE;
        }
        itemId = getIntent().getStringExtra("itemId");
        proKeyId = getIntent().getStringExtra("proKeyId");
        zan_cun = getIntent().getBooleanExtra("zan_cun", false);
        initView();
        initViewPagers(savedInstanceState);
        getDoThingDetail();
        receiver = new IntentReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_queryhanding_detail;
    }

    private void initView() {
        layoutInflater = getLayoutInflater();
        layout = (LinearLayout) findViewById(R.id.view_pager);
    }


    /**
     * 办件详情请求
     */
    private void getDoThingDetail() {
        ApiRequest request = OAInterface.doFileDetail(proKeyId);
        invoke.invokeWidthDialog(request, callBack, CODE_GET_DETAIL);
    }

    /**
     * 办件详情-查看该笔件动态表单请求
     */
    private void queryItemDynamicFormInfo() {
        ApiRequest request = OAInterface.queryItemDynamicFormInfo(proKeyId);
        invoke.invokeWidthDialog(request, callBack, CODE_GET_DYNAMIC_FORM_INFO);
    }

    /**
     * 材料信息请求
     */
    private void getMaterialsList() {
        ApiRequest request = OAInterface.getItem(itemId);
        invoke.invokeWidthDialog(request, callBack, CODE_GET_MATERIALS_LIST);
    }

    /**
     * 办件详情-查看该笔件已上传的附件列表请求
     */
    private void getBusinessFileList() {
        ApiRequest request = OAInterface.getBusinessFileList(proKeyId);
        invoke.invokeWidthDialog(request, callBack, CODE_GET_FILE_LIST);
    }

    private void showProgressBarDialog() {
        progressBarDialog = ProgressBarDialog.createDialog(QueryHandingDetailActivity.this);
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
            showToast(QueryHandingDetailActivity.this, "下载成功");
            //"1"为文件存在
            fileList.get(mPosition).setFileState("1");
            if (commonAdapter != null) {
                commonAdapter.notifyDataSetChanged();
            }
            if (progressBarDialog != null) {
                progressBarDialog.cancel();
            }
        }
    };

    private void initViewPagers(Bundle savedInstanceState) {
        helper = new CommonViewPagerHelper(QueryHandingDetailActivity.this, layout, mOnPageChangeListener);
        helper.onCreate(savedInstanceState);
        List<View> views = new ArrayList<>();
        base_info = layoutInflater.inflate(R.layout.activity_query_handing_detail_2, null);
        views.add(base_info);
        form_info = layoutInflater.inflate(R.layout.dynamic_form_detail, null);
        views.add(form_info);
        materials_info = layoutInflater.inflate(R.layout.materials_info, null);
        views.add(materials_info);
        attachment_info = layoutInflater.inflate(R.layout.attachment_detail, null);
        views.add(attachment_info);
        String[] titles = new String[]{getString(R.string.base_info), getString(R.string.form_info), getString(R.string.materials_info), getString(R.string.attachment_info)};
        for (int i = 0; i < titles.length; i++) {
            modelList.add(helper.new PagerModel(titles[i], views.get(i), null));
        }
        helper.showViews(modelList);
        loadView();
    }

    private void loadView() {
        et_bj_linkman_id_num = (EditText) base_info.findViewById(R.id.et_bj_linkman_id_num);
        et_bj_linkman_id_type = (EditText) base_info.findViewById(R.id.et_bj_linkman_id_type);
        et_bj_apply_person_name = (EditText) base_info.findViewById(R.id.et_bj_apply_person_name);
        et_bj_apply_time = (EditText) base_info.findViewById(R.id.et_bj_apply_time);
        et_bj_event_name = (EditText) base_info.findViewById(R.id.et_bj_event_name);
        et_bj_event_num = (EditText) base_info.findViewById(R.id.et_bj_event_num);
        et_bj_link_address = (EditText) base_info.findViewById(R.id.et_bj_link_address);
        et_bj_linkman_name = (EditText) base_info.findViewById(R.id.et_bj_linkman_name);
        et_bj_linkman_mobile_phone = (EditText) base_info.findViewById(R.id.et_bj_linkman_mobile_phone);
        et_bj_object_name = (EditText) base_info.findViewById(R.id.et_bj_object_name);
        et_bj_object_num = (EditText) base_info.findViewById(R.id.et_bj_object_num);
        tv_bj_object_num = (TextView) base_info.findViewById(R.id.tv_bj_object_num);
        tv_bj_object_num.setOnClickListener(this);
        tv_see_form = (TextView) base_info.findViewById(R.id.tv_see_form);
        tv_see_file = (TextView) base_info.findViewById(R.id.tv_see_file);
        tv_continue = (TextView) base_info.findViewById(R.id.tv_continue);
        tv_redo = (TextView) base_info.findViewById(R.id.tv_redo);
        v_continue = base_info.findViewById(R.id.v_continue);
        v_redo = base_info.findViewById(R.id.v_redo);
        if (!zan_cun) {
            tv_continue.setVisibility(View.GONE);
            tv_redo.setVisibility(View.GONE);
            v_continue.setVisibility(View.GONE);
            v_redo.setVisibility(View.GONE);
        }

        tv_continue.setOnClickListener(this);
        tv_redo.setOnClickListener(this);
        tv_see_form.setOnClickListener(this);
        tv_see_file.setOnClickListener(this);
        setEnabled();
        ll_form = (LinearLayout) form_info.findViewById(R.id.ll_form);
        tv_no_data_form = (TextView) form_info.findViewById(R.id.tv_no_data);
        lv_materials_info = (NoScrollListView) materials_info.findViewById(R.id.lv_materials_info);
        ll_head = (LinearLayout) materials_info.findViewById(R.id.ll_head);
        AutoUtils.auto(ll_head);
        tv_no_data_materials = (TextView) materials_info.findViewById(R.id.tv_no_data);
        lv_attachment = (ListView) attachment_info.findViewById(R.id.lv_attachment);
        tv_no_data_attachment = (TextView) attachment_info.findViewById(R.id.tv_no_data);
    }

    private void setEnabled() {
        et_bj_linkman_id_num.setEnabled(false);
        et_bj_linkman_id_num.setFocusable(false);
        et_bj_linkman_id_type.setEnabled(false);
        et_bj_linkman_id_type.setFocusable(false);
        et_bj_apply_person_name.setEnabled(false);
        et_bj_apply_person_name.setSingleLine(false);
        et_bj_apply_person_name.setFocusable(false);
        et_bj_apply_time.setEnabled(false);
        et_bj_apply_time.setFocusable(false);
        et_bj_event_name.setEnabled(false);
        et_bj_event_name.setSingleLine(false);
        et_bj_event_name.setFocusable(false);
        et_bj_event_num.setEnabled(false);
        et_bj_event_num.setFocusable(false);
        et_bj_link_address.setEnabled(false);
        et_bj_link_address.setFocusable(false);
        et_bj_linkman_name.setEnabled(false);
        et_bj_linkman_name.setFocusable(false);
        et_bj_linkman_mobile_phone.setEnabled(false);
        et_bj_linkman_mobile_phone.setFocusable(false);
        et_bj_object_name.setEnabled(false);
        et_bj_object_name.setSingleLine(false);
        et_bj_object_name.setFocusable(false);
        et_bj_object_num.setEnabled(false);
        et_bj_object_num.setFocusable(false);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentIndex = position;
            if (currentIndex == 0) {

            } else if (currentIndex == 1) {
                if (isFirst1) {
                    queryItemDynamicFormInfo();
                    isFirst1 = false;
                }
            } else if (currentIndex == 2) {
                if (isFirst2) {
                    getMaterialsList();
                    isFirst2 = false;
                }
            } else if (currentIndex == 3) {
                if (isFirst3) {
                    getBusinessFileList();
                    isFirst3 = false;
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

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
                    showData(mDoThingBean);
                } else if (CODE_GET_DYNAMIC_FORM_INFO == what) {
                    List<ResultItem> items = item.getItems("DATA");
                    parseDynamicFormInfo(items);
                } else if (CODE_GET_MATERIALS_LIST == what) {
                    List<ResultItem> items = item.getItems("FILE_DATA");
                    showMaterial(items);
                } else if (CODE_GET_FILE_LIST == what) {
                    List<ResultItem> items = item.getItems("DATA");
                    parseFileList(items);
                }
            } else {
                showToast(QueryHandingDetailActivity.this, message);
            }
        }

    };

    /**
     * 获取材料信息数据解析
     *
     * @param items
     */
    private void showMaterial(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            ll_head.setVisibility(View.GONE);
            tv_no_data_materials.setVisibility(View.VISIBLE);
            return;
        }
        ll_head.setVisibility(View.VISIBLE);
        for (ResultItem resultItem : items) {
            String materialNecessity = resultItem.getString("BYX");
			String materialId = resultItem.getString("CL_ID");
            String materialDescribe = resultItem.getString("CLDESCRIBE");
            String materialFormat = resultItem.getString("CLFORMAT");
            String materialCopies = resultItem.getString("CLFS");
            String materialName = resultItem.getString("CLNAME");
            String materialSize = resultItem.getString("CLSIZE");
            String materialForm = resultItem.getString("CLXS");
            String materialType = resultItem.getString("TYPE");
            MaterialsInfo materialsInfo = new MaterialsInfo();
            materialsInfo.setMaterialNecessity(materialNecessity);
			materialsInfo.setMaterialId(materialId);
            materialsInfo.setMaterialDescribe(materialDescribe);
            materialsInfo.setMaterialFormat(materialFormat);
            materialsInfo.setMaterialName(materialName);
            materialsInfo.setMaterialCopies(materialCopies);
            materialsInfo.setMaterialSize(materialSize);
            materialsInfo.setMaterialForm(materialForm);
            materialsInfo.setMaterialType(materialType);
            materialsInfoList.add(materialsInfo);
        }
        if (null == adapter) {
            adapter = new MaterialsAdapter(QueryHandingDetailActivity.this, materialsInfoList, R.layout.material_form_item);
            lv_materials_info.setAdapter(adapter);
        } else {
            adapter.setData(materialsInfoList);
        }
    }

    /**
     * 解析办件详情-查看该笔件动态表单数据
     *
     * @param items
     */
    private void parseDynamicFormInfo(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            tv_no_data_form.setVisibility(View.VISIBLE);
            return;
        }
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
     * 解析办件详情-查看该笔件已上传的附件列表数据
     *
     * @param items
     */
    private void parseFileList(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            tv_no_data_attachment.setVisibility(View.VISIBLE);
            return;
        }
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
        commonAdapter = new CommonAdapter<FileListBean>(QueryHandingDetailActivity.this, fileList, R.layout.attachment_list_item) {
            @Override
            public void convert(ViewHolder holder, FileListBean item) {
                holder.setTextView(R.id.tv_comp_file_name, item.getCompFileName());
                holder.setTextView(R.id.tv_file_name, item.getFileName());
                if ("1".equals(item.getFileState())) {
                    holder.setImageResource(R.id.iv_download, R.drawable.fj_icon_05);
                } else {
                    holder.setImageResource(R.id.iv_download, R.drawable.fj_icon_02);
                }
            }
        };
        lv_attachment.setAdapter(commonAdapter);
        lv_attachment.setOnItemClickListener(new OnItemClickListener() {

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
                            if (!NetWorkUtil.checkedNetWork(QueryHandingDetailActivity.this)) {
                                DialogUtils.showToast(QueryHandingDetailActivity.this, "请检查网络连接是否正常！");
                                return;
                            }
                            DownloadUtils.downloadFile(uiProgressRequestListener, QueryHandingDetailActivity.this, url, fileName);
                            showProgressBarDialog();
                        }
                    });

                }
                if (commonAdapter != null) {
                    commonAdapter.notifyDataSetChanged();
                }
            }
        });

        //长按删除已下载的本地附件
        lv_attachment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String status = fileList.get(position).getFileState();
                if ("1".equals(status)) {
                    MyDialog.showDialog(QueryHandingDetailActivity.this, "提示", "确定删除吗！", new MyDialog.InterfaceClick() {
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
                            commonAdapter.notifyDataSetChanged();
                        }
                    });
                }
                return true;
            }
        });
    }

    private void openFile() {
        doPermissionRW("存储", new PermissionCallBack() {
            @Override
            public void accept() {
                File file = new File(Environment.getExternalStorageDirectory() + "/download/" + fileName);
                AndroidFileUtil.openFile(QueryHandingDetailActivity.this, file);
            }
        });
    }

    private void showData(DoThingBean doThingBean) {
        et_bj_linkman_id_num.setText(doThingBean.getDATA().getPERSON_ID());
        if ("0".equals(doThingBean.getDATA().getPERSON_TYPE())) {
            et_bj_linkman_id_type.setText(getString(R.string.id_card));
        }
        et_bj_apply_person_name.setText(doThingBean.getDATA().getNAME());
        if (doThingBean.getDATA().getSQSJ().contains(".")) {
            String[] split = doThingBean.getDATA().getSQSJ().split("\\.");
            et_bj_apply_time.setText(split[0]);
        } else {
            et_bj_apply_time.setText(doThingBean.getDATA().getSQSJ());
        }
        et_bj_event_name.setText(doThingBean.getDATA().getSXMC());
        et_bj_event_num.setText(doThingBean.getDATA().getSXBM());
        et_bj_link_address.setText(doThingBean.getDATA().getPERSON_ADDRESS());
        et_bj_linkman_name.setText(doThingBean.getDATA().getPERSON_NAME());
        et_bj_linkman_mobile_phone.setText(doThingBean.getDATA().getPERSON_PHONE());
        et_bj_object_name.setText(doThingBean.getDATA().getREGISTER_REMARK());
        et_bj_object_num.setText(doThingBean.getDATA().getTRACKINGNUMBER());
        tv_bj_object_num.setText(doThingBean.getDATA().getTRACKINGNUMBER());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                QueryHandingDetailActivity.this.finish();
                break;
            case R.id.tv_see_file:

                break;
            case R.id.tv_see_form:

                break;
            case R.id.tv_bj_object_num:
                String text = tv_bj_object_num.getText().toString();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(text);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = android.content.ClipData.newPlainText(text, text);
                    clipboard.setPrimaryClip(clip);
                }
                if (!BeanUtils.isEmptyStr(text)) {
                    showToast(QueryHandingDetailActivity.this, "申报编号复制成功！");
                }
                break;
            case R.id.tv_continue:

                if (!"0".equals(mDoThingBean.getDATA().getPOSITION())) {
                    Intent intent = new Intent(QueryHandingDetailActivity.this, BasicInformationActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("item_id", mDoThingBean.getDATA().getSXID());
                    intent.putExtra("itemName", mDoThingBean.getDATA().getREGISTER_REMARK());
                    intent.putExtra("proKeyId", mDoThingBean.getDATA().getPROKEYID());
                    intent.putExtra("dynamicForm", mDoThingBean.getDATA().getDYNAMICFORM());
                    intent.putExtra("fileData", mDoThingBean.getDATA().getFILEDATA());
                    intent.putExtra("position", mDoThingBean.getDATA().getPOSITION());
                    startActivity(intent);
                } /*else if ("1".equals(mDoThingBean.getDATA().getPOSITION())) {
                    Intent intent = new Intent(QueryHandingDetailActivity.this, DeclareNoticeActivity.class);
                    intent.putExtra("item_id", mDoThingBean.getDATA().getSXID());
                    intent.putExtra("proKeyId", mDoThingBean.getDATA().getPROKEYID());
                    intent.putExtra("dynamicForm", mDoThingBean.getDATA().getDYNAMICFORM());
                    intent.putExtra("fileData", mDoThingBean.getDATA().getFILEDATA());
                    intent.putExtra("position", mDoThingBean.getDATA().getPOSITION());
                    startActivity(intent);
                } else if ("3".equals(mDoThingBean.getDATA().getPOSITION())) {
                    Intent intent = new Intent(QueryHandingDetailActivity.this, BaseInformationActivity.class);
                    intent.putExtra("itemId", mDoThingBean.getDATA().getSXID());
                    intent.putExtra("itemName", mDoThingBean.getDATA().getREGISTER_REMARK());
                    intent.putExtra("itemCode", mDoThingBean.getDATA().getSXBM());
                    intent.putExtra("proKeyId", mDoThingBean.getDATA().getPROKEYID());
                    intent.putExtra("fileData", mDoThingBean.getDATA().getFILEDATA());
                    intent.putExtra("position", mDoThingBean.getDATA().getPOSITION());
                    startActivity(intent);
                } else if ("4".equals(mDoThingBean.getDATA().getPOSITION())) {
                    Intent intent = new Intent(QueryHandingDetailActivity.this, DeclareMaterialsActivity.class);
                    intent.putExtra("itemId", mDoThingBean.getDATA().getSXID());
                    intent.putExtra("itemName", mDoThingBean.getDATA().getREGISTER_REMARK());
                    intent.putExtra("itemCode", mDoThingBean.getDATA().getSXBM());
                    intent.putExtra("proKeyId", mDoThingBean.getDATA().getPROKEYID());
                    intent.putExtra("dynamicForm", mDoThingBean.getDATA().getDYNAMICFORM());
                    intent.putExtra("fileData", mDoThingBean.getDATA().getFILEDATA());
                    intent.putExtra("position", mDoThingBean.getDATA().getPOSITION());
                    startActivity(intent);
                } else if ("5".equals(mDoThingBean.getDATA().getPOSITION())) {
                    Intent intent = new Intent(QueryHandingDetailActivity.this, DeclareNotifyActivity.class);
                    intent.putExtra("itemName", mDoThingBean.getDATA().getREGISTER_REMARK());
                    intent.putExtra("itemCode", mDoThingBean.getDATA().getSXBM());
                } */ else {
                    showToast(QueryHandingDetailActivity.this, "该办件不能继续办理");
                }
                break;
            case R.id.tv_redo:

                break;

            default:
                break;
        }
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
}
