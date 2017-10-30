package com.huangjie.hjandroiddemos.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.huangjie.hjandroiddemos.utils.UIUtils;

/**
 * Created by HuangJie on 2017/10/25.
 */

public class BezierView123 extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath = new Path();

    public BezierView123(Context context) {
        this(context, null);
    }

    public BezierView123(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView123(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Paint paint = mPaint;
        paint.setAntiAlias(true);//抗锯齿
        paint.setDither(true);//抗抖动
        paint.setStyle(Paint.Style.STROKE);//描边
        paint.setStrokeWidth(UIUtils.dip2px(2));

        //一阶贝塞尔曲线
        Path path = mPath;
        path.moveTo(100, 100);
        path.lineTo(300, 300);
        //二阶贝塞尔曲线
        //path.quadTo(500, 100, 700, 300);//绝对位置
        path.rQuadTo(200, -200, 400, 0);//相对位置
        //三阶贝塞尔曲线
        path.moveTo(300, 800);
        //path.cubicTo(400, 600, 600, 1000, 700, 800);//绝对位置
        path.rCubicTo(100, -200, 300, 200, 400, 0);//绝对位置

        new Thread(){
            @Override
            public void run() {
                initBezier();
            }
        }.start();

    }

    private void initBezier() {
        //四阶贝塞尔曲线
        Path path = mPath;
        path.moveTo(300, 900);
        float x[] = {300, 400, 600, 700};
        float y[] = {900, 700, 1100, 900};
        float fps = 1000;
        for (int i = 0; i <= fps; i++) {
            float progress = (float) i / fps;
            path.lineTo(calculateBezier(progress, x), calculateBezier(progress, y));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            postInvalidate();//刷新界面
        }
    }

    /**
     * 计算某时刻的贝塞尔所处的值(x或y)
     * @param t 时间0-1
     * @param values 贝塞尔点集合(x或y)
     * @return 当前t时刻的贝塞尔所处点
     */
    private float calculateBezier(float t, float... values) {
        for (int i = values.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                values[j] = values[j] + t * (values[j + 1] - values[j]);
            }
        }
        return values[0];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        canvas.drawPoint(500, 100, mPaint);//二阶控制点
        canvas.drawPoint(400, 600, mPaint);//三阶控制点
        canvas.drawPoint(600, 1000, mPaint);//三阶控制点
        canvas.drawPoint(400, 700, mPaint);//四阶控制点
        canvas.drawPoint(600, 1100, mPaint);//四阶控制点
    }
}
