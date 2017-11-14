package com.huangjie.hjandroiddemos.service;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.service.entity.FileInfo;

import java.util.List;

/**
 * Created by HuangJie on 2017/11/14.
 */

public class FileListAdapter extends BaseQuickAdapter<FileInfo, BaseViewHolder> {
    public FileListAdapter() {
        super(R.layout.item_download_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileInfo item) {
        helper.setText(R.id.tv_name, item.getFileName());
        ProgressBar pb_progress = helper.getView(R.id.pb_progress);
        pb_progress.setMax(100);
    }
}
