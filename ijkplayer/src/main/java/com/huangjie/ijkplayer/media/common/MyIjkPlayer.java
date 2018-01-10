package com.huangjie.ijkplayer.media.common;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import android.widget.Toast;

import com.huangjie.ijkplayer.R;
import com.huangjie.ijkplayer.media.IjkVideoView;
import com.huangjie.ijkplayer.media.utils.NetUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.util.IOUtils;
import master.flame.danmaku.ui.widget.DanmakuView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static com.huangjie.ijkplayer.media.utils.TimeUtils.stringForTime;


/**
 * Created by HuangJie on 2017/12/12.
 */

public class MyIjkPlayer extends FrameLayout {
    public static final String TAG = "MyIjkPlayer";
    private static final int TIME_MESSAGE_WHAT = 0;
    private static final int HIDE_CONTROLLER_MESSAGE_WHAT = 1;
    private static final int PRO_MESSAGE_WHAT = 2;

    private static final int RETRY_TIMES = 5;//重试次数
    private int mCount = 0;//错误次数
    private int duration;//视频长度
    private static final int AUTO_HIDE_TIME = 50000;//自动隐藏播放控制器时间
    private int initHeight;//记录播放器的高度
    private final String[] sizeStr = new String[]{"适应", "拉伸", "填充", "铺满", "16:9", "4:3"};//画面尺寸
    private GestureDetector mGestureDetector;
    private boolean islocked;
    private boolean isLive;
    private boolean isMobileNetPlay;
    //是否有弹幕
    private final boolean haveDama;
    private boolean isDamaPrepared;
    private boolean isVideoPrepared;

    //滑动进度条得到的新位置，和当前播放位置是有区别的,newPosition =0也会调用设置的，故初始化值为-1
    private int newPosition = -1;
    //当前亮度大小
    private float brightness;
    //当前声音大小
    private int volume;
    //设备最大音量
    private int mMaxVolume;
    //音频管理器
    private AudioManager audioManager;

    private final Context mContext;
    private final DanmakuContext mDanmakuContext;
    private final Activity mActivity;
    //广播接收器
    private NetWorkStateReceiver mNetWorkStateReceiver;
    //播放器
    private IjkVideoView mVideoView;
    //弹幕
    private DanmakuView mDanmakuView;
    //解析器对象
    private BaseDanmakuParser mParser;
    private MyIjkPlayerListener mMyIjkPlayerListener;

    private AlertDialog mMobileConnDialog;

    private RelativeLayout rl_loading;
    private ProgressBar pb_loading;
    private TextView tv_loading;

