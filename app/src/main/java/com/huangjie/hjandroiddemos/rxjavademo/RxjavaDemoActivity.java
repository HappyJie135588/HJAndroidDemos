package com.huangjie.hjandroiddemos.rxjavademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxjavaDemoActivity extends BaseActivity {
    @BindView(R.id.tv2)
    public TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_demo);
        ButterKnife.bind(this);
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, RxjavaDemoActivity.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.test1)
    public void test1() {
        Watcher watcher1 = new TestWather();
        Watcher watcher2 = new TestWather();
        Watcher watcher3 = new TestWather();
        Watched watched  = new TestWathed();
        watched.addWatcher(watcher1);
        watched.addWatcher(watcher2);
        watched.addWatcher(watcher3);
        watched.notifyWatcher("被观察者更新了");
    }

    @OnClick(R.id.test2)
    public void test2() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Rxjava1订阅成功哈哈哈");
            }
        });

        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                tv2.setText(s);
            }
        };
        observable.subscribe(subscriber);
    }

    @OnClick(R.id.test3)
    public void test3() {
        Observable observable = Observable.just("Rxjava just Action1");
        observable.subscribe(new Action1<String>() {

            @Override
            public void call(String s) {
                tv2.setText(s);
            }
        });
    }

    @OnClick(R.id.test4)
    public void test4() {
        Observable.just("链式Rxjava just Action1").subscribe(new Action1<String>() {

            @Override
            public void call(String s) {
                tv2.setText(s);
            }
        });
    }

    @OnClick(R.id.test5)
    public void test5() {
        Observable.just("Rxjava just lambda哈哈哈").subscribe(s -> tv2.setText(s));
    }

    @OnClick(R.id.test6)
    public void test6() {
        Observable.just("这是一个字符串").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s + "用map表达式添加的字符串";
            }
        }).subscribe(s -> tv2.setText(s));
    }

    @OnClick(R.id.test7)
    public void test7() {
        Observable.just("这是一个字符串").map(s -> s + "用lambda写的map表达式添加的字符串").subscribe(s -> tv2.setText(s));
    }

    @OnClick(R.id.test8)
    public void test8() {
        Observable.just("这是一个字符串").map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.hashCode();
            }
        }).subscribe(integer -> tv2.setText(integer.toString()));
    }

    @OnClick(R.id.test9)
    public void test9() {
        Observable.just("这是一个字符串").map(s -> s.hashCode()).subscribe(integer -> tv2.setText(integer.toString()));
    }

    @OnClick(R.id.test10)
    public void test10() {
        List<String> list = Arrays.asList("Android", "Ios", "C/C++", "CoCos2D");
        Observable.from(list).subscribe(s -> loggerHJ.d(s));
    }

    @OnClick(R.id.test11)
    public void test11() {
        List<String> list = Arrays.asList("Android", "Ios", "C/C++", "CoCos2D");
        Observable.just(list).flatMap(new Func1<List<String>, Observable<?>>() {
            @Override
            public Observable<?> call(List<String> strings) {
                return Observable.from(strings);
            }
        }).subscribe(s -> loggerHJ.d("flatMap " + s));
    }

    @OnClick(R.id.test12)
    public void test12() {
        List<String> list = Arrays.asList("Android", "Ios", "C/C++", "CoCos2D");
        Observable.from(list).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s.length() > 6;
            }
        }).subscribe(s -> loggerHJ.d("flatMap " + s));
    }

    @OnClick(R.id.test13)
    public void test13() {
        //take指定最多输出数量
        //doOnNext每次输出一个元素之前做一些额外的事情
        List<String> list = Arrays.asList("Android", "Ios", "C/C++", "CoCos2D");
        Observable.from(list).take(3).doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                loggerHJ.d("doOnNext " + s);
            }
        }).subscribe(s -> loggerHJ.d("flatMap " + s));
    }

    @OnClick(R.id.test14)
    public void test14() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://v.juhe.cn/weather/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        Weather                 weather    = retrofit.create(Weather.class);
        Observable<WeatherBean> observable = weather.getWeather("成都", "json", 1, "9c3be48c5a14be9130c3c9af4b87f551");
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<WeatherBean>() {
            @Override
            public void onCompleted() {
                loggerHJ.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                loggerHJ.d(e.getMessage());
            }

            @Override
            public void onNext(WeatherBean weatherBean) {
                loggerHJ.d(weatherBean.toString());
                Toast.makeText(UIUtils.getContext(), weatherBean.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
