package com.huangjie.ijkplayer.media.common;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.huangjie.ijkplayer.R;
import com.huangjie.ijkplayer.media.IjkVideoView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static com.huangjie.ijkplayer.media.utils.TimeUtils.stringForTime;


/**
 * Created by HuangJie on 2017/12/12.
 */

public class MyIjkPlayer extends FrameLayout {
    public static final String TAG = "xiong";
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static final int RETRY_TIMES = 5;
    private int mCount = 0;
    private static final int AUTO_HIDE_TIME = 500000;

    private Context mContext;

    private IjkVideoView mVideoView;
    private MyIjkPlayerListener mMyIjkPlayerListener;

    private RelativeLayout rl_loading;
    private ProgressBar pb_loading;
    private TextView tv_loading;

    private FrameLayout fl_controller;
    private LinearLayout ll_top;
    private TextView tv_back;
    private TextView tv_title;
    private TextView tv_time;

    private LinearLayout ll_bottom;
    private TextView tv_play;
    private TextView tv_play_time;
    private SeekBar seekbar;
    private TextView tv_total_time;
    private TextView tv_full;

    private String url;

    public MyIjkPlayer(@NonNull Context context) {
        this(context, null);
    }

    public MyIjkPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyIjkPlayer(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
        initInfo();
        initPlayer();
    }

    private void initView() {
        View.inflate(mContext, R.layout.default_player, this);
        mVideoView = findViewById(R.id.video_view);
        rl_loading = findViewById(R.id.rl_loading);
        pb_loading = findViewById(R.id.pb_loading);
        tv_loading = findViewById(R.id.tv_loading);
        fl_controller = findViewById(R.id.fl_controller);
        ll_top = findViewById(R.id.ll_top);
        tv_back = findViewById(R.id.tv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        ll_bottom = findViewById(R.id.ll_bottom);
        tv_play = findViewById(R.id.tv_play);
        tv_play_time = findViewById(R.id.tv_play_time);
        seekbar = findViewById(R.id.seekbar);
        tv_total_time = findViewById(R.id.tv_total_time);
        tv_full = findViewById(R.id.tv_full);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    private void initInfo() {
        fl_controller.setVisibility(GONE);
        mHandler.post(timeTask);

        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fl_controller.getVisibility() == VISIBLE) {
                    fl_controller.setVisibility(GONE);
                    mHandler.removeCallbacks(hideControllerTask);
                } else {
                    fl_controller.setVisibility(VISIBLE);
                    mHandler.postDelayed(hideControllerTask, AUTO_HIDE_TIME);
                }
            }
        });

        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    tv_play.setText("播放");
                } else {
                    mVideoView.start();
                    tv_play.setText("暂停");
                }
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: " + stringForTime(progress));
                tv_play_time.setText(stringForTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(proTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int time = seekBar.getProgress();
                Log.d(TAG, "onStopTrackingTouch: " + stringForTime(time));
                mVideoView.seekTo(time);
                mHandler.post(proTask);
            }
        });
    }

    //文件日期格式
    private DateFormat mDateFormat = new SimpleDateFormat("HH:mm");
    private Runnable timeTask = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            String time = mDateFormat.format(new Date(currentTime));
            tv_time.setText(time);
            mHandler.postDelayed(this, 1000 - (currentTime % 1000));
        }
    };

    private Runnable hideControllerTask = new Runnable() {
        @Override
        public void run() {
            fl_controller.setVisibility(GONE);
        }
    };
    private Runnable proTask = new Runnable() {
        @Override
        public void run() {
            int pos = setProgress();
            mHandler.postDelayed(this, 1000 - (pos % 1000));
        }
    };

    public void setUrl(String url) {
        this.url = url;
        mVideoView.setVideoPath(url);
    }

    public void setMyIjkPlayerListener(MyIjkPlayerListener listener) {
        this.mMyIjkPlayerListener = listener;
    }

    private void initPlayer() {
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                rl_loading.setVisibility(View.GONE);
                mVideoView.start();
                tv_play.setText("暂停");
                mHandler.post(proTask);
            }
        });
        mVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                mCount++;
                Log.d(TAG, "播放出错啦,准备第" + mCount + "次重试");
                if (mCount > RETRY_TIMES) {
                    new AlertDialog.Builder(mContext)
                            .setMessage("播放出错啦,我已经重试" + mCount + "次啦")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mMyIjkPlayerListener != null) {
                                        mMyIjkPlayerListener.onError();
                                    }
                                }
                            }).setCancelable(false)
                            .show();
                } else {
                    mVideoView.stopPlayback();
                    mVideoView.setVideoPath(url);
                }
                return false;
            }
        });
        mVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                Log.d(TAG, "onInfo");
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

    private int setProgress() {
        int position = mVideoView.getCurrentPosition();
        int duration = mVideoView.getDuration();
        if (duration > 0) {
            seekbar.setMax(duration);
            seekbar.setProgress(position);
        }
        int percent = mVideoView.getBufferPercentage();
        seekbar.setSecondaryProgress(percent);
        tv_total_time.setText(stringForTime(duration));
        return position;
    }

    public void onStop() {
        mCount = 0;
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
    }

    interface MyIjkPlayerListener {
        void onError();
    }
}
