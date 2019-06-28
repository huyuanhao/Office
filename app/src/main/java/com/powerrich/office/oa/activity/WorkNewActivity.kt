package com.powerrich.office.oa.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.powerrich.office.oa.R
import com.powerrich.office.oa.adapter.WorkPopAdapter
import com.powerrich.office.oa.adapter.WorkRecycleAdapter
import com.powerrich.office.oa.api.OAInterface
import com.powerrich.office.oa.base.BaseActivity
import com.powerrich.office.oa.base.BaseRequestCallBack
import com.powerrich.office.oa.bean.AreaBean
import com.powerrich.office.oa.bean.ItemsInfo
import com.powerrich.office.oa.bean.UserInfo
import com.powerrich.office.oa.bean.WorkNew
import com.powerrich.office.oa.common.ResultItem
import com.powerrich.office.oa.network.http.HttpResponse
import com.powerrich.office.oa.tools.*
import com.powerrich.office.oa.view.MyMaxHeightListView
import com.powerrich.office.oa.view.VerifyWorkDialog
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener
import kotlinx.android.synthetic.main.activity_work_new.*
import kotlinx.android.synthetic.main.common_title_bar.*
import java.util.*

/**
 * 办事指南 - 新界面
 */
class WorkNewActivity : BaseActivity(), View.OnClickListener {
    override fun provideContentViewId(): Int {
        return R.layout.activity_work_new
    }


    // 部门
    private val GET_SERVICE_ITEMS_REQ = 0
    private val GET_ITEM_REQ = 1
    // 主题
    private val SITE_LIST_REQ = 2

    private val GET_SITE_ITEM_LIST_REQ = 3
    //地区
    private val GET_AREA_REQ = 4

    //事项
    private val GET_EVENT_REQ = 5
    //在线事项
    private val GET_EVENT_REQ_ONLINE = 6
    private var animation: RotateAnimation? = null
    private var reverseAnimation: RotateAnimation? = null
    private var popupWindow: PopupWindow? = null
    private var listView: MyMaxHeightListView? = null
    private var workPopAdapter: WorkPopAdapter? = null
    private var currentPage = 1


    private var isCityShow: Boolean = false
    private var isDepartmentShow: Boolean = false
    private var isThemeShow: Boolean = false


    private var indexCityShow: Int = 0
    private var indexDepartmentShow: Int = 0
    private var indexThemeShow: Int = 0


    //department
    private var stringListCity = java.util.ArrayList<String>()
    private var stringListDepartment = java.util.ArrayList<String>()
    private var stringListTheme = java.util.ArrayList<String>()

    //主题集合
    private val departmentBeanList = ArrayList<ItemsInfo>()
    //部门集合
    private val themeBeanList = ArrayList<ItemsInfo>()
    //地区集合
    private val areaBeanList = ArrayList<AreaBean>()
    //事项集合
    private val workNewBeanList = ArrayList<WorkNew>()


    private var workRecycleAdapter: WorkRecycleAdapter? = null
//    // 1  个人   2 企业办事
//    private var title: String? = null

    //判断首页点击的是哪个办事  type类型1.个人办事 2.企业办事
    private var type: String? = null
    // 1  在线办事   2 预约办事 3 办事指南
    private var typeChild: String? = null
    //内容
    private var content: String = ""
    //区划编码，
    private var region_code: String = ""
    //办理单位
    private var normacceptdepartid: String = ""
    //主题id
    private var tagid: String = ""

    private val REQUEST_CODE = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        isAutoFlag = false
        super.onCreate(savedInstanceState)
        AutoUtils.auto(title_layout)

        type = intent.getStringExtra("type")
        typeChild = intent.getStringExtra("typeChild")


