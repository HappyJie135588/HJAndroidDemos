package com.huangjie.hjandroiddemos.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends BaseActivity {

    private TestService.MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (TestService.MyBinder) service;
            myBinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @OnClick({R.id.start_service, R.id.stop_service, R.id.bind_service, R.id.unbind_service})
    public void OnClickBtn(View view) {
        switch (view.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this, TestService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                loggerHJ.d("click Stop Service button");
                Intent stopIntent = new Intent(this, TestService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, TestService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                loggerHJ.d("click Unbind Service button");
                unbindService(connection);
                break;
        }
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ServiceActivity.class);
        activity.startActivity(intent);
    }
}

