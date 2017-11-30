package com.huangjie.hjandroiddemos.live;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.ToastUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class LiveActivity extends BaseActivity {

    LiveEntity mLiveEntity;

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.vv_video)
    VideoView vv_video;
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

    private void initView() {
        tv_name.setText(mLiveEntity.getName());
        String time = mDateFormat.format(new Date());
        tv_time.setText(time);
    }

    private void initPlayer() {
        Vitamio.isInitialized(this);
        vv_video.setVideoPath(mLiveEntity.getUrl());
        vv_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                loggerHJ.d("准备好了");
                mp.setPlaybackSpeed(1.0f);
                vv_video.start();
            }
        });
        vv_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                loggerHJ.d("出错啦");
                if (mCount > RETRY_TIMES) {
                    new AlertDialog.Builder(LiveActivity.this)
                            .setMessage("播放出错啦")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setCancelable(false).show();
                } else {
                    vv_video.stopPlayback();
                    vv_video.setVideoPath(mLiveEntity.getUrl());
                }
                mCount++;
                return false;
            }
        });
        vv_video.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                loggerHJ.d("onInfo");
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        rl_loading.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        rl_loading.setVisibility(View.GONE);
                }
                return false;
            }
        });
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
