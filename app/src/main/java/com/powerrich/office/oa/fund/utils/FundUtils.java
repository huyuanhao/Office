package com.powerrich.office.oa.fund.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.powerrich.office.oa.bean.FundInfo;
import com.powerrich.office.oa.bean.RepaymentPlanInfo;
import com.powerrich.office.oa.fund.bean.BaseFund;
import com.powerrich.office.oa.fund.bean.LoanAccountInfo;
import com.powerrich.office.oa.tools.GsonUtil;
import com.yt.simpleframe.http.bean.ReFundYQHKInfoBean;
import com.yt.simpleframe.http.bean.entity.DkDetailInfo;
import com.yt.simpleframe.http.bean.entity.ReFundJkrgxInfo;
import com.yt.simpleframe.http.bean.entity.ReFundYQCXInfo;
import com.yt.simpleframe.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlienChao
 * @date 2019/04/25 15:42
 */
public class FundUtils {
    //公积金账户信息查询
    private static final String GJJZH_INFO_KEY= "GJJZH_INFO_KEY";
    //贷款账户信息查询
    private static final String DKINFO_KEY= "DKINFO_KEY";
    //贷款还款计划
    private static final String DKHKJH_INFO_KEY= "DKHKJH_INFO_KEY";
    //8逾期未还款明细查询
    private static final String DKYQWHK_INFO_KEY= "DKYQWHK_INFO_KEY";
    //借款人关系查询
    private static final String JKRGX_INFO_KEY= "JKRGX_INFO_KEY";
    //贷款还款明细查询
    private static final String DKHK_INFO_KEY= "DKHKMX_INFO_KEY";

    private static class SingletonHolder {
        private static final FundUtils INSTANCE = new FundUtils();
    }

//    private FundUtils() {
//        throw new IllegalStateException("请获取FundUtils的单例方法去操作，谢谢Thanks♪(･ω･)ﾉ");
//    }

    public static FundUtils getInstance(){
        return SingletonHolder.INSTANCE;
    }



    /**
     * 2公积金账户信息查询 保存
     */
    public  void saveFundGjjZhInfo(Context context,String jsonStr){
        SharedPreferencesUtils.setParam(context,GJJZH_INFO_KEY,jsonStr);
    }
    /**
     * 2公积金账户信息查询  得到
     */
    public FundInfo.DATABean getFundGjjZhInfo(Context context){
        String s = (String) SharedPreferencesUtils.getParam(context, GJJZH_INFO_KEY, "");

        FundInfo fundInfo = GsonUtil.GsonToBean(s, FundInfo.class);

        if(null ==fundInfo||fundInfo.getDATA().size()==0){
            return new FundInfo.DATABean();
        }

        return fundInfo.getDATA().get(0);
    }




    /**
     * 保存贷款账户信息查询 保存
     */
    public  void saveFundInfo(Context context,String jsonStr){
        SharedPreferencesUtils.setParam(context,DKINFO_KEY,jsonStr);
    }
    /**
     * 保存贷款账户信息查询  得到
     */
    public  LoanAccountInfo getFundDkInfo(Context context){
        String s = (String) SharedPreferencesUtils.getParam(context, DKINFO_KEY, "");
        BaseFund<LoanAccountInfo> fundInfo  = new Gson().fromJson(s, new TypeToken<BaseFund<LoanAccountInfo>>() {
        }.getType());
        if(null ==fundInfo||fundInfo.getDATA().size()==0){
            return new LoanAccountInfo();
        }
        //如果给你不止一个信息过来遍历 选择正常的状态返回
        if(fundInfo.getDATA().size()>1){
            for (int i = 0; i <fundInfo.getDATA().size() ; i++) {
                LoanAccountInfo loanAccountInfo = fundInfo.getDATA().get(i);
                if("正常还款".equals(loanAccountInfo.getDkzt())){
                    return  loanAccountInfo;
                }
            }
        }

        return fundInfo.getDATA().get(0);
    }



    /**
     * 保存贷款还款计划信息 保存
     */
    public  void saveFundHkjhInfo(Context context,String jsonStr){
        SharedPreferencesUtils.setParam(context,DKHKJH_INFO_KEY,jsonStr);
    }
    /**
     * 保存贷款还款计划信息 得到
     */
    public  RepaymentPlanInfo.DATABean getFundHkjhInfo(Context context){
        String s = (String) SharedPreferencesUtils.getParam(context, DKHKJH_INFO_KEY, "");

        RepaymentPlanInfo repaymentPlanInfo = GsonUtil.GsonToBean(s, RepaymentPlanInfo.class);


        if(null ==repaymentPlanInfo||repaymentPlanInfo.getDATA().size()==0){
            return new RepaymentPlanInfo.DATABean();
        }

        return repaymentPlanInfo.getDATA().get(0);
    }



    /**
     * 8逾期未还款明细查询 保存
     */
    public  void saveFundYqwhkInfo(Context context,String jsonStr){
        SharedPreferencesUtils.setParam(context,DKYQWHK_INFO_KEY,jsonStr);
    }
    /**
     * 8逾期未还款明细查询  得到
     */
    public ReFundYQCXInfo getFundYqwhkInfo(Context context){
        String s = (String) SharedPreferencesUtils.getParam(context, DKYQWHK_INFO_KEY, "");


//        List<ReFundYQCXInfo> reFundYQCXInfos = new Gson().fromJson(s, new TypeToken<List<ReFundYQCXInfo>>() {
//        }.getType());

        List<ReFundYQCXInfo> reFundYQCXInfos = GsonUtil.jsonToList(s, ReFundYQCXInfo.class);


        if(null ==reFundYQCXInfos||reFundYQCXInfos.size()==0){
            return new ReFundYQCXInfo();
        }

        return reFundYQCXInfos.get(0);
    }

    /**
     * 借款人关系查询 保存
     */
    public  void saveFundJkrgxInfo(Context context,String jsonStr){
        SharedPreferencesUtils.setParam(context,JKRGX_INFO_KEY,jsonStr);
    }
    /**
     * 借款人关系查询  得到
     */
    public ReFundJkrgxInfo getFundJkrgxInfo(Context context){
        String s = (String) SharedPreferencesUtils.getParam(context, JKRGX_INFO_KEY, "");

        List<ReFundYQCXInfo> reFundYQCXInfos = GsonUtil.jsonToList(s, ReFundYQCXInfo.class);


        if(null ==reFundYQCXInfos||reFundYQCXInfos.size()==0){
            return new ReFundJkrgxInfo();
        }

        return new ReFundJkrgxInfo();
    }
    /**
     * 贷款还款明细查询 保存
     */
    public void saveFundDkmxInfo(Context context,String jsonStr){
        SharedPreferencesUtils.setParam(context,DKHK_INFO_KEY,jsonStr);
    }
    /**
     * 贷款还款明细查询  得到
     */
    public ReFundJkrgxInfo getFundDkmxInfo(Context context){
        String s = (String) SharedPreferencesUtils.getParam(context, DKHK_INFO_KEY, "");

        List<DkDetailInfo> dkDetailInfos = GsonUtil.jsonToList(s, DkDetailInfo.class);


        if(null ==dkDetailInfos||dkDetailInfos.size()==0){
            return new ReFundJkrgxInfo();
        }

        return new ReFundJkrgxInfo();
    }


}
