package com.huangjie.hjandroiddemos.service;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.service.entity.FileInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DownloadActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.pb_progress)
    ProgressBar pb_progress;
    @BindView(R.id.bt_stop)
    Button bt_stop;
    @BindView(R.id.bt_start)
    Button bt_start;

    private FileInfo fileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        fileInfo = new FileInfo(0,
                "http://sw.bos.baidu.com/sw-search-sp/software/291d87601be4d/kugou_8.1.71.20109_setup.exe",
                "kugou_8.1.71.20109_setup.exe", 0, 0);
    }

    @OnClick({R.id.bt_stop, R.id.bt_start})
    public void OnClickBtn(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                Intent startIntent = new Intent(this, DownloadService.class);
                startIntent.setAction(DownloadService.ACTION_START);
                startIntent.putExtra(DownloadService.FILE_INFO, fileInfo);
                startService(startIntent);
                break;
            case R.id.bt_stop:
                Intent stopIntent = new Intent(this, DownloadService.class);
                stopIntent.setAction(DownloadService.ACTION_STOP);
                stopIntent.putExtra(DownloadService.FILE_INFO, fileInfo);
                startService(stopIntent);
                break;
        }
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, DownloadActivity.class);
        activity.startActivity(intent);
    }
}
