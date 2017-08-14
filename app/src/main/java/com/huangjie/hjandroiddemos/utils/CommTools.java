package com.huangjie.hjandroiddemos.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016-05-06.
 */
public class CommTools {

    // 将长整型转换成时间格式
    public static String LongToHms(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        return dateFormat.format(time);
    }

    // 将长整型转换成带两位小数点的string格式
    public static String LongToPoint(long size) {
        float i = Float.parseFloat(String.valueOf(size));
        DecimalFormat fnum = new DecimalFormat("##0.00");
        if (i / 1024 / 1024 > 500) {
            return fnum.format(i / 1024 / 1024 / 1024) + " G";
        } else {
            return fnum.format(i / 1024 / 1024) + " M";
        }
    }

}