        //  initAnimo()
        initView()
        initData()
        initEvent()

    }


    //点击布局
    override fun onClick(v: View?) {
        backgroundAlpha(0.35F)
        if (v?.id == R.id.ll_city) {
//            iv_city.clearAnimation()
//            iv_city.startAnimation(animation)
            iv_city.setImageResource(R.drawable.work_up)
            workPopAdapter?.setData(stringListCity, indexCityShow)
            popupWindow?.showAsDropDown(view_spinner_below)
            isCityShow = true
            tv_city.setTextColor(resources.getColor(R.color.app_work_select))
            tv_department.setTextColor(resources.getColor(R.color.app_work_no_select))
            tv_theme.setTextColor(resources.getColor(R.color.app_work_no_select))

        } else if (v?.id == R.id.ll_department) {
//            iv_department.clearAnimation()
//            iv_department.startAnimation(animation)
            iv_department.setImageResource(R.drawable.work_up)
            workPopAdapter?.setData(stringListDepartment, indexDepartmentShow)
            popupWindow?.showAsDropDown(view_spinner_below)
            isDepartmentShow = true
            tv_city.setTextColor(resources.getColor(R.color.app_work_no_select))
            tv_department.setTextColor(resources.getColor(R.color.app_work_select))
            tv_theme.setTextColor(resources.getColor(R.color.app_work_no_select))
        } else if (v?.id == R.id.ll_theme) {
//            iv_theme.clearAnimation()
//            iv_theme.startAnimation(animation)
            iv_theme.setImageResource(R.drawable.work_up)
            workPopAdapter?.setData(stringListTheme, indexThemeShow)
            popupWindow?.showAsDropDown(view_spinner_below)
            isThemeShow = true
            tv_city.setTextColor(resources.getColor(R.color.app_work_no_select))
            tv_department.setTextColor(resources.getColor(R.color.app_work_no_select))
            tv_theme.setTextColor(resources.getColor(R.color.app_work_select))
        }

    }


    private fun initData() {

//        for (i in 1 until 5) {
//            stringListCity?.add("stringList:" + i)
//        }
//        for (i in 1 until 8) {
//            stringListDepartment?.add("stringList2:" + i)
//        }
//        for (i in 1 until 10) {
//            stringListTheme?.add("stringList3:" + i)
//        }


        getAreaItems()
        getServiceItems(type!!, "")
        getSiteList()
        layout_refresh.autoRefresh()

    }


    private fun initView() {
        et_content.clearFocus()
        system_back.visibility = View.VISIBLE
        if (typeChild == "1") {
            tv_top_title.text = "在线办事"
        } else if (typeChild == "2") {
            tv_top_title.text = "预约办事"
        } else {
            tv_top_title.text = "办事指南"
        }


        var inflate: View = View.inflate(this, R.layout.listview_work, null)

        listView = inflate.findViewById(R.id.lv_content) as MyMaxHeightListView?
        listView?.listViewHeight = 1000
        workPopAdapter = WorkPopAdapter(this)
        listView?.adapter = workPopAdapter



        popupWindow = PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        // 使其聚集
        popupWindow?.setFocusable(true)
        // 设置允许在外点击消失
        popupWindow?.setOutsideTouchable(true)
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow?.setBackgroundDrawable(BitmapDrawable())


        rv_search_more.layoutManager = LinearLayoutManager(this)
        workRecycleAdapter = WorkRecycleAdapter()
        rv_search_more.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        rv_search_more.adapter = workRecycleAdapter

    }

    private fun initEvent() {
        ll_city.setOnClickListener(this)
        ll_department.setOnClickListener(this)
        ll_theme.setOnClickListener(this)
        system_back.setOnClickListener {
            finish()
        }
        popupWindow?.setOnDismissListener {
            backgroundAlpha(1F)
            if (isCityShow) {
//                iv_city.clearAnimation()
//                iv_city.startAnimation(reverseAnimation)

                iv_city.setImageResource(R.drawable.work_down)
                tv_city.setTextColor(resources.getColor(R.color.app_work_no_select))
                isCityShow = false
            } else if (isDepartmentShow) {
//                iv_department.clearAnimation()
//                iv_department.startAnimation(reverseAnimation)
                iv_department.setImageResource(R.drawable.work_down)
                tv_department.setTextColor(resources.getColor(R.color.app_work_no_select))
                isDepartmentShow = false
            } else if (isThemeShow) {
//                iv_theme.clearAnimation()
//                iv_theme.startAnimation(reverseAnimation)
                iv_theme.setImageResource(R.drawable.work_down)
                tv_theme.setTextColor(resources.getColor(R.color.app_work_no_select))
                isThemeShow = false
            }
            popupWindow?.dismiss()

        }

        listView?.setOnItemClickListener { parent, view, position, id ->
            backgroundAlpha(1F)
            if (isCityShow) {
                indexCityShow = position
                region_code = areaBeanList?.get(position).regioN_CODE
                tv_city.text = areaBeanList?.get(position).regioN_NAME
                tv_city.setTextColor(resources.getColor(R.color.app_work_no_select))
                iv_city.setImageResource(R.drawable.work_down)

//                iv_city.clearAnimation()
//                iv_city.startAnimation(reverseAnimation)
                isCityShow = false
            } else if (isDepartmentShow) {
                normacceptdepartid = departmentBeanList?.get(position).taG_ID
                tv_department.text = departmentBeanList?.get(position).taG_NAME
                tv_department.setTextColor(resources.getColor(R.color.app_work_no_select))
                indexDepartmentShow = position
                iv_department.setImageResource(R.drawable.work_down)
//                iv_department.clearAnimation()
//                iv_department.startAnimation(reverseAnimation)
                isDepartmentShow = false
            } else if (isThemeShow) {
                tagid = themeBeanList?.get(position).taG_ID
                tv_theme.text = themeBeanList?.get(position).taG_NAME
                tv_theme.setTextColor(resources.getColor(R.color.app_work_no_select))
                indexThemeShow = position
                iv_theme.setImageResource(R.drawable.work_down)
//                iv_theme.clearAnimation()
//                iv_theme.startAnimation(reverseAnimation)
                isThemeShow = false
            }
            popupWindow?.dismiss()
            loadRefresh()
        }


        workRecycleAdapter?.setiOnClick {

            var workNew = workNewBeanList?.get(it)


            //在线办事   //预约办事
            if (typeChild == "1" || typeChild == "2") {

                //是否登录
                if (LoginUtils.getInstance().isLoginSuccess) {
                    // 判断用户是否实名认证进行办事事项的拦截


                    //判断是企业还是个人
                    val userType = LoginUtils.getInstance().userInfo.data.userduty


                    //如果用户类型是企业 且选择的是企业办事
                    if (userType.equals("1") && type == Constants.COMPANY_WORK_TYPE) {
                        val companyInfoList = LoginUtils.getInstance().userInfo.data.companylist
                        //企业个数大于1  用户自己去选择
                        if (!BeanUtils.isEmpty(companyInfoList) && companyInfoList.size > 1) {
                            gotoActivity(EnterpriseInformationActivity::class.java, Constants.COMPANY_WORK_TYPE, workNew.id, typeChild!!, workNew)
                        } else {
                            zxIntent(workNew)
                        }
                    } else {
                        zxIntent(workNew)
                    }
                } else {
                    // 没有登录则让用户先去登录
                    LoginUtils.getInstance().checkNeedLogin(this@WorkNewActivity, true, getString(R.string.declare_tips), REQUEST_CODE)
                }

                //办事指南
            } else {
                val intent = Intent()
                intent.setClass(this@WorkNewActivity, WorkGuideNewActivity::class.java)
                intent.putExtra("item_id", workNew.id)
                intent.putExtra("item_name", workNew.itemname)
                intent.putExtra("UNITSTR", workNew.normacceptdepart)
                intent.putExtra("type", type)
                intent.putExtra("typeHome", type)
                startActivity(intent)
            }


        }
        layout_refresh.setOnRefreshLoadmoreListener(object : OnRefreshLoadmoreListener {
            override fun onLoadmore(p0: RefreshLayout?) {
                //  layout_refresh.finishRefresh(1000)
                currentPage++
                if (typeChild!!.equals("1")) {
                    getOlineEventList(content, region_code, normacceptdepartid, tagid)
                } else {
                    getEventList(content, region_code, normacceptdepartid, tagid)
                }

            }

            override fun onRefresh(p0: RefreshLayout?) {
                //  layout_refresh.finishRefresh(1000)

                loadRefresh()
            }


        })

        et_content.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                (et_content.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(this@WorkNewActivity
                                .getCurrentFocus()!!.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS)
                content = et_content.getText().toString().trim({ it <= ' ' })
                layout_refresh.autoRefresh()
                Log.i("jsc", "context:" + content)
            }
            false
        })

        et_content?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 0) {
                    Log.i("jsc", "刷新")
                    content = ""
                    rl_no_data.visibility = View.GONE
                    layout_refresh.visibility = View.VISIBLE
                    layout_refresh.autoRefresh()
                }
            }

        })

    }

    private fun loadRefresh() {
        currentPage = 1
        layout_refresh.setLoadmoreFinished(false)
        workNewBeanList.clear()
        if (typeChild!!.equals("1")) {
            getOlineEventList(content, region_code, normacceptdepartid, tagid)
        } else {
            getEventList(content, region_code, normacceptdepartid, tagid)
        }
    }




    fun backgroundAlpha(bgAlpha: Float) {
        rv_search_more.alpha = bgAlpha

//        val lp = window.attributes
//        lp.alpha = bgAlpha //0.0-1.0
//        window.attributes = lp
    }


    private fun zxIntent(workNew: WorkNew) {

        var item_id: String = workNew.id
        //是否登录
        if (LoginUtils.getInstance().isLoginSuccess) {
            // 判断用户是否实名认证进行办事事项的拦截
            if (VerificationUtils.isAuthentication(this@WorkNewActivity)) {
                val userType = LoginUtils.getInstance().userInfo.data.userduty
                //如果是企业办事
                if (type == Constants.COMPANY_WORK_TYPE) {
                    //判断账号是企业还是个人

                    //当前用户为个人
                    if (userType == "0") {

                      //   DialogUtils.showToast(this@WorkNewActivity, "您是个人用户，请办理个人事项")
                        val intent = Intent()
                        intent.setClass(this@WorkNewActivity, WorkGuideNewActivity::class.java)
                        intent.putExtra("item_id", workNew.id)
                        intent.putExtra("item_name", workNew.itemname)
                        intent.putExtra("UNITSTR", workNew.normacceptdepart)
                        intent.putExtra("type", type)
                        intent.putExtra("typeHome", type)
                        startActivity(intent)
                    } else {
                        val itemType = if ("0" == LoginUtils.getInstance().userInfo.userType) Constants.PERSONAL_WORK_TYPE else Constants.COMPANY_WORK_TYPE
                        val companyInfoList = LoginUtils.getInstance().userInfo.data?.companylist
                        goIntent(companyInfoList, workNew)
                        //  gotoDeclareNotice(itemType, companyInfoList, item_id)
                    }
                    //个人办事
                } else {
                    //当前用户为个人
                    if(userType=="0"){
                        val itemType = if ("0" == LoginUtils.getInstance().userInfo.userType) Constants.PERSONAL_WORK_TYPE else Constants.COMPANY_WORK_TYPE
                        val companyInfoList = LoginUtils.getInstance().userInfo.data.companylist
                        goIntent(companyInfoList, workNew)
                    }else{
                    //   DialogUtils.showToast(this@WorkNewActivity, "您是法人，请办理企业事项")
                        val intent = Intent()
                        intent.setClass(this@WorkNewActivity, WorkGuideNewActivity::class.java)
                        intent.putExtra("item_id", workNew.id)
                        intent.putExtra("item_name", workNew.itemname)
                        intent.putExtra("UNITSTR", workNew.normacceptdepart)
                        intent.putExtra("type", type)
                        intent.putExtra("typeHome", type)
                        startActivity(intent)
                    }


                    //gotoDeclareNotice(itemType, companyInfoList, item_id)
                }


            }
        } else {
            // 没有登录则让用户先去登录
            LoginUtils.getInstance().checkNeedLogin(this@WorkNewActivity, true, getString(R.string.declare_tips), REQUEST_CODE)
        }
    }

    /**
     * 预约跳转
     */
    private fun yyIntent(workNew: WorkNew) {

//        val intent = Intent(this@WorkNewActivity, OnlineBookingHallActivity::class.java)
//        intent.putExtra("NORMACCEPTDEPART", workNew.normacceptdepart)
//        intent.putExtra("ITEMNAME", workNew.itemname)
//        intent.putExtra("ITEMID", workNew.id)
//        intent.putExtra("SITENO", workNew.sxflid)
//        intent.putExtra("type", type)
//        startActivity(intent)

        val intent = Intent()
        intent.setClass(this@WorkNewActivity, WorkGuideNewActivity::class.java)
        intent.putExtra("item_id", workNew.id)
        intent.putExtra("item_name", workNew.itemname)
        intent.putExtra("UNITSTR", workNew.normacceptdepart)
        intent.putExtra("type", type)
        intent.putExtra("typeHome", type)
        startActivity(intent)
    }

    private fun goIntent(companyInfoList: List<UserInfo.CompanyInfo>?, workNew: WorkNew) {
        if (typeChild == "1") {
            gotoDeclareNotice("", companyInfoList, workNew.id,workNew)
        } else if (typeChild == "2") {
            yyIntent(workNew)
        }

    }

    /**
     * 跳转到申报须知界面  在线跳转
     */
    private fun gotoDeclareNotice(itemType: String, companyInfoList: List<UserInfo.CompanyInfo>?, item_id: String,workNew:WorkNew) {
        i("申报的type$type")
//        val intent = Intent(this@WorkNewActivity, DeclareNoticeActivity::class.java)
//        intent.putExtra("type", type)
//        intent.putExtra("item_id", item_id)
//        intent.putExtra("position", "0")
//        intent.putExtra("companyId", "")
//        startActivity(intent)
        val intent = Intent()
        intent.setClass(this@WorkNewActivity, WorkGuideNewActivity::class.java)
        intent.putExtra("item_id", workNew.id)
        intent.putExtra("item_name", workNew.itemname)
        intent.putExtra("UNITSTR", workNew.normacceptdepart)
        intent.putExtra("type", type)
        intent.putExtra("typeHome", type)
        startActivity(intent)
    }


    /**
     * 跳转不同界面传参
     */
    private fun gotoActivity(c: Class<*>, itemType: String, item_id: String, intentType: String, workNew: WorkNew) {
        val intent = Intent(this, c)
        intent.putExtra("type", itemType)
        intent.putExtra("item_id", item_id)
        intent.putExtra("position", "0")
        intent.putExtra("intentType", intentType)

        intent.putExtra("NORMACCEPTDEPARTSTR", workNew.normacceptdepart)
        intent.putExtra("ITEMNAMESTR", workNew.itemname)
        intent.putExtra("SXFLIDSTR", workNew.sxflid)
        startActivity(intent)
        finish()
    }

    /**
     * 获取事项部门请求接口
     */
    private fun getServiceItems(type: String, tagName: String) {
        val request = OAInterface.getTagsListBySort(type, tagName)
        invoke.invokeWidthDialog(request, callBack, GET_SERVICE_ITEMS_REQ)
    }

    /**
     * 获取地区请求接口
     */
    private fun getAreaItems() {
        val request = OAInterface.getArea()
        invoke.invokeWidthDialog(request, callBack, GET_AREA_REQ)
    }

    /**
     * 获取事项信息请求
     */
    private fun getItem(type: String, tagId: String, itemName: String) {
        val request = OAInterface.getItemByTagId(type, tagId, itemName, currentPage.toString())
        invoke.invokeWidthDialog(request, callBack, GET_ITEM_REQ)
    }


    /**
     * 获取部门列表请求
     */
    private fun getSiteList() {
        val request = OAInterface.getSiteList()
        invoke.invokeWidthDialog(request, callBack, SITE_LIST_REQ)
    }


    /**
     *  预约办事 + 办事指南
     */
    private fun getEventList(content: String, region_code: String, normacceptdepartid: String, tagid: String) {

        val request = OAInterface.getEventList(type, content, region_code, normacceptdepartid, tagid, currentPage.toString())

        invoke.invoke(request, callBack, GET_EVENT_REQ)
    }


    /**
     *  在线办事
     */
    private fun getOlineEventList(content: String, region_code: String, normacceptdepartid: String, tagid: String) {

        val request = OAInterface.getOlineEventList(type, content, region_code, normacceptdepartid, tagid, currentPage.toString())

        invoke.invoke(request, callBack, GET_EVENT_REQ)
    }


    private val callBack = object : BaseRequestCallBack() {

        override fun process(response: HttpResponse, what: Int) {
            val item = response.getResultItem(ResultItem::class.java)
            val code = item.getString("code")
            val message = item.getString("message")
            if (Constants.SUCCESS_CODE == code) {
                //地区
                if (what == GET_AREA_REQ) {
                    val data = item.getItems("DATA");
                    parseAreaData(data)
                    //部门
                } else if (what == SITE_LIST_REQ) {
                    val data = item.getItems("DATA")
                    parseDepartmentData(data)
                    //主题
                } else if (what == GET_SERVICE_ITEMS_REQ) {
                    val data = item.getItems("DATA")
                    parseThemeData(data)
//                    tagId = itemsInfoList.get(adapter.getSelectedPosition()).getTAG_ID()
//                    getItem(type, tagId, "")
                    ////事项
                } else if (what == GET_EVENT_REQ) {
                    if (layout_refresh.isRefreshing()) {
                        layout_refresh.finishRefresh()
                    } else {
                        layout_refresh.finishLoadmore()
                    }


                    var resultItem: ResultItem? = item.get("DATA") as ResultItem?


                    var data: List<ResultItem>? = null
                    if (typeChild!!.equals("1")) {
                        data = resultItem?.getItems("DATA")
                    } else {
                        data = resultItem?.getItems("ITEM_DATA")
                    }


                    if (currentPage == 1 && BeanUtils.isEmpty(data)) {
                        rl_no_data.visibility = View.VISIBLE
                        layout_refresh.visibility = View.GONE
                    } else {
                        // 下一页后没数据
                        if (currentPage > 1 && BeanUtils.isEmpty(data)) {
                            layout_refresh.setLoadmoreFinished(true)
                        }
                        rl_no_data.visibility = View.GONE
                        layout_refresh.visibility = View.VISIBLE

                        parseEventData(data!!)
                        workRecycleAdapter?.setData(workNewBeanList)
                    }


                } else if (what == GET_EVENT_REQ_ONLINE) {

                } else if (what == GET_ITEM_REQ) {
                    val result = item.get("DATA") as ResultItem
                    if (result != null) {
                        val data = result.getItems("DATA")
//                        parseItemData(data)
//                        tv_no_data.setVisibility(View.GONE)
                    } else {
                        //                       refresh_layout.setEnableLoadmore(false)
                    }
                }
            } else {
                DialogUtils.showToast(this@WorkNewActivity, message)
            }
        }

    }

    /**
     * 解析地区的数据
     */
    private fun parseAreaData(items: List<ResultItem>) {
        if (BeanUtils.isEmpty(items)) {
            return
        }
        if (stringListCity.size > 0) {
            stringListCity.clear()
            areaBeanList.clear()
        }
        var areBean = AreaBean()
        areBean.regioN_NAME = "全地区"
        areBean.regioN_CODE = ""
        stringListCity.add(areBean.regioN_NAME)
        areaBeanList.add(areBean)
        for (item in items) {
            var areaBean = AreaBean()
            areaBean.regioN_NAME = item.getString("REGION_NAME")
            areaBean.regioN_CODE = item.getString("REGION_CODE")
            stringListCity.add(areaBean.regioN_NAME)
            areaBeanList.add(areaBean)
        }
    }


    /**
     * 事项解析
     */
    private fun parseEventData(items: List<ResultItem>) {
        Log.i("jsc", "事项个数：" + items.size)
        if (BeanUtils.isEmpty(items)) {
            return
        }


        for (item in items) {
            var workNew = WorkNew()
            workNew.itemname = item.getString("ITEMNAME")
            workNew.normacceptdepart = item.getString("NORMACCEPTDEPART")
            workNew.normacceptdepartid = item.getString("NORMACCEPTDEPARTID")
            workNew.regioN_CODE = item.getString("REGION_CODE")
            workNew.id = item.getString("ID")
            workNewBeanList.add(workNew)
        }

    }


    /**
     * 主题解析
     */
    private fun parseThemeData(items: List<ResultItem>) {
        if (BeanUtils.isEmpty(items)) {
            return
        }
        if (stringListTheme.size > 0) {
            stringListTheme.clear()
            themeBeanList.clear()
        }

        val info = ItemsInfo()
        info.taG_ID = ""
        info.taG_NAME = "全主题"
        stringListTheme.add(info.taG_NAME)
        themeBeanList.add(info)
        for (item in items) {
            val tag_id = item.getString("TAG_ID")
            val tag_name = item.getString("TAG_NAME")
            val tag_type = item.getString("TAG_TYPE")
            val itemsInfo = ItemsInfo()
            itemsInfo.taG_ID = tag_id
            itemsInfo.taG_NAME = tag_name
            itemsInfo.taG_TYPE = tag_type
            stringListTheme.add(tag_name)
            themeBeanList.add(itemsInfo)
        }
    }


    /**
     * 部门解析
     */
    private fun parseDepartmentData(items: List<ResultItem>) {
        if (BeanUtils.isEmpty(items)) {
            return
        }
        if (stringListDepartment.size > 0) {
            stringListDepartment.clear()
            departmentBeanList.clear()
        }
        var itemsInfo = ItemsInfo()
        itemsInfo.taG_ID = ""
        itemsInfo.taG_NAME = "全部门"
        stringListDepartment.add(itemsInfo.taG_NAME)
        departmentBeanList.add(itemsInfo)

        for (item in items) {
            val tag_id = item.getString("SITE_NO")
            val tag_name = item.getString("SHORT_NAME")
            val itemsInfo = ItemsInfo()
            itemsInfo.taG_ID = tag_id
            itemsInfo.taG_NAME = tag_name
            stringListDepartment.add(tag_name)
            departmentBeanList.add(itemsInfo)
        }
//        if (index == 0) {
//            val info = ItemsInfo()
//            info.taG_ID = ""
//            info.taG_NAME = "全部"
//            itemsInfoList.add(info)
//            for (item in items) {
//                val tag_id = item.getString("TAG_ID")
//                val tag_name = item.getString("TAG_NAME")
//                val tag_type = item.getString("TAG_TYPE")
//                val itemsInfo = ItemsInfo()
//                itemsInfo.taG_ID = tag_id
//                itemsInfo.taG_NAME = tag_name
//                itemsInfo.taG_TYPE = tag_type
//                itemsInfoList.add(itemsInfo)
//            }
//        } else {
//            for (item in items) {
//                val tag_id = item.getString("SITE_NO")
//                val tag_name = item.getString("SHORT_NAME")
//                val itemsInfo = ItemsInfo()
//                itemsInfo.taG_ID = tag_id
//                itemsInfo.taG_NAME = tag_name
//                itemsInfoList.add(itemsInfo)
//            }
//        }
//        if (adapter == null) {
//            adapter = ItemsAdapter(this@WorkActivity, itemsInfoList)
//            lv_left.setAdapter(adapter)
//        } else {
//            adapter.notifyDataSetChanged()
//        }
    }


    private fun initAnimo() {
        animation = RotateAnimation(0f, -180f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f)
        animation?.setInterpolator(LinearInterpolator())
        animation?.setDuration(250)
        animation?.setFillAfter(true)

        reverseAnimation = RotateAnimation(-180f, 0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f)
        reverseAnimation?.setInterpolator(LinearInterpolator())
        reverseAnimation?.setDuration(200)
        reverseAnimation?.setFillAfter(true)

    }

}
