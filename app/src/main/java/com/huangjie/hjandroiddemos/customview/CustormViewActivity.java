package com.huangjie.hjandroiddemos.customview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.huangjie.hjandroiddemos.R;

public class CustormViewActivity extends AppCompatActivity {
    private SettingItemView setting_item_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custorm_view);
        initView();
    }

    private void initView() {
        setting_item_view= (SettingItemView) findViewById(R.id.setting_item_view);
        setting_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setting_item_view.setCheck(!setting_item_view.isCheck());
            }
        });
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CustormViewActivity.class);
        activity.startActivity(intent);
    }
}
