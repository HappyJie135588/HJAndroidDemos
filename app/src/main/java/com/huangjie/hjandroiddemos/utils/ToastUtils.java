package com.huangjie.hjandroiddemos.utils;

import android.widget.Toast;

/**
 * Created by HuangJie on 2017/7/3.
 *  测试Toast正式发布不打印
 */

public class ToastUtils {
    private static boolean IS_SHOW_TEST_TOAST=true;

    public static void showToast(CharSequence test){
        if(IS_SHOW_TEST_TOAST) {
            Toast.makeText(UIUtils.getContext(), test, Toast.LENGTH_SHORT).show();
        }
    }
}
