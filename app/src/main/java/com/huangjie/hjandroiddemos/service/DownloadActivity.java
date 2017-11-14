package com.huangjie.hjandroiddemos.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.service.entity.FileInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DownloadActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_file)
    public RecyclerView rv_file;
    private FileListAdapter mAdapter;
    private List<FileInfo> mInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        rv_file.setLayoutManager(new LinearLayoutManager(this));
        mAdapter= new FileListAdapter();
        mAdapter.setOnItemChildClickListener(this);
        rv_file.setAdapter(mAdapter);
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
    }
    private void initData() {
        mInfoList = new ArrayList<>();
        FileInfo fileInfo1 = new FileInfo(0,
                "http://sw.bos.baidu.com/sw-search-sp/software/291d87601be4d/kugou_8.1.71.20109_setup.exe",
                "kugou_8.1.71.20109_setup.exe", 0, 0);
        mInfoList.add(fileInfo1);
        FileInfo fileInfo2 = new FileInfo(1,
                "http://sw.bos.baidu.com/sw-search-sp/software/24b2489bc4b6f/IQIYIsetup_bdtw_6.1.51.4886.exe",
                "IQIYIsetup_bdtw_6.1.51.4886.exe", 0, 0);
        mInfoList.add(fileInfo2);
        FileInfo fileInfo3 = new FileInfo(2,
                "http://www.imooc.com/mobile/mukewang.apk",
                "mukewang.apk", 0, 0);
        mInfoList.add(fileInfo3);
        FileInfo fileInfo4 = new FileInfo(3,
                "http://sw.bos.baidu.com/sw-search-sp/software/af33424968c2b/cloudmusicsetup_2.2.2.195462.exe",
                "cloudmusicsetup_2.2.2.195462.exe", 0, 0);
        mInfoList.add(fileInfo4);
        mAdapter.setNewData(mInfoList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, DownloadActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int finished = intent.getIntExtra("finished", 0);
//                pb_progress.setProgress(finished);
            }
        }
    };

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        FileInfo fileInfo = (FileInfo) adapter.getItem(position);
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
}
