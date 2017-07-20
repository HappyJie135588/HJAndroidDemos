package com.huangjie.hjandroiddemos.qqslidemenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.MainActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.rxjavademo.RxjavaDemoActivity;

public class TestQQSlideMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_qqslide_menu);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TestQQSlideMenuActivity.class);
        activity.startActivity(intent);
    }
}