    private FrameLayout fl_controller;//播放控制器
    //顶部播放控制器
    private LinearLayout ll_top;
    private TextView tv_back;
    private TextView tv_title;
    private TextView tv_time;
    //锁
    private TextView tv_lock;
    //滑动进度
    private RelativeLayout rl_scroll_progress;
    private TextView tv_scroll_time;
    private TextView tv_scroll_target;
    private TextView tv_scroll_total;
    //亮度设置
    private RelativeLayout rl_bright;
    private TextView tv_bright;
    private SeekBar sb_bright;
    //音量设置
    private RelativeLayout rl_volume;
    private TextView tv_volume;
    private SeekBar sb_volume;
    //底部播放控制器
    private LinearLayout ll_bottom;
    private SeekBar seekbar;
    private TextView tv_play;
    private TextView tv_play_time;
    private TextView tv_total_time;
    //弹幕控制
    private LinearLayout ll_dm;
    private TextView tv_dm_turn;
    private TextView tv_dm_setting;
    private TextView tv_dm_send;
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
        this.mActivity = (Activity) context;
        this.mDanmakuContext = DanmakuContext.create();
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.MyIjkPlayer);
        haveDama = ta.getBoolean(R.styleable.MyIjkPlayer_haveDanma, false);
        initView();
        initInfo();
        initPlayer();
        if (haveDama) {
            initDanma();
        }
    }

    private void initView() {
        View.inflate(mContext, R.layout.default_player, this);
        mVideoView = findViewById(R.id.video_view);
        mDanmakuView = findViewById(R.id.danmaku);
        rl_loading = findViewById(R.id.rl_loading);
        pb_loading = findViewById(R.id.pb_loading);
        tv_loading = findViewById(R.id.tv_loading);
        fl_controller = findViewById(R.id.fl_controller);
        ll_top = findViewById(R.id.ll_top);
        tv_back = findViewById(R.id.tv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        tv_lock = findViewById(R.id.tv_lock);
        rl_scroll_progress = findViewById(R.id.rl_scroll_progress);
        tv_scroll_time = findViewById(R.id.tv_scroll_time);
        tv_scroll_target = findViewById(R.id.tv_scroll_target);
        tv_scroll_total = findViewById(R.id.tv_scroll_total);
        rl_bright = findViewById(R.id.rl_bright);
        tv_bright = findViewById(R.id.tv_bright);
        sb_bright = findViewById(R.id.sb_bright);
        rl_volume = findViewById(R.id.rl_volume);
        tv_volume = findViewById(R.id.tv_volume);
        sb_volume = findViewById(R.id.sb_volume);
        ll_bottom = findViewById(R.id.ll_bottom);
        seekbar = findViewById(R.id.seekbar);
        tv_play = findViewById(R.id.tv_play);
        tv_play_time = findViewById(R.id.tv_play_time);
        tv_total_time = findViewById(R.id.tv_total_time);
        ll_dm = findViewById(R.id.ll_dm);
        tv_dm_turn = findViewById(R.id.tv_dm_turn);
        tv_dm_setting = findViewById(R.id.tv_dm_setting);
        tv_dm_send = findViewById(R.id.tv_dm_send);
        tv_turn = findViewById(R.id.tv_turn);
        tv_full = findViewById(R.id.tv_full);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //更新时间显示的任务
                case TIME_MESSAGE_WHAT:
                    long currentTime = System.currentTimeMillis();
                    String time = mDateFormat.format(new Date(currentTime));
                    Log.d(TAG, "当前时间:" + time);
                    tv_time.setText(time);
                    //因不能监听导航栏的显示和隐藏,定时获取屏幕的宽度设置播放控制器的宽度
                    ViewGroup.LayoutParams params = fl_controller.getLayoutParams();
                    params.width = getScreenWidth();
                    mHandler.sendEmptyMessageDelayed(TIME_MESSAGE_WHAT, 1000 - (currentTime % 1000));
                    break;
                //定时隐藏播放控制器的任务
                case HIDE_CONTROLLER_MESSAGE_WHAT:
                    hideController();
                    break;
                //更新播放进度的任务
                case PRO_MESSAGE_WHAT:
                    int position = mVideoView.getCurrentPosition();
                    if (isLive) {
                        Log.d(TAG, "直播时间: " + stringForTime(position));
                        tv_play_time.setText(stringForTime(position));
                    } else {
                        seekbar.setProgress(position);
                        int percent = mVideoView.getBufferPercentage();
                        seekbar.setSecondaryProgress(percent);
                    }
                    mHandler.sendEmptyMessageDelayed(PRO_MESSAGE_WHAT, 1000 - (position % 1000));
                    break;
            }
        }
    };

    private void initInfo() {
        //设置状态栏透明
        setImmerseLayout(ll_top);
        //开启时间显示任务
        mHandler.sendEmptyMessage(TIME_MESSAGE_WHAT);
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
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //添加手势控制
        mGestureDetector = new GestureDetector(mContext, new PlayerGestureListener());
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //手势结束
                    Log.d(TAG, "onTouch: 手势结束");
                    if (brightness >= 0) {
                        brightness = -1f;
                        rl_bright.setVisibility(GONE);
                    }
                    if (volume >= 0) {
                        //
                        volume = -1;
                        rl_volume.setVisibility(GONE);
                    }
                    if (newPosition >= 0) {
                        //滑动进度设置完毕
                        //隐藏滑动进度界面
                        rl_scroll_progress.setVisibility(GONE);
                        //启动定时隐藏控制器
                        mHandler.sendEmptyMessageDelayed(HIDE_CONTROLLER_MESSAGE_WHAT, AUTO_HIDE_TIME);
                        Log.d(TAG, "时间滑动: " + stringForTime(newPosition));
                        mVideoView.seekTo(newPosition);
                        if (haveDama) {
                            mDanmakuView.seekTo((long) newPosition);
                        }
                        //重新开始更新播放进度
                        mHandler.sendEmptyMessage(PRO_MESSAGE_WHAT);
                        //初始化滑动参数
                        newPosition = -1;
                    }
                }
                return true;
            }
        });
        this.setLongClickable(true);
        //返回按钮监听
        tv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                backClick();
            }
        });
        tv_lock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                turnLock();
            }
        });
        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnPlayPause();
            }
        });
        tv_dm_turn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                turnDanma();
            }
        });
        tv_dm_setting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_dm_send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addDanmaku(false);
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "点播时间: " + stringForTime(progress));
                tv_play_time.setText(stringForTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //停止更新播放进度
                mHandler.removeMessages(PRO_MESSAGE_WHAT);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int time = seekBar.getProgress();
                Log.d(TAG, "时间跳转: " + stringForTime(time));
                mVideoView.seekTo(time);
                if (haveDama) {
                    mDanmakuView.seekTo((long) time);
                }
                //重新开始更新播放进度
                mHandler.sendEmptyMessage(PRO_MESSAGE_WHAT);
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

    //锁定打开
    private void turnLock() {
        if (islocked) {
            //锁开
            tv_lock.setText("打开");
            islocked = false;
        } else {
            //锁上
            tv_lock.setText("锁住");
            islocked = true;
        }
    }

    //切换播放暂停
    private void turnPlayPause() {
        if (mVideoView.isPlaying()) {
            pause();
        } else {
            play();
        }
    }

    //暂停
    private void pause() {
        mVideoView.pause();
        if (haveDama) {
            mDanmakuView.pause();
        }
        tv_play.setText("播放");
    }

    //播放
    private void play() {
        mVideoView.start();
        if (haveDama) {
            mDanmakuView.resume();
        }
        tv_play.setText("暂停");
    }

    //切换弹幕显示隐藏
    private void turnDanma() {
        if (mDanmakuView.isShown()) {
            mDanmakuView.hide();
            tv_dm_turn.setText("弹幕关");
            tv_dm_setting.setVisibility(GONE);
            tv_dm_send.setVisibility(GONE);
        } else {
            mDanmakuView.show();
            tv_dm_turn.setText("弹幕开");
            tv_dm_setting.setVisibility(VISIBLE);
            tv_dm_send.setVisibility(VISIBLE);
        }
    }

    //显示控制界面
    private void showController() {
        fl_controller.setVisibility(VISIBLE);
        mHandler.sendEmptyMessage(TIME_MESSAGE_WHAT);
        mHandler.sendEmptyMessage(PRO_MESSAGE_WHAT);
        setSystemUIVisible(true);
    }

    //显示隐藏状态栏和导航栏
    private void setSystemUIVisible(boolean show) {
        int uiFlags = -1;
        if (show) {
            //显示状态栏和导航栏
            uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        } else {
            if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                //全屏隐藏状态栏和导航栏
                uiFlags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.INVISIBLE;
            } else {
                //非全屏只隐藏状态栏
                uiFlags = View.INVISIBLE;
            }
        }
        uiFlags |= 0x00001000;
        mActivity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    //隐藏控制界面
    private void hideController() {
        fl_controller.setVisibility(GONE);
        fl_controller.setPadding(0, 0, 0, 0);
        mHandler.removeMessages(TIME_MESSAGE_WHAT);
        mHandler.removeMessages(PRO_MESSAGE_WHAT);
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

    /**
     * 全屏切换
     */
    public void toggleFullScreen() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        if (getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            tv_full.setText("半屏");
            //竖屏隐藏弹幕控制
            ll_dm.setVisibility(VISIBLE);
            lp.height = LayoutParams.MATCH_PARENT;

        } else {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            tv_full.setText("全屏");
            //全屏显示弹幕控制
            ll_dm.setVisibility(INVISIBLE);
            lp.height = initHeight;
        }
        setLayoutParams(lp);
        //隐藏控制界面
        //hideController();
    }

    /**
     * 获取界面方向
     */
    public int getScreenOrientation() {
        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
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

    public void setUrl(final String url) {
        this.url = url;
        boolean isMobileConn = NetUtils.isMobileConn(mContext);
        boolean isWifiConn = NetUtils.isWifiConn(mContext);
        Log.d(TAG, "onReceive: 网络状态发生变化 isMobileConn:" + isMobileConn + " isWifiConn:" + isWifiConn);
        if (isMobileConn && !isWifiConn) {
            //移动数据已连接
            mMobileConnDialog = new AlertDialog.Builder(mContext)
                    .setMessage("当前为数据流量是否开始播放")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mVideoView.setVideoPath(url);
                            //设置可以用流量播放
                            isMobileNetPlay = true;
                        }
                    }).show();
        } else {
            mVideoView.setVideoPath(url);
        }
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
                isVideoPrepared = true;
                Log.d(TAG, "onPrepared: 视频加载完成!");
                Toast.makeText(mContext, "视频加载完成!", Toast.LENGTH_SHORT).show();
                videoStart();
            }
        });
        //播放完毕
        mVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                Log.d(TAG, "onCompletion: 播放完毕");
                tv_play.setText("停止");
                //移除更新播放进度的任务
                mHandler.removeMessages(PRO_MESSAGE_WHAT);
                mDanmakuView.stop();
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
                        if (haveDama) {
                            mDanmakuView.pause();
                        }
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        //702 缓冲完成
                        rl_loading.setVisibility(View.GONE);
                        if (haveDama) {
                            mDanmakuView.resume();
                        }
                        break;
                }
                return false;
            }
        });
    }

    //判断视频和弹幕是否加载完毕开始播放
    private void videoStart() {
        if (isVideoPrepared && isDamaPrepared) {
            Log.d(TAG, "onPrepared: 准备播放");
            rl_loading.setVisibility(GONE);
            //开始播放可以设置播放速度
            mVideoView.start();
            //开始播放弹幕
            mDanmakuView.start();
            //错误重试次数置零
            mCount = 0;
            tv_play.setText("暂停");
            //获取视频长度
            duration = mVideoView.getDuration();
            if (duration > 0) {
                isLive = false;
                //显示seekbar启动播放进度更新
                seekbar.setVisibility(VISIBLE);
                tv_total_time.setVisibility(VISIBLE);
                seekbar.setMax(duration);
                tv_total_time.setText("/" + stringForTime(duration));
                //设置滑动进度总时间
                tv_scroll_total.setText(stringForTime(duration));
            } else {
                isLive = true;
                //视频长度为0隐藏seekbar
                seekbar.setVisibility(GONE);
                tv_total_time.setVisibility(GONE);
            }
            //开始更新播放时间的任务
            mHandler.sendEmptyMessage(PRO_MESSAGE_WHAT);
            //开始播放隐藏播放控制器
            hideController();
        }
    }

    //二,弹幕初始化
    private void initDanma() {
        // 设置弹幕的最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        // 滚动弹幕最大显示3行
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 3);
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_LR, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_BOTTOM, true);
        //设置描边样式
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)
                //是否启用合并重复弹幕
                .setScrollSpeedFactor(1.2f)
                //设置弹幕滚动速度系数,只对滚动弹幕有效
                .setScaleTextSize(1.2f)
                // 图文混排使用SpannedCacheStuffer  设置缓存绘制填充器，默认使用{@link SimpleTextCacheStuffer}只支持纯文字显示, 如果需要图文混排请设置{@link SpannedCacheStuffer}如果需要定制其他样式请扩展{@link SimpleTextCacheStuffer}|{@link SpannedCacheStuffer}
                .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter)
                //设置最大显示行数
                .setMaximumLines(maxLinesPair)
                //设置防弹幕重叠，null为允许重叠
                .preventOverlapping(overlappingEnablePair);
        mParser = createParser(this.getResources().openRawResource(R.raw.comments)); //创建解析器对象，从raw资源目录下解析comments.xml文本
        mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
            @Override
            public void updateTimer(DanmakuTimer timer) {
            }

            @Override
            public void drawingFinished() {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void prepared() {
                isDamaPrepared = true;
                Log.d(TAG, "prepared: 弹幕加载完成");
                Toast.makeText(mContext, "弹幕加载完成!", Toast.LENGTH_SHORT).show();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        videoStart();
                    }
                });
            }
        });
        mDanmakuView.prepare(mParser, mDanmakuContext);
        //是否显示FPS
        mDanmakuView.showFPS(true);
        mDanmakuView.enableDanmakuDrawingCache(true);
    }

    //三,创建解析器对象，解析输入流
    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }
        //xml解析
        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);
        //json文件格式解析
        //ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_ACFUN);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;
    }

    //四,自定义弹幕背景和边距
    private static class BackgroundCacheStuffer extends SpannedCacheStuffer {
        // 通过扩展SimpleTextCacheStuffer或SpannedCacheStuffer个性化你的弹幕样式
        final Paint paint = new Paint();

        @Override
        public void measure(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
            // 在背景绘制模式下增加padding
            danmaku.padding = 10;
            super.measure(danmaku, paint, fromWorkerThread);
        }

        @Override
        public void drawBackground(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
            //弹幕背景颜色
            paint.setColor(0x8125309b);
            canvas.drawRect(left + 2, top + 2, left + danmaku.paintWidth - 2, top + danmaku.paintHeight - 2, paint);
        }

        @Override
        public void drawStroke(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, Paint paint) {
            // 禁用描边绘制
        }
    }

    //五,添加文本弹幕
    private void addDanmaku(boolean islive) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.text = "这是一条弹幕" + System.nanoTime();
        danmaku.padding = 5;
        //0 表示可能会被各种过滤器过滤并隐藏显示 //1 表示一定会显示, 一般用于本机发送的弹幕
        danmaku.priority = 0;
        //是否是直播弹幕
        danmaku.isLive = islive;
        //显示时间
        danmaku.time = mDanmakuView.getCurrentTime() + 1200;
        danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.textColor = Color.RED;
        //阴影/描边颜色
        danmaku.textShadowColor = Color.WHITE;
        //边框颜色，0表示无边框
        danmaku.borderColor = Color.GREEN;
        mDanmakuView.addDanmaku(danmaku);
    }

    //六、添加图文混排弹幕
    private void addDanmaKuShowTextAndImage(boolean islive) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
        drawable.setBounds(0, 0, 100, 100);
        SpannableStringBuilder spannable = createSpannable(drawable);
        danmaku.text = spannable;
        danmaku.padding = 5;
        // 一定会显示, 一般用于本机发送的弹幕
        danmaku.priority = 1;
        danmaku.isLive = islive;
        danmaku.time = mDanmakuView.getCurrentTime() + 1200;
        danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.textColor = Color.RED;
        // 重要：如果有图文混排，最好不要设置描边(设textShadowColor=0)，否则会进行两次复杂的绘制导致运行效率降低
        danmaku.textShadowColor = 0;
        danmaku.underlineColor = Color.GREEN;
        mDanmakuView.addDanmaku(danmaku);
    }

    //创建图文混排模式
    private SpannableStringBuilder createSpannable(Drawable drawable) {
        String text = "bitmap";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        ImageSpan span = new ImageSpan(drawable);//ImageSpan.ALIGN_BOTTOM);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append("图文混排");
        spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor("#8A2233B1")), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }

    //七、弹幕的隐藏/显示，暂停/继续
