package com.huangjie.hjandroiddemos;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huangjie.hjandroiddemos.customview.CustomViewActivity;
import com.huangjie.hjandroiddemos.mediarecorderdemo.MediaActivity;
import com.huangjie.hjandroiddemos.qqslidemenu.TestQQSlideMenuActivity;
import com.huangjie.hjandroiddemos.rxjavademo.RxjavaDemoActivity;
import com.huangjie.hjandroiddemos.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_CAMERA_PERMISSON_CODE = 1;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.sample_text)
    public TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        getWindow().setStatusBarColor(Color.parseColor("#f8f8f8"));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @OnClick(R.id.btn_rxjava)
    public void btn_rxjava() {
        RxjavaDemoActivity.actionStart(this);
    }

    @OnClick(R.id.btn_qqslidemenu)
    public void btn_qqslidemenu() {
        TestQQSlideMenuActivity.actionStart(this);
    }

    @OnClick(R.id.btn_mediarecord)
    public void btn_mediarecord() {
        int checkCode = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        loggerHJ.d("checkCode:" + checkCode);
        if (checkCode != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
            loggerHJ.d("shouldShow:" + shouldShow);
            if (!shouldShow) {
                // Explain to the user why we need to read the contacts
                showMessage("请允许应用对SD卡进行读写操作", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSON_CODE);
                    }
                });
                return;
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSON_CODE);
        }
        //MediaActivity.actionStart(this);
    }

    private void showMessage(String message, DialogInterface.OnClickListener okListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        loggerHJ.d("requestCode:" + requestCode);
        loggerHJ.d("permissions:" + gson.toJson(permissions));
        loggerHJ.d("requestCode:" + gson.toJson(grantResults));
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSON_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.btn_customerview)
    public void btn_customerview() {
        CustomViewActivity.actionStart(this);
    }

    private static long firstTime;

    /**
     * 连续按两次返回键退出程序
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (firstTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            ToastUtils.showToast("再按一次退出程序");
        }
        firstTime = System.currentTimeMillis();
    }

}
