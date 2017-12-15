package com.huangjie.hjandroiddemos.live;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.ToastUtils;
import com.huangjie.ijkplayer.media.common.MyIjkPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveActivity2 extends BaseActivity {

    @BindView(R.id.my_ijk_player)
    MyIjkPlayer my_ijk_player;
    LiveEntity mLiveEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live2);
        ButterKnife.bind(this);
        mLiveEntity = (LiveEntity) getIntent().getSerializableExtra("live");
        loggerHJ.d(gson.toJson(mLiveEntity));
        ToastUtils.showToast(gson.toJson(mLiveEntity));
        initPlayer();
    }

    public static void actionStart(Activity activity, LiveEntity liveEntity) {
        Intent intent = new Intent(activity, LiveActivity2.class);
        intent.putExtra("live", liveEntity);
        activity.startActivity(intent);
    }

    private void initPlayer() {
        my_ijk_player.setTitle(mLiveEntity.getName());
        my_ijk_player.setUrl(mLiveEntity.getUrl());
    }

    @Override
    protected void onStop() {
        super.onStop();
        my_ijk_player.onStop();
    }

}
