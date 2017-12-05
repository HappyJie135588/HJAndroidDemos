package com.huangjie.hjandroiddemos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.huangjie.hjandroiddemos.customview.CustomViewActivity;
import com.huangjie.hjandroiddemos.fragment.TestFragmentActivity;
import com.huangjie.hjandroiddemos.live.LiveListActivity;
import com.huangjie.hjandroiddemos.mediarecorderdemo.MediaActivity;
import com.huangjie.hjandroiddemos.preference.SettingActivity;
import com.huangjie.hjandroiddemos.qqslidemenu.TestQQSlideMenuActivity;
import com.huangjie.hjandroiddemos.rxjavademo.RxjavaDemoActivity;
import com.huangjie.hjandroiddemos.service.DownloadActivity;
import com.huangjie.hjandroiddemos.service.ServiceActivity;
import com.huangjie.hjandroiddemos.utils.ToastUtils;
import com.huangjie.hjandroiddemos.websocket.WebSocketActivity;
import com.huangjie.hjandroiddemos.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_CAMERA_PERMISSON_CODE = 1;
    private static final int REQUEST_PERMISSIONS_CODE = 2;

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
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write SDcard");
        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
            permissionsNeeded.add("Record_audio");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "你需要允许使用以下权限 " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessage(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_PERMISSIONS_CODE);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(MainActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_PERMISSIONS_CODE);
            return;
        }
        MediaActivity.actionStart(this);
    }


    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return false;
            }
        }
        return true;
    }

//        int checkCode = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
//        loggerHJ.d("checkCode:" + checkCode);
//        if (checkCode != PackageManager.PERMISSION_GRANTED) {
//            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
//            boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
//            loggerHJ.d("shouldShow:" + shouldShow);
//            if (!shouldShow) {
//                // Explain to the user why we need to read the contacts
//                showMessage("请允许应用对SD卡进行读写操作", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSON_CODE);
//                    }
//                });
//                return;
//            }
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSON_CODE);
//        }
    //MediaActivity.actionStart(this);
//}

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
            case REQUEST_PERMISSIONS_CODE: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    MediaActivity.actionStart(this);
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "有权限被禁用了请到设计页进行权限设置", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.btn_customerview)
    public void btn_customerview() {
        CustomViewActivity.actionStart(this);
    }

    @OnClick(R.id.btn_webview)
    public void btn_webview() {
        WebViewActivity.actionStart(this);
    }

    @OnClick(R.id.btn_websocket)
    public void btn_websocket() {
        WebSocketActivity.actionStart(this);
    }

    @OnClick(R.id.btn_service)
    public void btn_service() {
        ServiceActivity.actionStart(this);
    }

    @OnClick(R.id.btn_download)
    public void btn_download() {
        DownloadActivity.actionStart(this);
    }

    @OnClick(R.id.btn_fragment)
    public void btn_fragment() {
        TestFragmentActivity.actionStart(this);
    }

    @OnClick(R.id.btn_crash)
    public void btn_crash() {
        String a = null;
        loggerHJ.d(a.equals("a"));
    }

    @OnClick(R.id.btn_live)
    public void btn_live() {
        LiveListActivity.actionStart(this);
    }

    @OnClick(R.id.btn_preference)
    public void btn_preference() {
        SettingActivity.actionStart(this);
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
