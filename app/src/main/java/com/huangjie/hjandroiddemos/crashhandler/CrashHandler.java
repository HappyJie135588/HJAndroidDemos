package com.huangjie.hjandroiddemos.crashhandler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by HuangJie on 2017/11/28.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //用来存储设备信息与异常信息
    private Map<String, String> mInfo = new HashMap<>();
    //文件日期格式
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CrashHandler() {
    }

    private static class Holder {
        private static final CrashHandler INSTANCE = new CrashHandler();
    }

    public static CrashHandler getInstance() {
        return Holder.INSTANCE;

    }

    public void init(Context context) {
        this.mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //1,收集错误信息;
        //2,保存错误信息;
        //3,上传到服务器;
        if (!handleException(e)) {
            //未处理,调用系统默认的处理器处理
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(t, e);
            } else {
                //已经人为处理
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                Process.killProcess(Process.myPid());
                System.exit(1);
            }

        }
    }

    /**
     * 人为处理异常
     *
     * @param e
     * @return true:已经处理 false:没有处理
     */
    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }
        //toast提示
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "UncaugthException", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //收集错误信息
        collectErrorInfo();
        //保存错误信息
        saveErrorInfo(e);
        return false;
    }

    private void collectErrorInfo() {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = TextUtils.isEmpty(pi.versionName) ? "未设置版本名称" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mInfo.put("versionName", versionName);
                mInfo.put("versionCode", versionCode);
            }
            Field[] fields = Build.class.getFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        mInfo.put(field.getName(), field.get(null).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveErrorInfo(Throwable e) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : mInfo.entrySet()) {
            String keyName = entry.getKey();
            String value = entry.getValue();
            stringBuffer.append(keyName + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause !=null){
            cause.printStackTrace(printWriter);
            cause = e.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        stringBuffer.append(result);
        long curTime = System.currentTimeMillis();
        String time = mDateFormat.format(new Date());
        String fileName = "crash-"+time+"-"+curTime+".log";
        //判断 有没有SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path = "/sdcard/crash";
            File dir = new File(path);
            if(!dir.exists()){
                dir.mkdir();
            }
            FileOutputStream fos =null;
            try {
                fos = new FileOutputStream(path+fileName);
                fos.write(stringBuffer.toString().getBytes());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }
}
