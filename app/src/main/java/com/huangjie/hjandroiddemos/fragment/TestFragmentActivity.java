package com.huangjie.hjandroiddemos.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);
        ButterKnife.bind(this);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_content, new ContentFragment());
        transaction.commit();
    }

    @OnClick(R.id.tv_title)
    public void tv_title(){
        loggerHJ.d("点击了tv_title");
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(getSupportFragmentManager(),"xx");
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TestFragmentActivity.class);
        activity.startActivity(intent);
    }
}
