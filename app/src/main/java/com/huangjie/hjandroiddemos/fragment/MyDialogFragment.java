package com.huangjie.hjandroiddemos.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangjie.hjandroiddemos.R;

/**
 * Created by HuangJie on 2017/11/21.
 */

public class MyDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.create_group_dialog, null);
        Dialog dialog = new CircleDialog(getContext(), view, R.style.dialog);
        return dialog;
    }
}
