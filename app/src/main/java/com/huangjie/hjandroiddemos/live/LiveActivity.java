package com.huangjie.hjandroiddemos.live;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.ToastUtils;
import com.huangjie.ijkplayer.media.IjkVideoView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LiveActivity extends BaseActivity {

    LiveEntity mLiveEntity;

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.video_view)
    IjkVideoView mVideoView;
    @BindView(R.id.hud_view)
    TableLayout mHudView;
    @BindView(R.id.rl_loading)
    RelativeLayout rl_loading;

    private static final int RETRY_TIMES = 5;
    private int mCount = 0;

    //文件日期格式
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        ButterKnife.bind(this);
        mLiveEntity = (LiveEntity) getIntent().getSerializableExtra("live");
        loggerHJ.d(gson.toJson(mLiveEntity));
        ToastUtils.showToast(gson.toJson(mLiveEntity));
        initView();
        initPlayer();
    }

    private void initPlayer() {
        mVideoView.setVideoPath(mLiveEntity.getUrl());
        mVideoView.setHudView(mHudView);
        mVideoView.start();
    }

    private void initView() {
        tv_name.setText(mLiveEntity.getName());
        String time = mDateFormat.format(new Date());
        tv_time.setText(time);
    }

    @OnClick(R.id.tv_back)
    public void tv_back() {
        finish();
    }

    public static void actionStart(Activity activity, LiveEntity liveEntity) {
        Intent intent = new Intent(activity, LiveActivity.class);
        intent.putExtra("live", liveEntity);
        activity.startActivity(intent);
    }

}
