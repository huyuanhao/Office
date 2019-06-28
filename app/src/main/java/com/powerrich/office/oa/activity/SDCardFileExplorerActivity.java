package com.powerrich.office.oa.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.FileUtils;
import com.powerrich.office.oa.view.TopActivity;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 类描述：<br>
 * 附件文件浏览器
 *
 * @version v1.0
 */
public class SDCardFileExplorerActivity extends BaseActivity {

    public static final int REQUEST_CODE_FILE = 200;

    private TopActivity topActivity;        // 标题栏

    private TextView tvpath;
    private ListView lvFiles;

    private File currentParent;        // 记录当前的父文件夹
    private File[] currentFiles;    // 记录当前路径下的所有文件夹的文件数组


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topActivity = (TopActivity) findViewById(R.id.top_activity);
        topActivity.setTopTitle("选择文件");
        topActivity.setBtnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 获取系统的内置SDCard的目录
                    String sdString = Constants.SDString;
                    //判断外置sd卡目录
                    String sdcardPath = getPath_env();//Constants.SDCARDString;
                    if (!currentParent.getCanonicalPath().equals(sdString) && !currentParent.getCanonicalPath().equals(sdcardPath)) {
                        // 获取上一级目录
                        currentParent = currentParent.getParentFile();
                        //根据当前目录的位置设置标题栏的“返回”文字
                        setBtnBackText(topActivity, currentParent);
                        // 列出当前目录下的所有文件
                        currentFiles = currentParent.listFiles();
                        // 再次更新ListView
                        inflateListView(currentFiles);
                    } else {
                        //判断是否是内置sd卡，如果是，则进行判断是否存在外置sd卡
                        if (!currentParent.getCanonicalPath().equals(sdString)) {
                            File root = new File(sdcardPath);
                            // 如果外置SD卡存在的话，获取外置sd卡的路径
                            if (root != null && root.listFiles().length > 0) {
                                currentParent = root;
                                //根据当前目录的位置设置标题栏的“返回”文字
                                setBtnBackText(topActivity, currentParent);
                                currentFiles = root.listFiles();
                                // 使用当前目录下的全部文件、文件夹来填充ListView
                                inflateListView(currentFiles);
                            } else {
                                finish(); //关闭当前选择器
                            }
                        } else {
                            finish();    //关闭当前选择器
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        topActivity.setRightBtnVisibility(View.INVISIBLE);

        lvFiles = (ListView) this.findViewById(R.id.lv_files);
        lvFiles.setDivider(null);
        lvFiles.setCacheColorHint(Color.TRANSPARENT);
        lvFiles.setFadingEdgeLength(0);
        lvFiles.setSelector(android.R.color.transparent);

        tvpath = (TextView) this.findViewById(R.id.tv_path);

        // 获取系统的SDCard的目录
        String sdcardPath = Constants.SDString;
        File root = new File(sdcardPath);
        // 如果SD卡存在的话
        if (root.exists()) {
            currentParent = root;
            //根据当前目录的位置设置标题栏的“返回”文字
            setBtnBackText(topActivity, currentParent);
            currentFiles = root.listFiles();
            // 使用当前目录下的全部文件、文件夹来填充ListView
            inflateListView(currentFiles);
        }

        lvFiles.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 如果用户单击了文件，直接返回，不做任何处理
                if (currentFiles[position].isFile()) {
                    String filePath = currentFiles[position].getAbsolutePath();
                    String fileName = currentFiles[position].getName();
                    Intent intent = new Intent();
                    intent.putExtra("FILE_NAME", fileName);
                    intent.putExtra("FILE_PATH", filePath);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    // 也可自定义扩展打开这个文件等
                    return;
                }
                // 获取用户点击的文件夹 下的所有文件
                File[] tem = currentFiles[position].listFiles();
                if (tem == null || tem.length == 0) {
                    DialogUtils.showToast(SDCardFileExplorerActivity.this, "当前路径不可访问或者该路径下没有文件");

                } else {
                    // 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
                    currentParent = currentFiles[position];
                    //根据当前目录的位置设置标题栏的“返回”文字
                    setBtnBackText(topActivity, currentParent);
                    // 保存当前的父文件夹内的全部文件和文件夹
                    currentFiles = tem;
                    // 再次更新ListView
                    inflateListView(currentFiles);
                }
            }
        });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.sdcard_file_explorer;
    }

    //根据当前目录的位置设置标题栏的“返回”文字
    private void setBtnBackText(TopActivity topActivity, File filePath) {
        try {
            if (topActivity == null) {
                return;
            }
            if (filePath == null) {
                topActivity.setBtnBackText("返回");
                return;
            }
            // 获取系统的内置SDCard的目录
            String sdString = Constants.SDString;
            //判断外置sd卡目录
            String sdcardPath = getPath_env();//Constants.SDCARDString;
            if (filePath.getCanonicalPath().equals(sdString)) {
                topActivity.setBtnBackText("内部存储");
                return;
            }
            if (filePath.getCanonicalPath().equals(sdcardPath)) {
                topActivity.setBtnBackText("SD卡");
                return;
            }
            topActivity.setBtnBackText("上级目录");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文件夹填充ListView
     *
     * @param files
     */
    private void inflateListView(File[] files) {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            if (files[i].isDirectory()) {
                // 如果是文件夹就显示的图片为文件夹的图片
                listItem.put("icon", R.drawable.ic_folder);    //文件夹图片
            } else {
                String fileName = files[i].getName();
                String extend = FileUtils.getFileExtends(fileName);
                if ("jpg".equals(extend) || "png".equals(extend)) {
                    listItem.put("icon", R.drawable.ic_jpg);
                } else if ("doc".equals(extend) || "docx".equals(extend)) {
                    listItem.put("icon", R.drawable.ic_doc);
                } else if ("pdf".equals(extend)) {
                    listItem.put("icon", R.drawable.ic_pdf);
                } else if ("ppt".equals(extend) || "pdfx".equals(extend)) {
                    listItem.put("icon", R.drawable.ic_ppt);
                } else if ("txt".equals(extend)) {
                    listItem.put("icon", R.drawable.ic_txt);
                } else {
                    listItem.put("icon", R.drawable.ic_document);    //未知文件
                }
            }
            // 添加一个文件名称
            listItem.put("filename", files[i].getName());

            File myFile = new File(files[i].getName());
            //文件大小
            long fileSize = myFile.length();
            String fileKb = bytes2kb(fileSize);
            listItem.put("filesize", fileKb);

            listItems.add(listItem);
        }

        // 定义一个SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(this, listItems,
                R.layout.sdcard_file_list_item,
                new String[]{"filename", "icon", "filesize"},
                new int[]{R.id.file_name, R.id.icon, R.id.file_size});

        // 填充数据集
        lvFiles.setAdapter(adapter);

        try {
            tvpath.setText(currentParent.getCanonicalPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1) {
            return (returnValue + "MB");
        }
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }

    /**
     * 获取外置的sd卡路径
     *
     * @return String [返回类型说明]
     */
    private String getPath_env() {
        Map<String, String> map = System.getenv();

        // 遍历出来可以看到最后一项是外置SD卡路径
        String innerPath = "";
        String exterPath = "";
        boolean hasExter = false;

        Set<String> set = map.keySet();
        Iterator<String> keys = set.iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = map.get(key);
            Log.i("pop", key + ":" + value);

            if ("SECONDARY_STORAGE".equals(key)) {
                hasExter = true;
                exterPath = value;
            }

            if ("EXTERNAL_STORAGE".equals(key)) {
                innerPath = value;
            }
        }

        if (hasExter) {
            return exterPath;
        }
        return innerPath;
    }
}