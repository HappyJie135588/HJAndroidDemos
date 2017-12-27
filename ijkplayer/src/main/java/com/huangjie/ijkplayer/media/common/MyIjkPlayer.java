package com.huangjie.ijkplayer.media.common;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
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
    public static final String TAG = "MyIjkPlayer";
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static final int RETRY_TIMES = 5;//重试次数
    private int mCount = 0;//错误次数
    private int duration;//视频长度
    private static final int AUTO_HIDE_TIME = 500000;//自动隐藏播放控制器时间
    private int initHeight;//记录播放器的高度
    private String[] sizeStr = new String[]{"适应", "拉伸", "填充", "铺满", "16:9", "4:3"};//画面尺寸

    private Context mContext;

    private IjkVideoView mVideoView;
    private MyIjkPlayerListener mMyIjkPlayerListener;

    private RelativeLayout rl_loading;
    private ProgressBar pb_loading;
    private TextView tv_loading;

    private FrameLayout fl_controller;//播放控制器
    private LinearLayout ll_top;
    private TextView tv_back;
    private TextView tv_title;
    private TextView tv_time;

    private LinearLayout ll_bottom;
    private TextView tv_play;
    private LinearLayout ll_progress;
    private TextView tv_play_time;
    private SeekBar seekbar;
    private TextView tv_total_time;
    private TextView tv_turn;
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
        ll_progress = findViewById(R.id.ll_progress);
        tv_play_time = findViewById(R.id.tv_play_time);
        seekbar = findViewById(R.id.seekbar);
        tv_total_time = findViewById(R.id.tv_total_time);
        tv_turn = findViewById(R.id.tv_turn);
        tv_full = findViewById(R.id.tv_full);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    private void initInfo() {
        //设置状态栏透明
        setImmerseLayout(ll_top);
        fl_controller.setVisibility(GONE);
        //开启时间显示任务
        mHandler.post(timeTask);
        //获取记录播放器的高度
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                initHeight = getHeight();
                Log.d(TAG, "播放器的高度: initHeight=" + initHeight);
            }
        });
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fl_controller.getVisibility() == VISIBLE) {
                    hideController();
                    //移除自动隐藏控制器任务
                    mHandler.removeCallbacks(hideControllerTask);
                } else {
                    showController();
                }
            }
        });
        //返回按钮监听
        tv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                backClick();
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
                Log.d(TAG, "时间进度: " + stringForTime(progress));
                tv_play_time.setText(stringForTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //停止更新播放进度
                mHandler.removeCallbacks(proTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int time = seekBar.getProgress();
                Log.d(TAG, "时间跳转: " + stringForTime(time));
                mVideoView.seekTo(time);
                //重新开始更新播放进度
                mHandler.post(proTask);
            }
        });
        tv_turn.setText(sizeStr[0]);
        //画面尺寸切换
        tv_turn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = mVideoView.toggleAspectRatio();
                tv_turn.setText(sizeStr[i]);
            }
        });
        //全屏切换
        tv_full.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFullScreen();
            }
        });
    }

    //显示控制界面
    private void showController() {
        fl_controller.setVisibility(VISIBLE);
        mHandler.post(timeTask);
        mHandler.postDelayed(hideControllerTask, AUTO_HIDE_TIME);
        setSystemUIVisible(true);
    }

    //显示隐藏状态栏和导航栏
    private void setSystemUIVisible(boolean show) {
        if (show) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= 0x00001000;
            ((Activity) mContext).getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiFlags |= 0x00001000;
            ((Activity) mContext).getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    //隐藏控制界面
    private void hideController() {
        fl_controller.setVisibility(GONE);
        fl_controller.setPadding(0, 0, 0, 0);
        mHandler.removeCallbacks(timeTask);
        setSystemUIVisible(false);
    }

    //点击返回
    public void backClick() {
        if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            //如果是全屏就先退出全屏
            toggleFullScreen();
        } else {
            //非全屏直接回调给页面处理
            if (mMyIjkPlayerListener != null) {
                mMyIjkPlayerListener.onBack();
            }
        }
    }

    //全屏切换
    private void toggleFullScreen() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            tv_full.setText("全屏");
            lp.height = initHeight;
            ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            tv_full.setText("半屏");
            lp.height = LayoutParams.MATCH_PARENT;
            ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setLayoutParams(lp);
        //隐藏控制界面
        hideController();
    }

    /**
     * 获取界面方向
     * 拷贝的看不懂
     */
    public int getScreenOrientation() {
        int rotation = ((Activity) mContext).getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) && height > width || (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) && width > height) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }
        return orientation;
    }

    //文件日期格式
    private DateFormat mDateFormat = new SimpleDateFormat("HH:mm:ss");
    //更新时间显示的任务
    private Runnable timeTask = new Runnable() {
        @Override
        public void run() {
            final long currentTime = System.currentTimeMillis();
            String time = mDateFormat.format(new Date(currentTime));
            Log.d(TAG, "当前时间:" + time);
            tv_time.setText(time);
            //因不能监听导航栏的显示和隐藏,定时获取屏幕的宽度设置播放控制器的宽度
            ViewGroup.LayoutParams params = fl_controller.getLayoutParams();
            params.width = getScreenWidth();
            mHandler.postDelayed(this, 1000 - (currentTime % 1000));
        }
    };
    //定时隐藏播放控制器的任务
    private Runnable hideControllerTask = new Runnable() {
        @Override
        public void run() {
            hideController();
        }
    };

    //更新播放进度的任务
    private Runnable proTask = new Runnable() {
        @Override
        public void run() {
            int position = mVideoView.getCurrentPosition();
            if (duration > 0) {
                seekbar.setProgress(position);
            }
            int percent = mVideoView.getBufferPercentage();
            seekbar.setSecondaryProgress(percent);
            mHandler.postDelayed(this, 1000 - (position % 1000));
        }
    };

    public void setUrl(String url) {
        this.url = url;
        mVideoView.setVideoPath(url);
    }

    //回调监听
    public void setMyIjkPlayerListener(MyIjkPlayerListener listener) {
        this.mMyIjkPlayerListener = listener;
    }

    private void initPlayer() {
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        //准备播放
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                Log.d(TAG, "onPrepared: 准备播放");
                rl_loading.setVisibility(View.GONE);
                //开始播放可以设置播放速度
                mVideoView.start();
                //错误重试次数置零
                mCount = 0;
                tv_play.setText("暂停");
                //获取视频长度
                duration = mVideoView.getDuration();
                if (duration > 0) {
                    //显示seekbar启动播放进度更新
                    ll_progress.setVisibility(VISIBLE);
                    seekbar.setMax(duration);
                    tv_total_time.setText(stringForTime(duration));
                    mHandler.post(proTask);
                } else {
                    //视频长度为0隐藏seekbar
                    ll_progress.setVisibility(INVISIBLE);
                }
            }
        });
        //播放完毕
        mVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                Log.d(TAG, "onCompletion: 播放完毕");
                //移除更新播放进度的任务
                mHandler.removeCallbacks(proTask);
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
                    //重试
                    mVideoView.stopPlayback();
                    mVideoView.setVideoPath(url);
                }
                //返回true消耗事件
                return true;
            }
        });
