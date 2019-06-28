package com.powerrich.office.oa.fund.view;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.powerrich.office.oa.fund.view.CInterface.IGenerateUI;
import com.powerrich.office.oa.fund.view.entity.Prepayment;
import com.powerrich.office.oa.fund.view.widget.CcEditText;
import com.powerrich.office.oa.fund.view.widget.CcShowText;
import com.powerrich.office.oa.fund.view.widget.CcTextView;

import java.util.List;

/**
 * 生成View
 * @author AlienChao
 * @date 2019/04/19 11:07
 */
public class GenerateUtil {
    private LinearLayout linearLayout;

    private IGenerateUI mIGenerateUI;

    public void setIGenerateUI(IGenerateUI IGenerateUI) {
        mIGenerateUI = IGenerateUI;
    }

    public void startUI(Context context, List<Prepayment> prepaymentList){
        linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundColor(Color.parseColor("#F4F5F9"));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < prepaymentList.size(); i++) {
            Prepayment prepayment = prepaymentList.get(i);
            switch (prepayment.getType()){
                case WidgetType.CcTextView:
                    CcTextView ccTextView = new CcTextView(context);
                    ccTextView.setInit(prepayment);
                    linearLayout.addView(ccTextView);
                    break;
                case WidgetType.CcEditView:
                    CcEditText ccEditText = new CcEditText(context);
                    ccEditText.setInit(prepayment);
                    linearLayout.addView(ccEditText);
                    break;
               case WidgetType.CcSmallTitleView:
                   CcShowText ccShowText = new CcShowText(context,prepayment.getType());
                   ccShowText.setInit(prepayment);
                   linearLayout.addView(ccShowText);
                    break;
            }


        }
        
        
        
        
        mIGenerateUI.onStartUISuceess(linearLayout);
        
    }

}
