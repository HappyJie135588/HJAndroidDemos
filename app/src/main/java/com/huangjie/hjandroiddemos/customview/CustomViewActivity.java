package com.huangjie.hjandroiddemos.customview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_topbar_view)
    public void tv_topbar_view() {
        TopbarViewActivity.actionStart(this);
    }

    @OnClick(R.id.btn_touchpull_view)
    public void tv_touchpull_view() {
        TouchPullViewActivity.actionStart(this);
    }

    @OnClick(R.id.btn_bezier_view)
    public void btn_bezier_view() {
        BezierActivity.actionStart(this);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CustomViewActivity.class);
        activity.startActivity(intent);
    }
}
