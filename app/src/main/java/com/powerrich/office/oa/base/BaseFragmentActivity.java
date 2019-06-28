package com.powerrich.office.oa.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;


/**
 * Filename : BaseFragmentActivity.java
 *
 * @Description : FragmentActivity基本类
 * @Author : 王政
 * @Version : 1.0
 *
 */
public class BaseFragmentActivity extends FragmentActivity {

	public static final int DIALOG_ERROR = 11111;
	public static final int DIALOG_POWER = 22222;
	public static final String TITLE = "title";

	protected InvokeHelper helper;
	protected String errorMessage;

	protected static final String REUSLT_DATA_KEY = "RESULT_DATA";
	protected static final String EXIT = "SYSTEM_EXIT";
	protected static final String SESSION_LOST_KEY = "SESSION_LOST_KEY";

	private String title;

	private static SharedPreferences pref;

	private static boolean isLeave = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pref = getSharedPreferences("timeoutcheck", Context.MODE_PRIVATE);
		helper = new InvokeHelper(this);
//		SystemContext.initContext(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		title = getIntent().getStringExtra(TITLE);
		if (!BeanUtils.isEmpty(title)) {
			setTitle(title);
		}
	}

	public void showErrorMessage(String message) {
		errorMessage = message;
		showDialog(DIALOG_ERROR);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_POWER :
				return new AlertDialog.Builder(BaseFragmentActivity.this)
						.setTitle(R.string.app_name)
						.setMessage(errorMessage)
						.setPositiveButton(R.string.system_dialog_confirm,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										loginBack();
									}
								}).setOnCancelListener(new OnCancelListener() {

							@Override
							public void onCancel(DialogInterface dialog) {
								loginBack();
							}
						}).create();
			case DIALOG_ERROR :
				Toast.makeText(BaseFragmentActivity.this, errorMessage,
						Toast.LENGTH_SHORT).show();
				break;
			default :
				break;
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		if (3234 != id) {
			removeDialog(id);
		}
		super.onPrepareDialog(id, dialog);
	}

	public void sucessBack() {
		Intent intent = new Intent();
		intent.putExtra(REUSLT_DATA_KEY, true);
		setResult(RESULT_OK, intent);
		finish();
	}

	public void loginBack() {
//		Intent intent = new Intent(this, LoginActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(intent);
//		finish();
	}

	public void onClickback(View view) {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if (!(this instanceof SystemLoginActivity)) {
		if (123456 == requestCode && data != null) {
			if (data.getBooleanExtra(SESSION_LOST_KEY, false)) {
				loginBack();
			}
		}
		// }
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 验证返回的数据是否正常
	 *
	 * @param resultItems
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean checkResult(ResultItem item) {
		try {
			if (!BeanUtils.isEmpty(item)) {
				String code = item.getString("code");
				if (!Constants.SUCCESS_CODE.equals(code)) {
					errorMessage = item.getString("message");
					//令牌失效
					if (Constants.LOGIN_FAIL_CODE.equals(code)) {
						errorMessage = "您的账号在其它地方已登录，请重新登录！";
						showDialog(DIALOG_POWER);
					} else {
						showDialog(DIALOG_ERROR);
					}
					return false;
				}
			} else {
				errorMessage = getString(R.string.system_request_error_message);
				showDialog(DIALOG_ERROR);
				return false;
			}
		} catch (Exception e) {

		}
		return true;
	}

	@Override
	public void finish() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null && imm.isActive()) {// 若返回true，则表示输入法打开
			// 判断是否有View组件已经获得焦点
			if (this.getCurrentFocus() != null) {
				// 关闭输入法
				imm.hideSoftInputFromWindow(this.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		super.finish();
	}

	@Override
	protected void onUserLeaveHint() {
		// 如果是系统登录或者是手势密码登录，就不进行处理，公文和会议详情页面，也不算跳出
		//if (this instanceof LoginGesturesPasswordActivity
		//		|| this instanceof SystemLoginActivity
		//		|| this instanceof DocumentDetailActivity
		//		|| this instanceof MeetDetailActivity) {

		//} else {
			if (!isLeave) {
				isLeave = true;
				saveStartTime();
			}
		//}
	}

	@Override
	protected void onResume() { // 当用户使程序恢复为前台显示时执行onResume()方法,在其中判断是否超时.
		super.onResume();
		if (isLeave) {
			isLeave = false;
			timeOutCheck();
		}
	}

	public void timeOutCheck() {
//		long endtime = System.currentTimeMillis();
//		// 获取允许锁屏之后多久进入的秒数
//		String lockTimeSeconds = getString(R.string.is_lock_second);
//		if (!BeanUtils.isEmpty(lockTimeSeconds)) {
//			if (endtime - getStartTime() >= Integer.parseInt(lockTimeSeconds) * 1000) {
//				lockAction();
//			}
//		}
	}

	public void saveStartTime() {
		pref.edit().putLong("starttime", System.currentTimeMillis()).commit();
	}

	public long getStartTime() {
		return pref.getLong("starttime", 0);

	}

	private void lockAction() {
//		try {
//			UserHelper userHelper = new UserHelper();
//			ResultItem pwdItem = userHelper.getLastGesturesCode();
//			// 如果存在手势密码，则进行跳转输入手势密码
//			if (pwdItem != null && pwdItem.getInt("ISLOCK") == 1
//					&& !BeanUtils.isEmpty(pwdItem.getString("GESTURESCODE"))) {
//				Intent intent = new Intent(this,
//						LoginGesturesPasswordActivity.class);
//				intent.putExtra(Constants.LOCK_SCREEN, "true");
//				startActivity(intent);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
