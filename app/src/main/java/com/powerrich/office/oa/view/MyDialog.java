package com.powerrich.office.oa.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.tools.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * huyuanhao
 * 弹出框
 */

public class MyDialog implements DialogInterface {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private ImageView line_title;
    private ImageView line_button;
    private TextView txt_msg;
    private Button btn_neg;
    private Button btn_pos;
    private ListView listView;
    private ImageView img_line;
    private ProgressBar pb_loading_px;
    private LinearLayout llt_bt;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private int mGravity = Gravity.CENTER;


    public interface InterfaceClickCc {
        void onRightClick();
        void onLeftClick();

    }


    public interface InterfaceClick {
        void click();
    }

    private static MyDialog myDialog;

    public static void showDialog(Context context, String title, String msg, final InterfaceClick click) {
        if (myDialog != null && myDialog.isShowing()) {
            return;
        }
        myDialog = new MyDialog(context).builder().setTitle(title)
                .setMessage(msg);
        myDialog.setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDialog.dismiss();
                click.click();
            }
        }).setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDialog.dismiss();
            }
        });

        myDialog.show();
    }


    public static void showDialogCc(Context context, String msg, final InterfaceClick click) {
        if (myDialog != null && myDialog.isShowing()) {
            return;
        }
        myDialog = new MyDialog(context).builder()
                .setMessage(msg);
        myDialog.setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDialog.dismiss();
                click.click();
            }
        });

        myDialog.show();
    }


    public static MyDialog showWaitDialog(Context context, String title, String msg) {
        if (myDialog != null && myDialog.isShowing()) {
            return myDialog;
        }
        myDialog = new MyDialog(context).builder().setTitle(title)
                .setMessage(msg);
        myDialog.showPg();
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();

        return myDialog;
    }


    public static void showDialogButtonStr(Context context, String title, String msg, String confirmStr, String cacelStr, final
    InterfaceClickCc click) {
        if (myDialog != null && myDialog.isShowing()) {
            return;
        }
        myDialog = new MyDialog(context).builder().setTitle(title)
                .setMessage(msg);
        myDialog.setPositiveButtonCc(confirmStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDialog.dismiss();
                click.onRightClick();
            }
        }).setNegativeButton(cacelStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDialog.dismiss();
                click.onLeftClick();
            }
        });

        myDialog.show();
    }

    /**
     * 可以更改button颜色
     * @param context
     * @param title
     * @param msg
     * @param confirmStr
     * @param cacelStr
     * @param click
     */
    public static void showDialogCusColor(Context context, String title, String msg, String confirmStr, int confirmColor,String cacelStr,int cacelColor, final
    InterfaceClickCc click) {
        if (myDialog != null && myDialog.isShowing()) {
            return;
        }
        myDialog = new MyDialog(context).builder().setTitle(title)
                .setMessage(msg);
        myDialog.setPositiveCusColor(confirmStr, confirmColor,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDialog.dismiss();
                click.onRightClick();
            }
        }).setNegativeCusColor(cacelStr, cacelColor ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDialog.dismiss();
                click.onLeftClick();
            }
        });

        myDialog.show();
    }


    public MyDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public MyDialog(Context context, int gravity) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        this.mGravity = gravity;
    }

    public MyDialog builder() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_alertdialog, null);
        AutoUtils.auto(view);
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        pb_loading_px = (ProgressBar) view.findViewById(R.id.pb_loading_px);
        llt_bt = (LinearLayout) view.findViewById(R.id.llt_bt);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        line_title = (ImageView) view.findViewById(R.id.line_title);
        line_title.setVisibility(View.GONE);
        line_button = (ImageView) view.findViewById(R.id.line_button);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);
        listView = (ListView) view.findViewById(R.id.list);
        listView.setVisibility(View.GONE);

        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(mGravity);

        if (mGravity == Gravity.BOTTOM) {
            lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                    .getWidth() * 1), FrameLayout.LayoutParams.WRAP_CONTENT));
        } else {
            lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                    .getWidth() * 0.8), FrameLayout.LayoutParams.WRAP_CONTENT));
        }

        return this;
    }

    public MyDialog setTitle(int textId) {
        return setTitle(context.getString(textId));
    }

    public void showPg() {
        pb_loading_px.setVisibility(View.VISIBLE);
        llt_bt.setVisibility(View.GONE);
        line_button.setVisibility(View.GONE);
    }

    public MyDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }
    public MyDialog setTitleGon() {
        showTitle = false;

        return this;
    }


    public MyDialog setMessage(int textId) {
        return setMessage(context.getString(textId));
    }

    public MyDialog setMessage(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public MyDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public void setCanceledOnTouchOutside(boolean b) {
        dialog.setCanceledOnTouchOutside(b);
    }

    public MyDialog setPositiveButton(int textId, final OnClickListener listener) {
        return setPositiveButton(context.getString(textId), listener);
    }

    public MyDialog setPositiveButton(String text,
                                      final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dialog, 0);
                dialog.dismiss();
            }
        });
        return this;
    }

    public MyDialog setPositiveButtonCc(String text,
                                      final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
            btn_pos.setTextColor(context.getResources().getColor(R.color.cancal));
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dialog, 0);
//                dialog.dismiss();
            }
        });
        return this;
    }





    public MyDialog setNegativeButton(int textId, final OnClickListener listener) {
        return setNegativeButton(context.getString(textId), listener);
    }
    public MyDialog setPositiveCusColor(String text,int color,
                                        final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
            btn_pos.setTextColor(color);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dialog, 0);
                dialog.dismiss();
            }
        });
        return this;
    }

    public MyDialog setNegativeCusColor(String text,int color,
                                      final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
            btn_neg.setTextColor(color);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(dialog, 0);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public MyDialog setNegativeButton(String text,
                                      final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(dialog, 0);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    //list弹出框
    public MyDialog setSingleChoiceItems(CharSequence[] items, int checkedItem, final OnClickListener listener) {
        txt_msg.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        List list = new ArrayList<StringInfo>();
        for (int i = 0; i < items.length; i++) {
            list.add(new StringInfo(items[i].toString(), false));
        }
        final ListAdapter listAdapter = new ListAdapter(context);
        listAdapter.setList(list);
        listView.setAdapter(listAdapter);
        if (checkedItem >= 0 && checkedItem < items.length) {
            listAdapter.select(checkedItem);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listAdapter.select(i);
                listener.onClick(dialog, i);
            }
        });
        return this;
    }


    boolean TitleGone = false;//设置标题为空

    public MyDialog SetTitleGone(boolean b) {
        this.TitleGone = b;
        return this;
    }

    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
            line_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (TitleGone) {
            txt_title.setVisibility(View.GONE);
            line_title.setVisibility(View.GONE);
        }

        if (!showPosBtn && !showNegBtn) {
            if (mGravity == Gravity.BOTTOM) {
                btn_pos.setText("取消");
            } else {
                btn_pos.setText("确定");
            }
            btn_pos.setVisibility(View.VISIBLE);
//          btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
//          btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
//          btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
//          btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
//          btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (btn_neg.getText().toString().equals("取消")) {
            btn_neg.setTextColor(Color.parseColor("#999999"));
        }

        if (btn_pos.getText().toString().equals("取消")) {
            btn_pos.setTextColor(Color.parseColor("#999999"));
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void setGravity(int gravity) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    @Override
    public void cancel() {
        dialog.cancel();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    public class StringInfo {
        private String title;
        private boolean selected;

        public StringInfo(String title, boolean selected) {
            this.title = title;
            this.selected = selected;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    public class ListAdapter extends BaseAdapter {

        Context context;
        LayoutInflater mInflater;
        ViewHolder holder;
        List<StringInfo> list;
        StringInfo stringInfo;

        public ListAdapter(Context context) {
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
        }

        public void setList(List<StringInfo> list) {
            this.list = list;
        }

        // 选中当前选项时，让其他选项不被选中
        public void select(int position) {
            if (!list.get(position).isSelected()) {
                list.get(position).setSelected(true);
                for (int i = 0; i < list.size(); i++) {
                    if (i != position) {
                        list.get(i).setSelected(false);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public StringInfo getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.dialog_list, null);
//                holder.radioBtn = (RadioButton) convertView
//                        .findViewById(R.id.radioButton);
//                holder.radioBtn.setClickable(false);
                holder.textView = (TextView) convertView
                        .findViewById(R.id.tv_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            stringInfo = getItem(position);
//            holder.radioBtn.setChecked(book.isSelected());
            holder.textView.setText(stringInfo.getTitle());
            if (stringInfo.isSelected()) {
                holder.textView.setTextColor(Color.parseColor("#037BFF"));
            } else {
                holder.textView.setTextColor(Color.parseColor("#000000"));
            }
            return convertView;
        }

        class ViewHolder {
            RadioButton radioBtn;
            TextView textView;
        }

    }

}
