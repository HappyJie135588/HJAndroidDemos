package com.huangjie.hjandroiddemos.live;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
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
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class LiveActivity extends BaseActivity {

    LiveEntity mLiveEntity;
    @BindView(R.id.rl_root)
    RelativeLayout rl_root;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.rl_loading)
    RelativeLayout rl_loading;
    @BindView(R.id.hud_view)
    TableLayout mHudView;
    @BindView(R.id.video_view)
    IjkVideoView mVideoView;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.tv_play)
    TextView tv_play;


    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static final int RETRY_TIMES = 5;
    private int mCount = 0;
    private static final int AUTO_HIDE_TIME = 5000;

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
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView.setVideoPath(mLiveEntity.getUrl());
        mVideoView.setHudView(mHudView);
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                rl_loading.setVisibility(View.GONE);
                mVideoView.start();
            }
        });
        mVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                loggerHJ.d("出错啦");
                if (mCount > RETRY_TIMES) {
                    new AlertDialog.Builder(LiveActivity.this)
                            .setMessage("播放出错啦")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setCancelable(false)
                            .show();
                } else {
                    mVideoView.stopPlayback();
                    mVideoView.setVideoPath(mLiveEntity.getUrl());
                }
                mCount++;
                return false;
            }
        });
        mVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                return false;
            }
        });
        mVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                loggerHJ.d("onInfo");
                switch (i) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        rl_loading.setVisibility(View.VISIBLE);
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                        rl_loading.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });

    }

    private void initView() {
        tv_name.setText(mLiveEntity.getName());
        String time = mDateFormat.format(new Date());
        tv_time.setText(time);

        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_top.getVisibility() == View.VISIBLE || ll_bottom.getVisibility() == View.VISIBLE || mHudView.getVisibility() == View.VISIBLE) {
                    ll_top.setVisibility(View.GONE);
                    ll_bottom.setVisibility(View.GONE);
                    mHudView.setVisibility(View.GONE);
                    mHandler.removeCallbacks(task);
                } else {
                    if(mVideoView.isPlaying()){
                        tv_play.setText("暂停");
                    }else{
                        tv_play.setText("播放");
                    }
                    ll_top.setVisibility(View.VISIBLE);
                    ll_bottom.setVisibility(View.VISIBLE);
                    mHudView.setVisibility(View.VISIBLE);
                    mHandler.postDelayed(task, AUTO_HIDE_TIME);
                }
            }
        });
        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVideoView.isPlaying()){
                    mVideoView.stopPlayback();
                    tv_play.setText("播放");
                }else{
                    mVideoView.setVideoPath(mLiveEntity.getUrl());
//                    mVideoView.start();
                    tv_play.setText("暂停");
                }
            }
        });
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            ll_top.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.GONE);
            mHudView.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        mCount=0;
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
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