//        mDanmakuView.hide();
//        mDanmakuView.show();
//        //暂停
//        if (haveDanma && mDanmakuView.isPrepared()) {
//            mDanmakuView.pause();
//        }
//        //继续
//        if (haveDanma && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
//            mDanmakuView.resume();
//        }
    //八、弹幕的定时发送
    Timer timer = new Timer();

    //    private void timedSend() {
//        Boolean b = (Boolean) mBtnSendDanmakus.getTag();
//        timer.cancel();
//        if (b == null || !b) {
//            mBtnSendDanmakus.setText("取消定时");
//            timer = new Timer();
//            timer.schedule(new AsyncAddTask(), 0, 1000);
//            mBtnSendDanmakus.setTag(true);
//        } else {
//            mBtnSendDanmakus.setText("定时发送");
//            mBtnSendDanmakus.setTag(false);
//        }
//
//        Timer timer = new Timer();
//    }
    class AsyncAddTask extends TimerTask {

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                addDanmaku(true);
                SystemClock.sleep(20);
            }
        }
    }

    //九、创建图文混排的填充适配器
    private BaseCacheStuffer.Proxy mCacheStufferAdapter = new BaseCacheStuffer.Proxy() {

        private Drawable mDrawable;

        /**
         * 在弹幕显示前使用新的text,使用新的text
         * @param danmaku
         * @param fromWorkerThread 是否在工作(非UI)线程,在true的情况下可以做一些耗时操作(例如更新Span的drawblae或者其他IO操作)
         * @return 如果不需重置，直接返回danmaku.text
         */
        @Override
        public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
            if (danmaku.text instanceof Spanned) { // 根据你的条件检查是否需要需要更新弹幕
                // FIXME 这里只是简单启个线程来加载远程url图片，请使用你自己的异步线程池，最好加上你的缓存池
                new Thread() {
                    @Override
                    public void run() {
                        String url = "http://www.bilibili.com/favicon.ico";
                        InputStream inputStream = null;
                        Drawable drawable = mDrawable;
                        if (drawable == null) {
                            try {
                                URLConnection urlConnection = new URL(url).openConnection();
                                inputStream = urlConnection.getInputStream();
                                drawable = BitmapDrawable.createFromStream(inputStream, "bitmap");
                                mDrawable = drawable;
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                IOUtils.closeQuietly(inputStream);
                            }
                        }
                        if (drawable != null) {
                            drawable.setBounds(0, 0, 100, 100);
                            SpannableStringBuilder spannable = createSpannable(drawable);
                            danmaku.text = spannable;
                            if (mDanmakuView != null) {
                                mDanmakuView.invalidateDanmaku(danmaku, false);
                            }
                            return;
                        }
                    }
                }.start();
            }
        }

        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            // TODO 重要:清理含有ImageSpan的text中的一些占用内存的资源 例如drawable
        }
    };

    //重新开始播放
    public void onResume() {
        mVideoView.start();
        if (haveDama && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
        //注册广播
        Log.d(TAG, "onResume: 注册广播");
        registerNetWorkReceiver();
    }

    //注册广播
    private void registerNetWorkReceiver() {
        if (mNetWorkStateReceiver == null) {
            mNetWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mContext.registerReceiver(mNetWorkStateReceiver, filter);
    }


    //暂停播放
    public void onPause() {
        Log.d(TAG, "onStop: 暂停播放");
        mVideoView.pause();
        if (haveDama && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
        //取消注册广播
        Log.d(TAG, "onPause: 取消注册广播");
        mContext.unregisterReceiver(mNetWorkStateReceiver);
    }

    //停止播放
    public void onDestroy() {
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
        if (haveDama) {
            // dont forget release!
            mDanmakuView.release();
            //Completion
            mDanmakuView = null;
        }
    }

    public interface MyIjkPlayerListener {
        void onError();

        void onBack();
    }

    //设置状态栏透明
    protected void setImmerseLayout(View view) {// view为标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    //播放器的手势监听
    public class PlayerGestureListener extends GestureDetector.SimpleOnGestureListener {

        //是否是按下的标识，默认为其他动作，true为按下标识，false为其他动作
        private boolean isDownTouch;
        //是否声音控制,默认为亮度控制，true为声音控制，false为亮度控制
        private boolean isVolume;
        //是否横向滑动，默认为纵向滑动，true为横向滑动，false为纵向滑动
        private boolean isLandscape;

        //按下
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown: ");
            isDownTouch = true;
            return true;
        }

        //单击
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(TAG, "onSingleTapConfirmed: 单击");
            if (fl_controller.getVisibility() == VISIBLE) {
                hideController();
                //移除自动隐藏控制器任务
                mHandler.removeMessages(HIDE_CONTROLLER_MESSAGE_WHAT);
            } else {
                showController();
                mHandler.sendEmptyMessageDelayed(HIDE_CONTROLLER_MESSAGE_WHAT, AUTO_HIDE_TIME);
            }
            return true;
        }

        //双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap: 双击");
            //双击播放暂停
            turnPlayPause();
            return true;
        }

        //长按
        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress: 长按");
            turnLock();
        }

        //滑动
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll: 滑动");
            if (!islocked) {
                float mOldX = e1.getX(), mOldY = e1.getY();
                float deltaY = mOldY - e2.getY();
                float deltaX = mOldX - e2.getX();
                if (isDownTouch) {
                    isLandscape = Math.abs(distanceX) >= Math.abs(distanceY);
                    isVolume = mOldX > getWidth() * 0.5f;
                    if (isLandscape) {
                        if (!isLive) {
                            /**进度设置*/
                            //停止更新播放进度
                            showController();
                            mHandler.removeMessages(PRO_MESSAGE_WHAT);
                            rl_scroll_progress.setVisibility(VISIBLE);
                        }
                    } else {
                        if (isVolume) {
                            /**声音设置*/
                            rl_volume.setVisibility(VISIBLE);
                        } else {
                            /**亮度设置*/
                            rl_bright.setVisibility(VISIBLE);
                        }
                    }
                    isDownTouch = false;
                }

                if (isLandscape) {
                    if (!isLive) {
                        Log.d(TAG, "onScroll: 进度设置");
                        /**进度设置*/
                        onProgressSlide(-deltaX / getWidth());
                    }
                } else {
                    float percent = deltaY / getHeight();
                    if (isVolume) {
                        Log.d(TAG, "onScroll: 声音设置");
                        /**声音设置*/
                        onVolumeSlide(percent);
                    } else {
                        Log.d(TAG, "onScroll: 亮度设置");
                        /**亮度设置*/
                        onBrightnessSlide(percent);
                    }
                }
            }
            return true;
        }

    }

    //快进或者快退滑动改变进度
    private void onProgressSlide(float percent) {
        int position = mVideoView.getCurrentPosition();
        //可滑动最大时间长度为100秒
        int deltaMax = Math.min(100 * 1000, duration - position);
        //实际滑动长度
        int delta = (int) (deltaMax * percent);
        //滑动后的播放位置时间
        newPosition = delta + position;
        if (newPosition > duration) {
            newPosition = duration;
        } else if (newPosition <= 0) {
            newPosition = 0;
            //限制最小滑动时间大于已播放时间
            delta = -position;
        }
        //滑动时间长度 单位:秒
        int showDelta = delta / 1000;
        if (showDelta != 0) {
            String text = showDelta > 0 ? ("+" + showDelta) : "" + showDelta;
            tv_scroll_time.setText(text + "s");
            tv_scroll_target.setText(stringForTime(newPosition) + "/");
            Log.d(TAG, "滑动进度: " + stringForTime(newPosition));
            //设置滑动后的进度
            seekbar.setProgress(newPosition);
        }
    }

    //亮度滑动改变亮度
    private void onBrightnessSlide(float percent) {
        if (brightness < 0) {
            brightness = mActivity.getWindow().getAttributes().screenBrightness;
            Log.d(TAG, "brightness:" + brightness + ",percent:" + percent);
        }
        WindowManager.LayoutParams lpa = mActivity.getWindow().getAttributes();
        lpa.screenBrightness = brightness + percent;
        if (lpa.screenBrightness > 1.0f) {
            lpa.screenBrightness = 1.0f;
        } else if (lpa.screenBrightness < 0.01f) {
            lpa.screenBrightness = 0.01f;
        }
        sb_bright.setProgress((int) (lpa.screenBrightness * 100 + 0.5f));
        mActivity.getWindow().setAttributes(lpa);
    }

    //滑动改变声音大小
    private void onVolumeSlide(float percent) {
        if (volume == -1) {
            volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            Log.d(TAG, "volume:" + volume + ",percent:" + percent);
        }
        int index = (int) (percent * mMaxVolume + 0.5f) + volume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;
        // 显示
        sb_volume.setProgress(index * 100 / mMaxVolume);
        // 变更声音
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
    }

    public class NetWorkStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isMobileConn = NetUtils.isMobileConn(mContext);
            boolean isWifiConn = NetUtils.isWifiConn(mContext);
            Log.d(TAG, "onReceive: 网络状态发生变化 isMobileConn:" + isMobileConn + " isWifiConn:" + isWifiConn);
            if (isMobileConn && !isWifiConn) {
                //移动数据已连接
                //判断是否可以用流量播放
                if (!isMobileNetPlay) {
                    pause();
                    mMobileConnDialog = new AlertDialog.Builder(mContext)
                            .setMessage("当前为数据流量是否继续播放")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    play();
                                    //设置可以用流量播放
                                    isMobileNetPlay = true;
                                }
                            }).show();
                }
            } else if (isWifiConn) {
                //Wifi已连接
                if (mMobileConnDialog != null && mMobileConnDialog.isShowing()) {
                    mMobileConnDialog.dismiss();
                }
                if (!mVideoView.isPlaying()) {
                    play();
                }
                //设置可以用流量播放失效
                isMobileNetPlay = false;
            }
        }
    }

}
