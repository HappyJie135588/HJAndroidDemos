package com.huangjie.hjandroiddemos.customview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.customview.widget.SettingItemView;

public class TopbarViewActivity extends BaseActivity {
    private SettingItemView setting_item_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topbar_view);
        initView();
    }

    private void initView() {
        setting_item_view = (SettingItemView) findViewById(R.id.setting_item_view);
        setting_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setting_item_view.setCheck(!setting_item_view.isCheck());
            }
        });
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TopbarViewActivity.class);
        activity.startActivity(intent);
    }
}
