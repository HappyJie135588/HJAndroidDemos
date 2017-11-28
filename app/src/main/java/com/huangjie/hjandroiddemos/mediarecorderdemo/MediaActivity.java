package com.huangjie.hjandroiddemos.mediarecorderdemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.VideoView;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaActivity extends BaseActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    @BindView(R.id.videoview)
    public VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MediaActivity.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.btn_sysrecord)
    public void btn_sysrecord() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @OnClick(R.id.btn_customerrecord)
    public void btn_customerrecord() {
        MediaRecorderActivity.actionStart(this);
    }

    @OnClick(R.id.btn_sysvideolist)
    public void btn_sysvideolist() {
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        //intent.setType("image/*");
        // intent.setType("audio/*"); //选择音频
        intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）

        // intent.setType("video/*;image/*");//同时选择视频和图片

        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        startActivityForResult(intent, 3);
    }

    @OnClick(R.id.btn_customervideolist)
    public void btn_customervideolist() {
        LocalVideoListActivity2.actionStart(this);
    }

    @OnClick(R.id.btn_play)
    public void btn_play() {
        ToastUtils.showToast("开始播发");
        mVideoView.start();//播放视频
        mVideoView.requestFocus();//播放窗口为当前窗口
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            loggerHJ.d("地址:" + videoUri.toString());
            ToastUtils.showToast(videoUri.toString());
            mVideoView.setVideoURI(videoUri);
            //mVideoView.start();//程序运行时自动开始播放视频
            //mVideoView.requestFocus();//播放窗口为当前窗口
        } else if (requestCode == MediaRecorderActivity.REQUEST_CUSTOMER_VIDEO_CAPTURE) {
            Uri videoUri = intent.getData();
            loggerHJ.d("地址:" + videoUri.toString());
            ToastUtils.showToast(videoUri.toString());
            mVideoView.setVideoURI(videoUri);
            //mVideoView.start();//程序运行时自动开始播放视频
            //mVideoView.requestFocus();//播放窗口为当前窗口
        } else if (requestCode == LocalVideoListActivity2.LOCAL_VIDEO_RESULT) {
            Uri videoUri = intent.getData();
            loggerHJ.d("地址:" + videoUri.toString());
            ToastUtils.showToast(videoUri.toString());
            mVideoView.setVideoURI(videoUri);
            //mVideoView.start();//程序运行时自动开始播放视频
            //mVideoView.requestFocus();//播放窗口为当前窗口
        }
    }
}