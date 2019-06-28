package com.powerrich.office.oa.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.OnlineBookingInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.view.MyDialog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 网上预约 信息验证界面
 * @author Administrator
 *
 */
public class OnlineBookingInfoVerifyActivity extends BaseActivity implements OnClickListener {
	private static int SAVEAPPOINTMENTINFO=000;
	private static int SENDYZM=111;
	private static String SUCCESS = "预约成功";
	private static String FAILURE = "预约失败";
	private Button btn_confirm;
	private TextView tv_timer;
	/** 预约人姓名*/ 
	private EditText et_name;
	/** 身份证号码*/
	private EditText et_idNum;
	/** 手机号码*/
	private EditText et_phoneNum;
	/** 验证码*/
	private EditText et_verificationCode;
	private String valiCode = "";
	private int countDown;
	private String sendPhoneNum = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initTitleBar(R.string.title_activity_online_booking_info_verify, this, null);
		initView();
	}

	@Override
	protected int provideContentViewId() {
		return R.layout.activity_online_booking_info_verify;
	}

	private void initView() {
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		tv_timer = (TextView) findViewById(R.id.tv_timer);
		et_name = (EditText) findViewById(R.id.et1);
		et_idNum = (EditText) findViewById(R.id.et2);
		et_phoneNum = (EditText) findViewById(R.id.et3);
		et_verificationCode = (EditText) findViewById(R.id.et4);
		btn_confirm.setOnClickListener(this);
		tv_timer.setOnClickListener(this);
		loadData();
	}

	private void loadData() {
		if (LoginUtils.getInstance().isLoginSuccess()){//如果已登陆过，自动显示当前用户信息
			et_name.setText(LoginUtils.getInstance().getUserInfo().getDATA().getREALNAME());
			et_idNum.setText(LoginUtils.getInstance().getUserInfo().getDATA().getIDCARD());
			et_phoneNum.setText(LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM());
		}
	}

	IRequestCallBack callBack = new BaseRequestCallBack() {

		@Override
		public void process(HttpResponse response, int what) {
			ResultItem item = response.getResultItem(ResultItem.class);
			String code = item.getString("code");
			String message = item.getString("message");
			if (Constants.SUCCESS_CODE.equals(code)) {
				if (SENDYZM==what) {
					valiCode = item.getString("vali_code");
					//设置倒计时
					setTimerTask();
				} else if (SAVEAPPOINTMENTINFO == what) {
					showBookedDialog(SUCCESS,"预约受理号:" + item.getString("ORDER_NO"));
				}
			} else if ("-99".equals(code)){
				showBookedDialog(FAILURE,message);
			} else {
				DialogUtils.showToast(OnlineBookingInfoVerifyActivity.this, message);
			}
		}
		
	};
	
	private boolean validate() {
		String idNum = et_idNum.getText().toString().trim();
		if (BeanUtils.isEmptyStr(et_name.getText().toString().trim())) {
			return setReturnMsg("预约人姓名不能为空");
		} else if (!BeanUtils.validCard(idNum)) {
			return setReturnMsg("身份证号格式不正确");
		} else if (!BeanUtils.isMobileNO(et_phoneNum.getText().toString().trim())) {
			return setReturnMsg("请输入正确的手机号");
		} else if (BeanUtils.isEmptyStr(et_verificationCode.getText().toString().trim())) {
			return setReturnMsg("请输入验证码");
		} else if (!valiCode.equals(et_verificationCode.getText().toString().trim())) {
			return setReturnMsg("验证码不正确");
		}
		return true;
	}

	/**
	 * 保存个人信息
	 */
	protected void savePersonInfo() {
		String name = et_name.getText().toString().trim();
		String idNum = et_idNum.getText().toString().trim();
		String phoneNum = sendPhoneNum;//保存发送验证码的手机号
//		OnlineBookingInfo.name = name;
//		OnlineBookingInfo.idcard = idNum;
//		OnlineBookingInfo.phone = phoneNum;
}

	private boolean setReturnMsg(String msg){
        DialogUtils.showToast(OnlineBookingInfoVerifyActivity.this, msg);
        return false;
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.system_back:
			showCloseDialog();
			break;
		case R.id.btn_confirm:
			if (validate()) {
				savePersonInfo();
				//调用保存预约接口
//				invoke.invokeWidthDialog(OAInterface.saveAppointmentInfo(), callBack,SAVEAPPOINTMENTINFO);
			}
			break;
		case R.id.tv_timer:
			sendPhoneNum = et_phoneNum.getText().toString().trim();
			if (!BeanUtils.isMobileNO(sendPhoneNum)) {
				DialogUtils.showToast(OnlineBookingInfoVerifyActivity.this, "手机号格式不正确");
				return;
			}
			//发送短信接口
			invoke.invokeWidthDialog(OAInterface.getPhoneValidateCode(sendPhoneNum), callBack,SENDYZM);
			break;

		default:
			break;
		}
	}

	/**
	 * 设置倒计时
	 */
	private void setTimerTask() {
		countDown = 60;
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						countDown --;
						if (countDown <= 0){
							timer.cancel();//取消倒计时
							tv_timer.setText("重新获取");
							tv_timer.setClickable(true);//重新获得点击
							tv_timer.setTextColor(ContextCompat.getColor(OnlineBookingInfoVerifyActivity.this,
									R.color.white));
							tv_timer.setBackgroundResource(R.drawable.blue_corner_selector);  //还原背景色
						} else {
							tv_timer.setClickable(false); //设置不可点击
							tv_timer.setTextColor(ContextCompat.getColor(OnlineBookingInfoVerifyActivity.this,
									R.color.gray));
							tv_timer.setText("已发送(" + countDown + "s)");  //设置倒计时时间
							tv_timer.setBackgroundResource(R.drawable.gray_bg); //设置按钮为灰色，这时是不能点击的
//							SpannableString spannableString = new SpannableString(tv_timer.getText().toString());  //获取按钮上的文字
//							ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
//							/**
//							 * public void setSpan(Object what, int start, int end, int flags)
//							 * 主要是start跟end，start是起始位置,无论中英文，都算一个。
//							 * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
//							 */
//							spannableString.setSpan(span, 4, 6, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
//							tv_timer.setText(spannableString);
						}
					}
				});


			}
		}, 1000, 1000);//表示1000毫秒之后，每隔1000毫秒执行一次.
	}
	/**
	 * 展示预约成功后提示
	 */
	private void showBookedDialog(final String state, String message){
		MyDialog builder = new MyDialog(OnlineBookingInfoVerifyActivity.this).builder();
				builder.setCancelable(false)
				.setTitle(state)
				.setMessage(message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (SUCCESS.equals(state)) {//若预约成功，则关闭此界面
							finish();
						}
					}
				}).show();
	}

	@Override
	public void onBackPressed() {
		showCloseDialog();
	}

	private void showCloseDialog(){
		MyDialog builder = new MyDialog(OnlineBookingInfoVerifyActivity.this).builder();
		builder.setMessage("是否放弃预约？")
				.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						OnlineBookingInfoVerifyActivity.this.finish();
					}
				})
				.setPositiveButton("继续预约", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.show();
	}
}
