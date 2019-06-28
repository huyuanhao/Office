package com.powerrich.office.oa.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.BookingTimeInfo;
import com.powerrich.office.oa.bean.OnlineBookingInfo;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.view.ExpandLayout;
import com.powerrich.office.oa.view.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;


/**
 * Cc：在线预约
 * @author ch
 * @title 网上预约第二步
 */
public class OnlineBookingHallActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 事项id和预约日期获取对应日期可以预约的时间段
     */
    private static final int GETITEMAPPOINTMENTTIME = 222;
    private static final int READER_ITEM_CODE = 333;
    private NoScrollGridView gv_booking_time;
    private TextView tv_date;
    private String itemId, orderTime;
    private int oldPosition = -1;
    private TextView tv_dep, tv_item;
    private CommonAdapter bookingTimeAdapter;
    private TextView tv_base_info;
    private TextView name;
    private TextView tv_name;
    private TextView tv_id_card;
    private RelativeLayout rl_org_name;
    private RelativeLayout rl_org_num;
    private TextView tv_org_name;
    private TextView tv_org_num;
    private TextView tv_phone_number;
    private EditText et_email;
    private ExpandLayout exp_base_info;
    public static OnlineBookingHallActivity instance;
    private TextView tv_empty_time;
    private PopupWindow pop;
    private String type;
    private String normacceptDepart;
    private String itemName;
    private String sxbm;
    private String siteNo;
    private boolean isReaderItem;
    private CommonAdapter dateAdapter;
    private List<String> date7;
    private ScrollView scrollView;
    private String userDuty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        Intent intent = getIntent();
        normacceptDepart = intent.getStringExtra("NORMACCEPTDEPART");
        siteNo = intent.getStringExtra("SITENO");
        itemName = intent.getStringExtra("ITEMNAME");
        itemId = intent.getStringExtra("ITEMID");
        initTitleBar(R.string.title_activity_online_booking_hall, this, null);
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_online_booking_hall;
    }

    private void initView() {
        scrollView = findViewById(R.id.scrollView);
        gv_booking_time = findViewById(R.id.gv_booking_time);
        tv_empty_time = findViewById(R.id.tv_empty_time);
        tv_dep = findViewById(R.id.tv_dep);
        tv_item = findViewById(R.id.tv_item);
        tv_date = findViewById(R.id.tv_date);
        tv_base_info = findViewById(R.id.tv_base_info);
        exp_base_info = findViewById(R.id.exp_base_info);
        name = findViewById(R.id.name);
        tv_name = findViewById(R.id.tv_name);
        tv_id_card = findViewById(R.id.tv_id_card);
        rl_org_name = findViewById(R.id.rl_org_name);
        rl_org_num = findViewById(R.id.rl_org_num);
        tv_id_card = findViewById(R.id.tv_id_card);
        tv_org_name = findViewById(R.id.tv_org_name);
        tv_org_num = findViewById(R.id.tv_org_num);
		tv_phone_number = findViewById(R.id.tv_phone_number);
		et_email = findViewById(R.id.et_email);
		findViewById(R.id.btn_next_step).setOnClickListener(this);
		tv_base_info.setOnClickListener(this);
		tv_date.setOnClickListener(this);
		exp_base_info.initExpand(false);//设定初始化折叠，默认展开
        exp_base_info.setScrollView(scrollView);
		initData();
	}
	private void initData() {
        tv_dep.setText(normacceptDepart == null ? "" : normacceptDepart);
        tv_item.setText(itemName == null ? "" : itemName);
        if (LoginUtils.getInstance().isLoginSuccess()){//如果已登陆过，自动显示当前用户信息
            UserInfo userInfo = LoginUtils.getInstance().getUserInfo();
            if (!BeanUtils.isEmpty(userInfo)) {
                userDuty = userInfo.getDATA().getUSERDUTY();
                if ("0".equals(userDuty)) {//"0"为个人
                    type = Constants.PERSONAL_WORK_TYPE;
                    tv_name.setText(userInfo.getDATA().getREALNAME());
                    tv_phone_number.setText(userInfo.getDATA().getMOBILE_NUM());
                    tv_id_card.setText(userInfo.getDATA().getIDCARD());
                    rl_org_name.setVisibility(View.GONE);
                    rl_org_num.setVisibility(View.GONE);
                } else if ("1".equals(userDuty)){ //"1"为企业
                    type = Constants.COMPANY_WORK_TYPE;
                    tv_name.setText(userInfo.getDATA().getFRDB());
                    tv_id_card.setText(userInfo.getDATA().getFRDB_SFZHM());
                    rl_org_name.setVisibility(View.VISIBLE);
                    rl_org_num.setVisibility(View.VISIBLE);
                    tv_org_name.setText(userInfo.getDATA().getCOMPANYNAME());
                    tv_org_num.setText(userInfo.getDATA().getBUSINESSLICENCE());
                    tv_phone_number.setText(userInfo.getDATA().getGR_REALPHONE());
                }
//                et_email.setText(userInfo.getDATA().getEMAIL());
            }
        }
        getInternetDate();
    }

    /**
     * 获取网络日期
     */
    private void getInternetDate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                date7 = DateUtils.get7date();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (date7 != null && date7.size() > 0) {
                            String s = date7.get(0);
                            String[] strings = s.split("\\(");
                            String string = strings[0];
                            tv_date.setText(string);
                            getItemAppointmentTime(string);
                        }
                        if (dateAdapter != null) {
                            dateAdapter.setData(date7);
                        }
                    }
                });

            }
        }).start();
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (GETITEMAPPOINTMENTTIME == what) {
                    Gson gson = new Gson();
                    String jsonStr = item.getJsonStr();
                    BookingTimeInfo bookingTimeInfo = gson.fromJson(jsonStr, BookingTimeInfo.class);
                    BookingTimeInfo.DATABean dataBean = bookingTimeInfo.getDATA();
                    List<BookingTimeInfo.DATABean.YYSJBean> yysj = dataBean.getYYSJ();
                    showBookingTime(yysj);
                    if (!isReaderItem) {//调用一次之后不再调用
                        readerItem();
                    }
                } else if (READER_ITEM_CODE == what) {
                    isReaderItem = true;
                }
            } else {
                DialogUtils.showToast(OnlineBookingHallActivity.this, message);
            }
        }

