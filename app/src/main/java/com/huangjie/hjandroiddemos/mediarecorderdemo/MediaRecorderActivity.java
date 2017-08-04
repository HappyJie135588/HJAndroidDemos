package com.huangjie.hjandroiddemos.mediarecorderdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.UIUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaRecorderActivity extends BaseActivity implements SurfaceHolder.Callback {

    @BindView(R.id.iv_flash)
    public ImageView   iv_flash;
    @BindView(R.id.iv_switch)
    public ImageView   iv_switch;
    @BindView(R.id.tv_time)
    public TextView    tv_time;
    @BindView(R.id.surfaceview)
    public SurfaceView mSurfaceView;
    @BindView(R.id.iv_play)
    public ImageView   iv_play;
    @BindView(R.id.iv_capture)
    public ImageView   iv_capture;

    private Camera        mCamera;
    private MediaRecorder mMediaRecorder;
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer   mediaPlayer;//播放视频的类
    private boolean isRecording = false;
    private Timer mTimer;
    private int time = 0;

    private int cameraPosition = 1;//0代表后置摄像头，1代表前置摄像头
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_media_recorder);
        ButterKnife.bind(this);
        initView();
    }
    /** Check if this device has a camera */
    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance(int cameraId) {
        Camera c = null;
        try {
            c = Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private void initView() {
        // Create an instance of Camera
        mCamera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_BACK);

        // Create our Preview view and set it as the content of our activity.

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mTimer = new Timer();
    }


    @OnClick(R.id.iv_flash)
    public void iv_flash() {
        Camera.Parameters parameters          = mCamera.getParameters();
        List<String>      supportedFlashModes = parameters.getSupportedFlashModes();
        String            flashMode           = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
            // 开启闪光灯
            if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            }
        } else if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            // 关闭闪光灯
            if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            }
        }
        mCamera.setParameters(parameters);
    }

    @OnClick(R.id.iv_switch)
    public void iv_switch() {
        Camera.CameraInfo cameraInfo  = new Camera.CameraInfo();
        int               cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (cameraPosition == 1) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    changeCamera(i);
                    cameraPosition = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    changeCamera(i);
                    cameraPosition = 1;
                    break;
                }
            }
        }
    }

    private void changeCamera(int cameraId) {
        mCamera.stopPreview();//停掉原来摄像头的预览
        mCamera.release();//释放资源
        mCamera = getCameraInstance(cameraId);
        initCamera();

    }

    private boolean prepareVideoRecorder() {

        //mCamera = getCameraInstance();
        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        // Step 4: Set output file
        mFilePath = getOutputMediaFile().toString();
        mMediaRecorder.setOutputFile(mFilePath);

        //解决录制视频, 播放器横向问题
        mMediaRecorder.setOrientationHint(90);

        // Step 5: Set the preview output
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (Exception e) {
            loggerHJ.d("Exception preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    @OnClick(R.id.iv_capture)
    public void iv_capture() {
        if (isRecording) {
            // stop recording and release camera
            mMediaRecorder.stop();  // stop the recording
            releaseMediaRecorder(); // release the MediaRecorder object
            releaseCamera();
            // inform the user that recording has stopped
            iv_capture.setImageResource(R.mipmap.hj_test_back);
            isRecording = false;

            mTimer.cancel();

        } else {
            // initialize video camera
            if (prepareVideoRecorder()) {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mMediaRecorder.start();

                // inform the user that recording has started
                iv_capture.setImageResource(R.mipmap.hj_test_stop);
                isRecording = true;

                mTimer.schedule(mTimerTask, 1000, 1000);
            } else {
                // prepare didn't work, release the camera
                releaseMediaRecorder();
                // inform user
            }
        }
    }
    @OnClick(R.id.iv_play)
    public void iv_play(){
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        Uri uri = Uri.parse(mFilePath);
        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDisplay(mSurfaceHolder);
        try{
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
        mediaPlayer.start();
    }


    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            time++;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_time.setText("00:" + time);
                }
            });

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
        releaseMediaPlayer();
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();      // release the mediaPlayer for other applications
            mediaPlayer = null;
        }
    }


    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), UIUtils.getString(R.string.app_name));
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                loggerHJ.d("failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "VID_" + timeStamp + ".mp4");
        return mediaFile;
    }

    @OnClick(R.id.iv_back)
    public void iv_back() {
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MediaRecorderActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
// The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();

            startFaceDetection(); // start face detection feature

        } catch (IOException e) {
            loggerHJ.d("Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mSurfaceHolder.getSurface() == null) {
            // preview surface does not exist
            loggerHJ.d("mHolder.getSurface() == null");
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
            loggerHJ.d("Error stopping camera preview: " + e.getMessage());
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        initCamera();
    }

    private void initCamera() {
        mCamera.setDisplayOrientation(90);
        Camera.Parameters parameters = mCamera.getParameters();
        /*add by HJ 实现Camera自动对焦 begin*/
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes != null) {
            for (String mode : focusModes) {
                mode.contains("continuous-video");
                parameters.setFocusMode("continuous-video");
            }
        }
        /*add by HJ end*/
        /*add by HJ 拉伸   begin*/
        Camera.Size size        = parameters.getPreferredPreviewSizeForVideo();
        int         videoHeight = size.height;
        int         videoWidth  = size.width;
        parameters.setPreviewSize(videoWidth, videoHeight);
        /*add by HJ end*/
        mCamera.setParameters(parameters);
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
            loggerHJ.d("Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//        mSurfaceView = null;
//        mSurfaceHolder = null;
//        if (mMediaRecorder != null) {
//            mMediaRecorder.release(); // Now the object cannot be reused
//            mMediaRecorder = null;
//            loggerHJ.d("surfaceDestroyed release mRecorder");
//        }
//        if (mCamera != null) {
//            mCamera.release();
//            mCamera = null;
//        }
//        if (mediaPlayer != null){
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
    }

    public void startFaceDetection() {
        // Try starting Face Detection
        Camera.Parameters params = mCamera.getParameters();

        // start face detection only *after* preview has started
        if (params.getMaxNumDetectedFaces() > 0) {
            // camera supports face detection, so can start it:
            mCamera.startFaceDetection();
        }
    }
}
