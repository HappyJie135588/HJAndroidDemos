package com.huangjie.hjandroiddemos;

import android.os.Bundle;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.mediarecorderdemo.MediaRecorderActivity;
import com.huangjie.hjandroiddemos.qqslidemenu.TestQQSlideMenuActivity;
import com.huangjie.hjandroiddemos.rxjavademo.RxjavaDemoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.sample_text)
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        MediaRecorderActivity.actionStart(this);
    }
}
