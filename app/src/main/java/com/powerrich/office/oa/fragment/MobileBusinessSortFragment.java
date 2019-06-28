package com.powerrich.office.oa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonRvAdapter;
import com.powerrich.office.oa.adapter.ViewHolderRv;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.base.InvokeHelper;
import com.powerrich.office.oa.bean.MobileBusinessLeftBean;
import com.powerrich.office.oa.bean.MobileBusinessRightBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.thirdapp.activity.ArticleInfoActivity;
import com.powerrich.office.oa.thirdapp.activity.ArticleListActivity;
import com.powerrich.office.oa.tools.AutoUtils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yt.simpleframe.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobileBusinessSortFragment extends Fragment implements OnRefreshLoadmoreListener {
    private static final String ARG_PARAM1 = "param1";
    private static final int GET_LEFT = 000;
    private static final int GET_RIGHT = 111;

    private RecyclerView recyclerView_left;
    private RecyclerView recyclerView_right;
    private SmartRefreshLayout refresh_layout;
    private CommonRvAdapter<MobileBusinessLeftBean> leftAdapter;
    private CommonRvAdapter<MobileBusinessRightBean> rightAdapter;
    private Map<Integer, ArrayList<MobileBusinessRightBean>> allRightData = new HashMap<>();
    private int oldPosition = 0;
    private String typeValue;
    private InvokeHelper invokeHelper;
    private boolean isFirstLoad = true;
    private View content;

    public MobileBusinessSortFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MobileBusinessSortFragment.
     */
    public static MobileBusinessSortFragment newInstance(String typeValue) {
        MobileBusinessSortFragment fragment = new MobileBusinessSortFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, typeValue);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typeValue = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        if (view != null) {
//            ViewGroup parent = (ViewGroup) view.getParent();
//            if (parent != null) {
//                parent.removeView(view);
//            }
//            return view;
//        }
        View view = inflater.inflate(R.layout.fragment_mobile_business_sort, container, false);
        AutoUtils.auto(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content = view.findViewById(R.id.content);
        recyclerView_left = (RecyclerView) view.findViewById(R.id.recyclerView_left);
        refresh_layout = (SmartRefreshLayout) view.findViewById(R.id.refresh_layout);
        recyclerView_right = (RecyclerView) view.findViewById(R.id.recyclerView_right);
        initData();
    }

    private void initData() {
        recyclerView_left.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_right.setLayoutManager(new GridLayoutManager(getContext(), 3));
        refresh_layout.setEnableLoadmore(false);
        refresh_layout.setOnRefreshLoadmoreListener(this);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad && isVisibleToUser && isResumed()) {
            loadData();
            isFirstLoad =false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad && getUserVisibleHint()) {
            loadData();
            isFirstLoad =false;
        }
    }

    private void loadData() {
        invokeHelper = new InvokeHelper(getContext());
        getServiceAriticleTypeListNew();
    }
    private void getServiceAriticleTypeListNew() {
        invokeHelper.invokeWidthDialog(OAInterface.GetServiceAriticleTypeListNew(typeValue), callBack, GET_LEFT);
    }
    private void getClassifyNew(String id) {
        invokeHelper.invoke(OAInterface.GetClassifyNew(id ,typeValue), callBack, GET_RIGHT);
    }
    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            List<ResultItem> data = item.getItems("Data");
            if (GET_LEFT == what) {
                showLeft(data);
            } else if (GET_RIGHT == what) {
                ArrayList<MobileBusinessRightBean> rightTitles = new ArrayList<>();
                if (null != data) {
                    for (ResultItem datum : data) {
                        MobileBusinessRightBean mobileBusinessBean = new MobileBusinessRightBean();
                        mobileBusinessBean.setId(datum.getInt("Id"));
                        mobileBusinessBean.setDetail(datum.getString("Detail"));
                        mobileBusinessBean.setImageFile(datum.getString("ImageFile"));
                        mobileBusinessBean.setUrl(datum.getString("Url"));
                        rightTitles.add(mobileBusinessBean);
                    }
                }
                showRight(rightTitles);
                allRightData.put(oldPosition, rightTitles);
            }
        }

        @Override
        public void onNetError(int what) {
            super.onNetError(what);
            if (rightAdapter != null) {
                rightAdapter.setData(null);
            }
        }

        @Override
        public void onReturnError(HttpResponse response, ResultItem error, int what) {
            super.onReturnError(response, error, what);
            if (rightAdapter != null) {
                rightAdapter.setData(null);
            }
        }
    };

    private void showRight(ArrayList<MobileBusinessRightBean> rightTitles) {
        if (null == rightAdapter) {
            content.setVisibility(View.VISIBLE);
            rightAdapter = new CommonRvAdapter<MobileBusinessRightBean>(rightTitles, R.layout.mobile_business_sort_right) {
                @Override
                public void convert(ViewHolderRv holder, MobileBusinessRightBean item, int position) {
                    holder.setText(R.id.title, item.getDetail());
                    ImageView img = holder.getItemView(R.id.img);
                   Glide.with(getContext()).load(item.getImageFile()).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
                    // Glide.with(getContext()).load(item.getImageFile()).into(img);
                }
            };
            rightAdapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    MobileBusinessRightBean item = rightAdapter.getItem(position);
                    if(StringUtil.isEmpty(item.getUrl())){
                        Intent intent = new Intent(getContext(), ArticleListActivity.class);
                        intent.putExtra("nid",8673);
                        intent.putExtra("pidx",item.getId());
                        intent.putExtra("typeid",typeValue);
                        intent.putExtra("title",item.getDetail());
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getContext(), ArticleInfoActivity.class);
                        intent.putExtra("url", item.getUrl());
                        startActivity(intent);
                    }
                }
            });
            recyclerView_right.setAdapter(rightAdapter);
        } else {
            rightAdapter.setData(rightTitles);
        }
    }

    private void showLeft(List<ResultItem> data) {
        ArrayList<MobileBusinessLeftBean> leftTitles = new ArrayList<>();
        if (null != data) {
            for (ResultItem datum : data) {
                MobileBusinessLeftBean leftSelectInfo = new MobileBusinessLeftBean();
                leftSelectInfo.setId(datum.getString("Id"));
                leftSelectInfo.setTitle(datum.getString("SerTypeName"));
                leftTitles.add(leftSelectInfo);
            }
        }
        if (leftTitles.size() != 0) {//初始化第一条分类
            leftTitles.get(oldPosition).setSelected(true);
            getClassifyNew(leftTitles.get(oldPosition).getId());
        }
        if (null == leftAdapter) {
            leftAdapter = new CommonRvAdapter<MobileBusinessLeftBean>(leftTitles, R.layout.mobile_business_sort_left) {
                @Override
                public void convert(ViewHolderRv holder, MobileBusinessLeftBean item, int position) {
                    View itemView = holder.getItemView(R.id.ll);
                    TextView textView = holder.getItemView(R.id.title);
                    if (item.isSelected()) {
                        itemView.setBackgroundResource(R.color.gray_system_bg);
                        textView.setTextColor(getContext().getResources().getColor(R.color.blue_main));
                    } else {
                        itemView.setBackgroundResource(R.color.white);
                        textView.setTextColor(getContext().getResources().getColor(R.color.gray));
                    }
                    holder.setText(R.id.title, item.getTitle());
                }
            };
            leftAdapter.setOnItemClickListener(new CommonRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (oldPosition == position) return;
                    ArrayList<MobileBusinessRightBean> rightData = allRightData.get(position);
                    if (BeanUtils.isEmpty(rightData)) {
                        getClassifyNew(leftAdapter.getItem(position).getId());
                    } else {
                        showRight(rightData);
                    }
                    leftAdapter.getItem(position).setSelected(true);
                    leftAdapter.getItem(oldPosition).setSelected(false);
                    leftAdapter.notifyDataSetChanged();
                    oldPosition = position;
                }
            });
            recyclerView_left.setAdapter(leftAdapter);
        } else {
            leftAdapter.setData(leftTitles);
        }

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        if (leftAdapter != null && oldPosition < leftAdapter.getItemCount()) {
            getClassifyNew(leftAdapter.getItem(oldPosition).getId());
        } else {
            loadData();
        }
        refresh_layout.finishRefresh();
    }
}
