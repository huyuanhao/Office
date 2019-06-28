package com.powerrich.office.oa.tools;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Parcel;

import com.imnjh.imagepicker.FileChooseInterceptor;
import com.imnjh.imagepicker.PickerAction;
import com.powerrich.office.oa.R;

/**
 * Created by Martin on 2017/1/18.
 * 图片过滤选择器
 */

public class SingleFileLimitInterceptor implements FileChooseInterceptor {

  private static final long MAX_FILE_SIZE_ORIGINAL = 1000 * 1024; // 200K

  public SingleFileLimitInterceptor() {}

  @Override
  public boolean onFileChosen(Context context, ArrayList<String> selectedPic,
      boolean original,
      int resultCode, PickerAction action) {
    if (resultCode != Activity.RESULT_OK) {
      return true;
    }
    if (original) {
      ArrayList<String> confirmedFiles = new ArrayList<>();
      for (String filePath : selectedPic) {
        File file = new File(filePath);
        if (file.exists()) {
          if (file.length() <= MAX_FILE_SIZE_ORIGINAL) {
            confirmedFiles.add(filePath);
          }
        }
      }
      if (confirmedFiles.size() < selectedPic.size()) {
        showSingleFileLimitDialog(context, original, resultCode, action, confirmedFiles);
        return false;
      }
    }
    return true;
  }

  private void showSingleFileLimitDialog(Context context, final boolean original,
      final int resultCode,
      final PickerAction action, final ArrayList<String> confirmedFiles) {
    new AlertDialog.Builder(context)
        .setMessage("选择图片过大")
        .setPositiveButton(
            R.string.general_ok, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                action.proceedResultAndFinish(confirmedFiles, original, resultCode);
              }
            })
        .setNegativeButton(R.string.general_cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {}
        })
        .show();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {

  }

  protected SingleFileLimitInterceptor(Parcel in) {}

  public static final Creator<SingleFileLimitInterceptor> CREATOR =
      new Creator<SingleFileLimitInterceptor>() {
        @Override
        public SingleFileLimitInterceptor createFromParcel(Parcel source) {
          return new SingleFileLimitInterceptor(source);
        }

        @Override
        public SingleFileLimitInterceptor[] newArray(int size) {
          return new SingleFileLimitInterceptor[size];
        }
      };
}
