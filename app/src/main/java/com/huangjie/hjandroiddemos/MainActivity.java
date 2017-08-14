package com.huangjie.hjandroiddemos;

import android.os.Bundle;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.customview.CustormViewActivity;
import com.huangjie.hjandroiddemos.mediarecorderdemo.MediaActivity;
import com.huangjie.hjandroiddemos.qqslidemenu.TestQQSlideMenuActivity;
import com.huangjie.hjandroiddemos.rxjavademo.RxjavaDemoActivity;
import com.huangjie.hjandroiddemos.utils.ToastUtils;

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
        MediaActivity.actionStart(this);
    }

    @OnClick(R.id.btn_customerview)
    public void btn_customerview() {
        CustormViewActivity.actionStart(this);
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
