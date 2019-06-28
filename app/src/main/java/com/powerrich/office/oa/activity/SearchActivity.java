package com.powerrich.office.oa.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.powerrich.office.oa.R;


/**
 * 搜索页
 *
 * @author Mingpeng
 */
public class SearchActivity extends Activity implements View.OnClickListener {
    private EditText tvSearch;
    private TextView tvTextSearch;
    private ImageView img;
    private View llContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tvSearch = (EditText) findViewById(R.id.search_et);
        tvTextSearch = (TextView) findViewById(R.id.search_btn);
        img = (ImageView) findViewById(R.id.iv_arrow);
        llContent = findViewById(R.id.ll_content);
        tvSearch.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvSearch.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                performEnterAnimation();
            }
        });
        tvTextSearch.setOnClickListener(this);
        img.setOnClickListener(this);
    }

    private void performEnterAnimation() {
        float originY = getIntent().getIntExtra("y", 0);

        int[] location = new int[2];
        tvSearch.getLocationOnScreen(location);

        final float translateY = originY - (float) location[1];

        //放到前一个页面的位置
        tvSearch.setY(tvSearch.getY() + translateY);
        img.setY(tvSearch.getY() + (tvSearch.getHeight() - img.getHeight()) / 2);
        float top = getResources().getDisplayMetrics().density * 20;
        //平移动画
        final ValueAnimator translateVa = ValueAnimator.ofFloat(tvSearch.getY(), top);
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                tvSearch.setY((Float) valueAnimator.getAnimatedValue());
                img.setY(tvSearch.getY() + (tvSearch.getHeight() - img.getHeight()) / 2);
                tvTextSearch.setY(tvSearch.getY() + (tvSearch.getHeight() - tvTextSearch.getHeight()) / 2);
            }
        });
        //缩放动画
        ValueAnimator scaleVa = ValueAnimator.ofFloat(1, 0.8f);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                tvSearch.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });
        //透明度动画
        ValueAnimator alphaVa = ValueAnimator.ofFloat(0, 1f);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                llContent.setAlpha((Float) valueAnimator.getAnimatedValue());
                tvSearch.setAlpha((Float) valueAnimator.getAnimatedValue());
                img.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });

        alphaVa.setDuration(500);
        translateVa.setDuration(500);
        scaleVa.setDuration(500);

        alphaVa.start();
        translateVa.start();
        scaleVa.start();
    }

    private boolean finishing;

    @Override
    public void onBackPressed() {
        if (!finishing) {
            finishing = true;
            performExitAnimation();
        }
    }

    private void performExitAnimation() {
        float originY = getIntent().getIntExtra("y", 0);

        int[] location = new int[2];
        tvSearch.getLocationOnScreen(location);
        final float translateY = originY - (float) location[1];
        final ValueAnimator translateVa = ValueAnimator.ofFloat(tvSearch.getY(), tvSearch.getY() + translateY);
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                tvSearch.setY((Float) valueAnimator.getAnimatedValue());
                img.setY(tvSearch.getY() + (tvSearch.getHeight() - img.getHeight()) / 2);
                tvTextSearch.setY(tvSearch.getY() + (tvSearch.getHeight() - tvTextSearch.getHeight()) / 2);
            }
        });
        translateVa.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        ValueAnimator scaleVa = ValueAnimator.ofFloat(0.8f, 1f);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                tvSearch.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });

        ValueAnimator alphaVa = ValueAnimator.ofFloat(1, 0f);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                llContent.setAlpha((Float) valueAnimator.getAnimatedValue());
                tvSearch.setAlpha((Float) valueAnimator.getAnimatedValue());
                img.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });


        alphaVa.setDuration(500);
        translateVa.setDuration(500);
        scaleVa.setDuration(500);

        alphaVa.start();
        scaleVa.start();
        translateVa.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow:
                finish();
                break;
            case R.id.search_btn:
            // TODO: 2017/12/4 获取输入框内容开始搜索
                break;

            default:
                break;
        }
    }
}
