package com.huangjie.hjandroiddemos.rxjavademo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by HuangJie on 2017/7/17.
 */

public interface Weather {
    @GET("index")
    Observable<WeatherBean> getWeather(@Query("cityname") String cityname,@Query("dtype") String dtype,@Query("format") int format,@Query("key") String key);
}
