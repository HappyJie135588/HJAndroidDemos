package com.huangjie.hjandroiddemos.customview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;

public class BezierActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_view);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, BezierActivity.class);
        activity.startActivity(intent);
    }
}
