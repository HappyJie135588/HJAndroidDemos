package com.huangjie.hjandroiddemos.service;

import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.service.entity.FileInfo;
import com.huangjie.hjandroiddemos.utils.MyLogger;

/**
 * Created by HuangJie on 2017/11/14.
 */

public class FileListAdapter extends BaseQuickAdapter<FileInfo, BaseViewHolder> {
    MyLogger loggerHJ = MyLogger.getHuangJie();
    public FileListAdapter() {
        super(R.layout.item_download_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileInfo item) {
        loggerHJ.d(item.toString());
        helper.setText(R.id.tv_name, item.getFileName());
        ProgressBar pb_progress = helper.getView(R.id.pb_progress);
        pb_progress.setProgress(item.getFinished());
        helper.addOnClickListener(R.id.bt_start);
        helper.addOnClickListener(R.id.bt_stop);
    }
}
