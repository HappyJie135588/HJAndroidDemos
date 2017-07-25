package com.huangjie.hjandroiddemos.mediarecorddemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huangjie.hjandroiddemos.R;

import java.io.File;
import java.io.IOException;

import retrofit2.Retrofit;

/**
 * Created by HuangJie on 2017/7/25.
 */

public class RecordMp4 extends Activity implements SurfaceHolder.Callback {

    private Button mStopButton;

// private SurfaceView mSurfaceView;

    private SurfaceHolder mSurfaceHolder;

    private MediaRecorder mMediaRecorder;

    private String path, pid, uid;

    private CheckBox playOrStop;

    private Camera mCamera;

    private CameraPreview mSurfaceView;

    private TextView recor_mp4_left_time;

    private int second = 20;

    Handler handler;


    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

// 去掉标题栏

        requestWindowFeature(Window.FEATURE_NO_TITLE);

// 设置全屏

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                WindowManager.LayoutParams.FLAG_FULLSCREEN);

// 设置横屏显示

        setContentView(R.layout.record_mp4);

        handler = new Handler();

        pid = getIntent().getStringExtra("pid");

        uid = getIntent().getStringExtra("uid");


        init();

    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, RecordMp4.class);
        activity.startActivity(intent);
    }


    private void init() {

        playOrStop = (CheckBox) findViewById(R.id.etsound_playstop);

        playOrStop.setOnClickListener(new ClickListenerImpl());

        recor_mp4_left_time = (TextView) findViewById(R.id.recor_mp4_left_time);


        mCamera = getCameraInstance();

// 创建Preview view并将其设为activity中的内容

        mSurfaceView = new CameraPreview(this, mCamera);

        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

        preview.addView(mSurfaceView);

        mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(this);

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }


    Runnable runnable = new Runnable() {

        @Override

        public void run() {

            second--;

            if (second <= 0) {

                handler.removeCallbacks(this);

            } else {

                recor_mp4_left_time.setText("还剩" + second + "s");

                handler.postDelayed(this, 1000);

            }

        }

    };


    private Camera getCameraInstance() {

        Camera c = null;


        try {

// 获取Camera实例

            c = Camera.open();

            c.setDisplayOrientation(90);

        } catch (Exception e) {

// 摄像头不可用（正被占用或不存在）

        }

// 不可用则返回null

        return c;

    }


    @SuppressLint("NewApi")

    private void initMediaRecorder() {

// next codes is right for 2.3 and 4.0

        mMediaRecorder = new MediaRecorder();

// mCamera = Camera.open();

// mCamera.setDisplayOrientation(90);


        mCamera.unlock();

        mMediaRecorder.setCamera(mCamera);

// 设置视频源

        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        mMediaRecorder.setOrientationHint(90);// 视频旋转90度

// 设置音频源

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);

// 设置文件输出格式

        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

// 设置视频编码方式

        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

// 设置音频编码方式

        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

// 设置视频高和宽,注意文档的说明:

// Must be called after setVideoSource().

// Call this after setOutFormat() but before prepare().

// 设置录制的视频帧率,注意文档的说明:

// Must be called after setVideoSource().

// Call this after setOutFormat() but before prepare().

        mMediaRecorder.setVideoFrameRate(16);

// 设置预览画面

        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

// 设置输出路径

        path = this.getFilesDir().getAbsolutePath() + File.separator + System.currentTimeMillis() + ".mp4";


        mMediaRecorder.setOutputFile(path);

        mMediaRecorder.setVideoSize(640, 480);

// 设置视频的最大持续时间

        mMediaRecorder.setMaxDuration(20000);

// 为MediaRecorder设置监听

        mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {

            public void onInfo(MediaRecorder mr, int what, int extra) {

                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {

                    Toast.makeText(RecordMp4.this, "已经达到最长录制时间", Toast.LENGTH_SHORT).show();

                    System.out.println("已经达到最长录制时间");

                    if (mMediaRecorder != null) {

                        mMediaRecorder.stop();

                        mMediaRecorder.release();

                        mMediaRecorder = null;

                    }

                }

            }

        });

    }


    private class ClickListenerImpl implements View.OnClickListener {

        public void onClick(View v) {

            CheckBox checkBox = (CheckBox) v;

            if (checkBox.getId() == R.id.etsound_playstop) {

                if (mMediaRecorder == null) {

                    try {

                        initMediaRecorder();

                        mMediaRecorder.prepare();

                        mMediaRecorder.start();

                        handler.postDelayed(runnable, 0);

                    } catch (IllegalStateException e) {

                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                } else {

                    if (mMediaRecorder != null) {

                        mMediaRecorder.stop();

                        releaseMediaRecorder();

                        mCamera.lock();

                    }

                }

            }

        }

    }


    @Override

    protected void onPause() {

        super.onPause();

        handler.removeCallbacks(runnable);

// 如果正在使用MediaRecorder，首先需要释放它。

        releaseMediaRecorder();

// 在暂停事件中立即释放摄像头

        releaseCamera();

    }


    private void releaseMediaRecorder() {

        if (mMediaRecorder != null) {

// 清除recorder配置

            handler.removeCallbacks(runnable);

            mMediaRecorder.reset();

// 释放recorder对象

            mMediaRecorder.release();

            mMediaRecorder = null;

// 为后续使用锁定摄像头

            mCamera.lock();

        }

    }


    private void releaseCamera() {

        if (mCamera != null) {

// 为其它应用释放摄像头

            mCamera.release();

            mCamera = null;

        }

    }


// SurfaceHolder.Callback接口

    public void surfaceCreated(SurfaceHolder holder) {

        try {


        } catch (IllegalStateException e) {

            e.printStackTrace();

        }

    }

//  在 surfaceChanged以后，才在 initMediaRecorder()方法里 MediaRecorder 变量设置参数，不然 会在

//mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); 出报错。

    public void surfaceChanged(SurfaceHolder holder, int format, int width,

                               int height) {

        System.out.println("SurfaceView---->Changed");

    }


    public void surfaceDestroyed(SurfaceHolder holder) {

        System.out.println("SurfaceView---->Destroyed");

        if (mMediaRecorder != null) {

            mMediaRecorder.stop();

            mMediaRecorder.release();

            mMediaRecorder = null;

        }

    }

}
