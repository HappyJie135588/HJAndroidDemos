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

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        canvas.drawPoint(500, 100, mPaint);//二阶控制点
        canvas.drawPoint(400, 600, mPaint);//三阶控制点
        canvas.drawPoint(600, 1000, mPaint);//三阶控制点
    }
}
