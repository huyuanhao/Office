package com.powerrich.office.oa.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.powerrich.office.oa.R
import com.powerrich.office.oa.adapter.SearchMoreRecycleAdapter
import com.powerrich.office.oa.adapter.SearchRecycleAdapter
import com.powerrich.office.oa.api.OAInterface
import com.powerrich.office.oa.base.BaseActivity
import com.powerrich.office.oa.base.BaseRequestCallBack
import com.powerrich.office.oa.bean.SearchBean
import com.powerrich.office.oa.common.ResultItem
import com.powerrich.office.oa.network.http.HttpResponse
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener
import kotlinx.android.synthetic.main.activity_search_more.*
import kotlinx.android.synthetic.main.common_title_bar.*
import java.util.ArrayList
import com.powerrich.office.oa.R.id.recyclerView
import com.powerrich.office.oa.bean.UserInfo
import com.powerrich.office.oa.tools.*


/**
 * 搜索更多
 */
class SearchMoreActivity : BaseActivity() {
    private var currentPage: Int = 1
    //搜索的内容
    private var content: String? = null
    //搜索类型
    private var contentType: String? = null
    private var searchMoreRecycleAdapter: SearchMoreRecycleAdapter? = null
    private var arrayList: ArrayList<SearchBean> = ArrayList()
    private val REQUEST_CODE = 111

    override fun provideContentViewId(): Int {
        return R.layout.activity_search_more
    }

//    private var stringList: MutableList<SearchBean>? = null
//    private fun aa() {
//        stringList = ArrayList()
//        stringList!!.add(SearchBean())
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initIntent()
        initView()
        initData();
        initEvent()
    }


    private fun initView() {
        system_back.visibility = View.VISIBLE
        tv_top_title.text = contentType + "-" + content
        rv_search_more.layoutManager = LinearLayoutManager(this)
        searchMoreRecycleAdapter = SearchMoreRecycleAdapter()
        rv_search_more.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_search_more.adapter = searchMoreRecycleAdapter
    }

    private fun initIntent() {
        content = intent.getStringExtra("CONTENT");
        contentType = intent.getStringExtra("CONTENTTYPE");

    }


    private fun initData() {
        invoke.invoke(OAInterface.searchMore(content, currentPage.toString()), callBack)
    }

    private val callBack = object : BaseRequestCallBack() {
        override fun process(response: HttpResponse?, what: Int) {


            layout_refresh.finishLoadmore()
            layout_refresh.finishRefresh()

            val item = response?.getResultItem(ResultItem::class.java)
            val code = item?.getString("code")
            val message = item?.getString("message")
            if (Constants.SUCCESS_CODE == code) {
                //   var item_data = item.get("DATA") as ResultItem
                var item_data = item.getItems("DATA")
                //无数据
                if (currentPage == 1 && BeanUtils.isEmpty(item_data)) {
                    rl_no_data.visibility = View.VISIBLE
                    layout_refresh.visibility = View.GONE
                } else {
                    // 下一页后没数据
                    if (currentPage > 1 && BeanUtils.isEmpty(item_data)) {
                        layout_refresh.setLoadmoreFinished(true)
                    }

                    rl_no_data.visibility = View.GONE
                    layout_refresh.visibility = View.VISIBLE

                    for (item: ResultItem in item_data) {
                        val searchBean = SearchBean()
                        searchBean.itemId = item.getString("ID")
                        searchBean.itemName = item.getString("ITEMNAME")
                        searchBean.shortName = item.getString("SHORT_NAME")
                        searchBean.siteNo = item.getString("SITENO")
                        searchBean.normacceptdepart = item.getString("NORMACCEPTDEPART")
                        arrayList?.add(searchBean)
                    }

                    searchMoreRecycleAdapter?.setData(arrayList)
                }
            } else {
                DialogUtils.showToast(this@SearchMoreActivity, message)
            }
        }


    }

    private fun initEvent() {

        system_back.setOnClickListener {
            finish()
        }
        searchMoreRecycleAdapter?.setiOnClick {
            var searchBean: SearchBean = arrayList.get(it)
            if (contentType.equals("在线办事")) {
                zxIntent(searchBean?.itemId)
            } else if (contentType.equals("预约办事")) {
                val intent = Intent(this@SearchMoreActivity, OnlineBookingHallActivity::class.java)
                intent.putExtra("NORMACCEPTDEPART", searchBean?.getNormacceptdepart())
                intent.putExtra("ITEMNAME", searchBean?.getItemName())
                intent.putExtra("ITEMID", searchBean?.getItemId())
                intent.putExtra("SITENO", searchBean?.getSiteNo())
                startActivity(intent)
            } else {
                val intent = Intent()
                intent.setClass(this@SearchMoreActivity, WorkGuideNewActivity::class.java)
                intent.putExtra("item_id", searchBean?.getItemId())
                intent.putExtra("item_name", searchBean?.getItemName())
                intent.putExtra("UNITSTR", searchBean?.normacceptdepart)
              //  intent.putExtra("type", Constants.PERSONAL_WORK_TYPE)
                startActivity(intent)
            }

        }


        layout_refresh.setOnRefreshLoadmoreListener(object : OnRefreshLoadmoreListener {
            override fun onLoadmore(p0: RefreshLayout?) {
                currentPage++
                initData()
            }

            override fun onRefresh(p0: RefreshLayout?) {
                currentPage = 1
                layout_refresh.setLoadmoreFinished(false)
                arrayList.clear()
                initData()

            }

        })

    }


    /**
     * 在线跳转
     */
    private fun zxIntent(item_id: String?) {
        Log.i("jsc", "item_id:" + item_id)
        if (LoginUtils.getInstance().isLoginSuccess) {
            // 判断用户是否实名认证进行办事事项的拦截
            if (VerificationUtils.isAuthentication(this@SearchMoreActivity)) {
            //    val itemType = if ("0" == LoginUtils.getInstance().userInfo.userType) Constants.PERSONAL_WORK_TYPE else Constants.COMPANY_WORK_TYPE
                val companyInfoList = LoginUtils.getInstance().userInfo.data.companylist
                gotoDeclareNotice(Constants.PERSONAL_WORK_TYPE , companyInfoList, item_id!!)
            }
        } else {
            // 没有登录则让用户先去登录
            LoginUtils.getInstance().checkNeedLogin(this@SearchMoreActivity, true, getString(R.string.declare_tips), REQUEST_CODE)
        }
    }


    /**
     * 跳转到申报须知界面
     */
    private fun gotoDeclareNotice(itemType: String, companyInfoList: List<UserInfo.CompanyInfo>, item_id: String) {
        val intent = Intent(this@SearchMoreActivity, DeclareNoticeActivity::class.java)
        intent.putExtra("type",itemType )
        intent.putExtra("item_id", item_id)
        intent.putExtra("position", "0")
        intent.putExtra("companyId", if (BeanUtils.isEmpty(companyInfoList)) "" else companyInfoList[0].id)
        startActivity(intent)
    }


}
