package com.powerrich.office.oa.view.wheel;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.view.View;
import android.widget.Button;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.tools.DateUtils;


public class WheelTime {

	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;
	public int screenheight;
	private boolean hasSelectTime;
	private static int START_YEAR = Calendar.getInstance().get(Calendar.YEAR), END_YEAR = Calendar.getInstance().get(Calendar.YEAR) + 10;
	private Button bt_confirm;
	private Button bt_cancel;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public static int getSTART_YEAR() {
		return START_YEAR;
	}

	public static void setSTART_YEAR(int sTART_YEAR) {
		START_YEAR = sTART_YEAR;
	}

	public static int getEND_YEAR() {
		return END_YEAR;
	}

	public static void setEND_YEAR(int eND_YEAR) {
		END_YEAR = eND_YEAR;
	}

	public WheelTime(View view) {
		super();
		this.view = view;
		hasSelectTime = false;
		setView(view);
	}
	public WheelTime(View view, boolean hasSelectTime) {
		super();
		this.view = view;
		this.hasSelectTime = hasSelectTime;
		setView(view);
	}
	public void initDateTimePicker(int year, int month, int day) {
		this.initDateTimePicker(year, month, day, 0, 0);
	}
	/**
	 * 弹出日期时间选择器
	 */
	public void initDateTimePicker(int year, int month, int day, int h, int m) {
		// int year = calendar.get(Calendar.YEAR);
		// int month = calendar.get(Calendar.MONTH);
		// int day = calendar.get(Calendar.DATE);
		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
		String[] months_little = {"4", "6", "9", "11"};

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);
		bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
		bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
		wv_year.setCyclic(true);// 可循环滚动
		 wv_year.setLabel("年");// 添加文字
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

		// 月
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		 wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		// 日
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {// 闰年
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			} else {// 非闰年
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
			}
		}
		 wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_mins = (WheelView) view.findViewById(R.id.min);
		if (hasSelectTime) {
			wv_hours.setVisibility(View.VISIBLE);
			wv_mins.setVisibility(View.VISIBLE);

			wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
			wv_hours.setCyclic(true);// 可循环滚动
			// wv_hours.setLabel("时");// 添加文字
			wv_hours.setCurrentItem(h);

			wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
			wv_mins.setCyclic(true);// 可循环滚动
			// wv_mins.setLabel("分");// 添加文字
			wv_mins.setCurrentItem(m);
		} else {
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
		}

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0) {// 闰年
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					} else {// 非闰年
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
					}
				}
				compareDate();
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {// 闰年
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					} else {// 非闰年
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
					}
				}
				compareDate();
			}
		};
		// 添加"日"监听
		OnWheelChangedListener wheelListener_day = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				compareDate();
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);
		wv_day.addChangingListener(wheelListener_day);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 0;
		if (hasSelectTime) {
//			textSize = (screenheight / 100) * 3;
			textSize = (screenheight / 120) * 3;
		} else {
//			textSize = (screenheight / 100) * 4;
			textSize = (screenheight / 120) * 4;
		}
		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
		
		wv_day.TEXT_VALUE_SIZE = textSize+2;
		wv_month.TEXT_VALUE_SIZE = textSize+2;
		wv_year.TEXT_VALUE_SIZE = textSize+2;
		wv_hours.TEXT_VALUE_SIZE = textSize+2;
		wv_mins.TEXT_VALUE_SIZE = textSize+2;

	}

	public String getTime() {
		if (!hasSelectTime) {
			return this.getTime("yyyy-MM-dd");
		} else {
			return this.getTime("yyyy-MM-dd HH:mm");
		}
	}
	
	/**
	 * 返回自定义格式的日期字符串
	 */
	public String getTime(String format) {
		int theYear = wv_year.getCurrentItem() + START_YEAR;//年
		int theMonth = wv_month.getCurrentItem();//月：0 到 11 之间的数
		int theDay = wv_day.getCurrentItem() + 1;//日：1 到31 之间的数
		int theHour = wv_hours.getCurrentItem();//时：0 到23 之间的数
		int theMins = wv_mins.getCurrentItem();//分：0 到59 之间的数
		
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.set(theYear,theMonth,theDay,theHour,theMins);
		
		return DateUtils.getDateStr(cal.getTime(), format);
	}

	private void compareDate() {
		Date currentDate = DateUtils.parseDate(DateUtils.getDateStr(), "yyyy-MM-dd");
		Date selectDate = DateUtils.parseDate(getTime(),"yyyy-MM-dd");
		if (selectDate.before(currentDate)) {
			bt_confirm.setBackgroundResource(R.drawable.gray2_corners2_icon);
			bt_confirm.setEnabled(false);
		} else {
			bt_confirm.setBackgroundResource(R.drawable.blue2_corners2_icon);
			bt_confirm.setEnabled(true);
		}
	}

	public Button getConfirmButton() {
		return bt_confirm;
	}
	public Button getCancelButton() {
		return bt_cancel;
	}
}
