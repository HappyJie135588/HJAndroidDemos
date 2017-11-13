package com.huangjie.hjandroiddemos.websocket;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.ToastUtils;
import com.huangjie.hjandroiddemos.webview.WebViewActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebSocketActivity extends BaseActivity {

    @BindView(R.id.et_text)
    EditText et_text;
    @BindView(R.id.tv_text)
    TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);
        ButterKnife.bind(this);
        try {
            initSocketClient();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.btn_connect, R.id.btn_send, R.id.btn_close})
    public void OnBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_connect:
                connect();
                break;
            case R.id.btn_send:
                sendMsg(et_text.getText().toString().trim());
                break;
            case R.id.btn_close:
                closeConnect();
                break;
        }
    }


    //WebSocketClient 和 address
    private WebSocketClient mWebSocketClient;
    private String address = "ws://echo.websocket.org/";
//    private String address = "ws://119.146.223.64:6900/hello/websocket";


//初始化WebSocketClient

    /**
     * @throws URISyntaxException
     */
    private void initSocketClient() throws URISyntaxException {
        if (mWebSocketClient == null) {
            mWebSocketClient = new WebSocketClient(new URI(address)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    //连接成功
                    loggerHJ.d(serverHandshake.getHttpStatusMessage());
                    ToastUtils.showToast("opened connection");
                }


                @Override
                public void onMessage(String s) {
                    //服务端消息
                    loggerHJ.d(s);
//                    tv_text.setText(s);
                    ToastUtils.showToast("received:" + s);
                }


                @Override
                public void onClose(int i, String s, boolean remote) {
                    //连接断开，remote判定是客户端断开还是服务端断开
                    loggerHJ.d(("Connection closed by " + (remote ? "remote peer" : "us") + ", info=" + s));
                    ToastUtils.showToast("Connection closed by " + (remote ? "remote peer" : "us") + ", info=" + s);
                    //
                    closeConnect();
                }


                @Override
                public void onError(Exception e) {
                    loggerHJ.d(e.toString());
                    tv_text.setText(e.toString());
                    ToastUtils.showToast("error:" + e);
                }
            };
        }
    }


    //连接
    private void connect() {
//        new Thread() {
//            @Override
//            public void run() {
                mWebSocketClient.connect();
//            }
//        }.start();
    }


    //断开连接
    private void closeConnect() {
        try {
            mWebSocketClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWebSocketClient = null;
        }
    }


//发送消息

    /**
     *
     */
    private void sendMsg(String msg) {
        mWebSocketClient.send(msg);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, WebSocketActivity.class);
        activity.startActivity(intent);
    }


}
