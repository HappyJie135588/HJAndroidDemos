package com.huangjie.hjandroiddemos.customview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huangjie.hjandroiddemos.R;

public class CustormViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custorm_view);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CustormViewActivity.class);
        activity.startActivity(intent);
    }
}