//        int MEDIA_INFO_UNKNOWN = 1;//未知信息
//        int MEDIA_INFO_STARTED_AS_NEXT = 2;//播放下一条
//        int MEDIA_INFO_VIDEO_RENDERING_START = 3;//视频开始整备中
//        int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;//视频日志跟踪
//        int MEDIA_INFO_BUFFERING_START = 701;//701 开始缓冲中
//        int MEDIA_INFO_BUFFERING_END = 702;//702 缓冲结束
//        int MEDIA_INFO_NETWORK_BANDWIDTH = 703;//703 网络带宽，网速方面
//        int MEDIA_INFO_BAD_INTERLEAVING = 800;//交错出错意味着交错存储的 media 有错误或者根本没交错
//        int MEDIA_INFO_NOT_SEEKABLE = 801;//801 media 无法定位播放（如直播）
//        int MEDIA_INFO_METADATA_UPDATE = 802;//一个新的元数据集可用
//        int MEDIA_INFO_TIMED_TEXT_ERROR = 900;//未能妥善处理定时文本轨道
//        int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;//不支持字幕
//        int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;//字幕超时
//        int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;//这里返回了视频旋转的角度，根据角度旋转视频到正确的画面
//        int MEDIA_INFO_AUDIO_RENDERING_START = 10002;//音频开始整备中
//        int MEDIA_INFO_AUDIO_DECODED_START = 10003;//音频开始解码
//        int MEDIA_INFO_VIDEO_DECODED_START = 10004;//视频开始解码
//        int MEDIA_INFO_OPEN_INPUT = 10005;//
//        int MEDIA_INFO_FIND_STREAM_INFO = 10006;
//        int MEDIA_INFO_COMPONENT_OPEN = 10007;
//        int MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE = 10100;
//        int MEDIA_ERROR_UNKNOWN = 1;
//        int MEDIA_ERROR_SERVER_DIED = 100;//服务挂掉
//        int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;//数据错误没有有效的回收
//        int MEDIA_ERROR_IO = -1004;//IO错误
//        int MEDIA_ERROR_MALFORMED = -1007;
//        int MEDIA_ERROR_UNSUPPORTED = -1010;//数据不支持
//        int MEDIA_ERROR_TIMED_OUT = -110;//数据超时
        mVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int arg1, int extra) {
                Log.i(TAG, "onInfo (" + arg1 + "," + extra + ")");
                switch (arg1) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //701 暂停播放等待缓冲更多数据
                        rl_loading.setVisibility(View.VISIBLE);
                        tv_loading.setText("加载中...");
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //702 缓冲完成
                        rl_loading.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });
    }

    //生命周期控制
    //重新开始播放
    public void onRestart() {
        mVideoView.start();
        //重新开始更新时间显示的任务
        mHandler.post(timeTask);
        //移除更新播放进度的任务
        mHandler.post(proTask);
    }

    //暂停播放
    public void onStop() {
        Log.d(TAG, "onStop: 暂停播放");
        mVideoView.pause();
        //移除更新时间显示的任务
        mHandler.removeCallbacks(timeTask);
        //移除更新播放进度的任务
        mHandler.removeCallbacks(proTask);
    }

    //停止播放
    public void onDestroy() {
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
    }

    public interface MyIjkPlayerListener {
        void onError();

        void onBack();
    }

    //设置状态栏透明
    protected void setImmerseLayout(View view) {// view为标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(mContext);
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    //获取状态栏高度
    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getScreenWidth() {
        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }


}