//        @Override
//        public void finish(final Object dialogObj, int what) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Activity activity = OnlineBookingHallActivity.this;
//                    if (activity != null && !activity.isFinishing() && dialogObj != null) {
//                        if(dialogObj instanceof LoadingDialog){
//                           ((LoadingDialog)dialogObj).dismiss();
//                        }
//                    }
//                }
//            }, 500);
//        }
    };

    /**
     * 获取事项浏览接口
     */
    private void readerItem() {
        ApiRequest request = OAInterface.readerItem(itemId == null ? "" :itemId, type);
        invoke.invoke(request, callBack, READER_ITEM_CODE);
    }

    /**
     * 获取该日期下所有预约时间段和预约状态
     *
     * @param dateTime
     */
    private void getItemAppointmentTime(String dateTime) {
        invoke.invokeWidthDialog(OAInterface.getItemAppointmentTime(itemId == null ? "" : itemId, dateTime), callBack, GETITEMAPPOINTMENTTIME);
    }

    /**
     * 展示预约时间段
     *
     * @param yysj
     */
    protected void showBookingTime(List<BookingTimeInfo.DATABean.YYSJBean> yysj) {
        ArrayList<OnlineBookingInfo> onlineBookingInfos = new ArrayList<>();
        //重置上次点击位置
        oldPosition = -1;
        if (!BeanUtils.isEmpty(yysj)) {
            gv_booking_time.setVisibility(View.VISIBLE);
            tv_empty_time.setVisibility(View.GONE);
            //解析可预约时间段
            for (int i = 0; i < yysj.size(); i++) {
                OnlineBookingInfo onlineBookingInfo = new OnlineBookingInfo("1", yysj.get(i).getTime(),
                        yysj.get(i).getCount());//status： "1":可预约，"2"：已预约
                onlineBookingInfos.add(onlineBookingInfo);
            }
        } else {
            tv_empty_time.setVisibility(View.VISIBLE);
            gv_booking_time.setVisibility(View.GONE);
        }
        if (null == bookingTimeAdapter) {
            bookingTimeAdapter = new CommonAdapter<OnlineBookingInfo>(OnlineBookingHallActivity.this,
                    onlineBookingInfos, R.layout.booking_time_girdview_item) {
                @Override
                public void convert(ViewHolder holder, final OnlineBookingInfo item) {
                    holder.setTextView(R.id.tv_booking_time, item.getTime());
                    Button bt_status = holder.getItemView(R.id.booking_status);
                    if (Integer.valueOf(BeanUtils.isEmptyStr(item.getCount()) ? "0" : item.getCount()) >= 20) {
                        bt_status.setText("不可预约(" + item.getCount() + "/20" + ")");
                        bt_status.setBackgroundResource(R.drawable.gray_bg);
                        bt_status.setEnabled(false);
                    } else {
                        String status = item.getStatus();
                        bt_status.setEnabled(true);
                        if ("1".equals(status)) {
                            bt_status.setText("可预约(" + item.getCount() + "/20" + ")");
                            bt_status.setBackgroundResource(R.drawable.green_icon);
                        } else if ("2".equals(status)) {
                            bt_status.setText("已选择");
                            bt_status.setBackgroundResource(R.drawable.gray_bg);
                        }
                    }
                    bt_status.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = bookingTimeAdapter.getPosition(item);
                            orderTime = item.getTime();
                            String status = item.getStatus();
                            if (oldPosition != position) {
                                if ("1".equals(status)) {
                                    item.setStatus("2");
                                    if (oldPosition != -1) {
                                        OnlineBookingInfo oldItem = (OnlineBookingInfo) bookingTimeAdapter.getItem(oldPosition);
                                        oldItem.setStatus("1");
                                    }
                                }
                                bookingTimeAdapter.notifyDataSetChanged();
                                oldPosition = position;
                            }
                        }
                    });
                }
            };
            gv_booking_time.setAdapter(bookingTimeAdapter);
        } else {
            bookingTimeAdapter.setData(onlineBookingInfos);
        }
    }

    private boolean validate() {
//		String idNum = tv_id_card.getText().toString().trim();
//		if (BeanUtils.isEmptyStr(tv_name.getText().toString().trim())) {
//			return setReturnMsg("预约人姓名不能为空");
//		} else if (!BeanUtils.validCard(idNum)) {
//			return setReturnMsg("身份证号格式不正确");
//		} else if (!BeanUtils.isMobileNO(tv_phone_number.getText().toString().trim())) {
//			return setReturnMsg("请输入正确的手机号");
//		}
        if (BeanUtils.isEmptyStr(tv_name.getText().toString().trim())) {
            return setReturnMsg("预约人姓名不能为空");
        } else if (BeanUtils.isEmptyStr(tv_id_card.getText().toString().trim())) {
            return setReturnMsg("身份证号不能为空");
        } else if (BeanUtils.isEmptyStr(tv_phone_number.getText().toString().trim())) {
            return setReturnMsg("手机号不能为空");
        }
        return true;
    }

    private boolean setReturnMsg(String msg) {
        DialogUtils.showToast(OnlineBookingHallActivity.this, msg);
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.tv_date:
                if (BeanUtils.isEmptyStr(itemId) || BeanUtils.isEmptyStr(itemName)) {
                    DialogUtils.showToast(OnlineBookingHallActivity.this, "预约业务为空");
                    return;
                }
                showDateSelectPop();
                break;
            case R.id.btn_next_step:
                if (BeanUtils.isEmptyStr(tv_date.getText().toString().trim())) {
                    DialogUtils.showToast(OnlineBookingHallActivity.this, "请选择预约日期");
                    return;
                }
                if (BeanUtils.isEmptyStr(orderTime)) {
                    DialogUtils.showToast(OnlineBookingHallActivity.this, "请选择预约时间段");
                    return;
                }
                if (validate()) {
                    OnlineBookingInfo bookingInfo = saveBookingInfo();
                    Intent intent = new Intent(OnlineBookingHallActivity.this, OnlineBookingVerifyActivity.class);
                    intent.putExtra("bookingInfo", bookingInfo);
                    startActivity(intent);
                }
                break;
            case R.id.tv_base_info://基本信息展开或关闭
                exp_base_info.toggleExpand();
                if (exp_base_info.isExpand()) {
                    Drawable up_arrows2 = getResources().getDrawable(R.drawable.up_arrows2);
                    up_arrows2.setBounds(0, 0, up_arrows2.getMinimumWidth(), up_arrows2.getMinimumHeight());
                    tv_base_info.setCompoundDrawables(null, null, up_arrows2, null);
                } else {
                    Drawable down_arrows2 = getResources().getDrawable(R.drawable.down_arrows2);
                    down_arrows2.setBounds(0, 0, down_arrows2.getMinimumWidth(), down_arrows2.getMinimumHeight());
                    tv_base_info.setCompoundDrawables(null, null, down_arrows2, null);
                }
                break;
            default:
                break;
        }
    }

    private void showDateSelectPop() {
        if (null == pop) {
            View view = this.getLayoutInflater().inflate(R.layout.dropdownlist_popupwindow, null);
            pop = DialogUtils.createPopWindow(OnlineBookingHallActivity.this, view, tv_date, tv_date.getWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            ListView listView = (ListView) view.findViewById(R.id.listView);
            if (dateAdapter == null) {
                dateAdapter = new CommonAdapter<String>(OnlineBookingHallActivity.this, date7, R.layout.dropdown_list_item) {
                    @Override
                    public void convert(ViewHolder holder, String item) {
                        holder.setTextView(R.id.tv, item);
                    }
                };
                listView.setAdapter(dateAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //重置预约时间段
                        orderTime = "";
                        String item = (String) parent.getAdapter().getItem(position);
                        String[] strings = item.split("\\(");
                        tv_date.setText(strings[0]);
                        if (pop.isShowing()) {
                            pop.dismiss();
                        }
                        getItemAppointmentTime(tv_date.getText().toString());
                    }
                });
            } else {
                dateAdapter.setData(date7);
            }
        } else {
            if (pop.isShowing()) {
                pop.dismiss();
            } else {
                pop.showAsDropDown(tv_date);
            }
        }
    }

	/**
	 * 保存个人信息
	 */
	private OnlineBookingInfo saveBookingInfo() {
		OnlineBookingInfo bookingInfo = new OnlineBookingInfo();
		bookingInfo.setItemName(tv_item.getText().toString().trim());
		bookingInfo.setName(tv_name.getText().toString().trim());
		bookingInfo.setIdcard(tv_id_card.getText().toString().trim());
		if (!BeanUtils.isEmpty(LoginUtils.getInstance().getUserInfo())) {
			if (type==Constants.COMPANY_WORK_TYPE) {//"0"为个人   "1"为企业
				bookingInfo.setOrgName(tv_org_name.getText().toString().trim());
				bookingInfo.setOrgNum(tv_org_num.getText().toString().trim());
			}
		}
		bookingInfo.setPhone(tv_phone_number.getText().toString().trim());
//		bookingInfo.setEmail(et_email.getText().toString().trim());
		bookingInfo.setOrderDate(tv_date.getText().toString());
		bookingInfo.setOrderTime(orderTime);
		bookingInfo.setItemId(itemId == null ? "" :itemId);
		bookingInfo.setSiteid(siteNo == null ? "" : siteNo);
		return bookingInfo;
	}
}
