package com.huangjie.hjandroiddemos.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.huangjie.hjandroiddemos.utils.MyLogger;

/**
 * Created by HuangJie on 2017/10/24.
 */

public class TouchPullView extends View {
    public static MyLogger loggerHJ = MyLogger.getHuangJie();
    private Paint mCirclePaint;//圆的画笔
    private int mCircleRadius = 100;//半径
    private float progress;
    private int mCirclePointX;
    private int mCirclePointY;
    private int mDrayHeight = 800;//可拖动的高度

    public TouchPullView(Context context) {
        this(context, null);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setAntiAlias(true);//设置抗锯齿
        p.setDither(true);//设置防抖动
        p.setStyle(Paint.Style.FILL);//设置填充方式
        p.setColor(0xFF000000);
        mCirclePaint = p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCirclePointX, mCirclePointY, mCircleRadius, mCirclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int iWidth = 2 * mCircleRadius + getPaddingLeft() + getPaddingRight();
        int iHeight = (int) (mDrayHeight * progress + 0.5f) + getPaddingTop() + getPaddingBottom();

        int measureWidth, measureHeight;

        if (widthMode == MeasureSpec.EXACTLY) {//确切的
            measureWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {//最多
            measureWidth = Math.min(iWidth, widthSize);
        } else /*if (widthMode == MeasureSpec.UNSPECIFIED)*/ {//未知
            measureWidth = iWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {//确切的
            measureHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {//最多
            measureHeight = Math.min(iHeight, heightSize);
        } else /*if (heightMode == MeasureSpec.UNSPECIFIED)*/ {//未知
            measureHeight = iHeight;
        }
        //设置测量的宽高
        setMeasuredDimension(measureWidth, measureHeight);
    }

    /**
     * 当大小改变时触发
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCirclePointX = getWidth() >> 1;//除以2
        mCirclePointY = getHeight() >> 1;
    }

    public void setProgress(float progress) {
        loggerHJ.d("progress" + progress);
        this.progress = progress;
        requestLayout();//请求重新进行测量
    }

}
