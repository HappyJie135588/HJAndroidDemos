package com.huangjie.hjandroiddemos.live;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.ToastUtils;
import com.huangjie.ijkplayer.media.common.MyIjkPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveActivity extends BaseActivity {

    @BindView(R.id.my_ijk_player)
    MyIjkPlayer my_ijk_player;
    LiveEntity mLiveEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        ButterKnife.bind(this);
        mLiveEntity = (LiveEntity) getIntent().getSerializableExtra("live");
        loggerHJ.d(gson.toJson(mLiveEntity));
        ToastUtils.showToast(gson.toJson(mLiveEntity));
        initPlayer();
        setImmerseLayout();
    }

    public static void actionStart(Activity activity, LiveEntity liveEntity) {
        Intent intent = new Intent(activity, LiveActivity.class);
        intent.putExtra("live", liveEntity);
        activity.startActivity(intent);
    }

    private void initPlayer() {
        my_ijk_player.setTitle(mLiveEntity.getName());
        my_ijk_player.setUrl(mLiveEntity.getUrl());
        my_ijk_player.setMyIjkPlayerListener(new MyIjkPlayer.MyIjkPlayerListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onBack() {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        my_ijk_player.backClick();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loggerHJ.d("onRestart");
        my_ijk_player.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loggerHJ.d("onStop");
        my_ijk_player.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loggerHJ.d("onDestroy");
        my_ijk_player.onDestroy();
    }

    protected void setImmerseLayout() {// view为标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
