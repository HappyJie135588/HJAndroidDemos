package com.huangjie.hjandroiddemos.customview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.customview.widget.TouchPullView;

public class TouchPullViewActivity extends BaseActivity {
    private static final float TOUCH_MOVE_MAX_Y = 800;
    private float mTouchMoveStartY = 0;
    private TouchPullView mTouchPullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_pull_view);
        mTouchPullView = (TouchPullView) findViewById(R.id.touchPull);

        findViewById(R.id.activity_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //得到意图
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchMoveStartY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float y = event.getY();
                        if (y > mTouchMoveStartY) {
                            float moveSize = y - mTouchMoveStartY;
                            float progress = moveSize >= TOUCH_MOVE_MAX_Y ? 1 : moveSize / TOUCH_MOVE_MAX_Y;
                            mTouchPullView.setProgress(progress);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TouchPullViewActivity.class);
        activity.startActivity(intent);
    }
}
