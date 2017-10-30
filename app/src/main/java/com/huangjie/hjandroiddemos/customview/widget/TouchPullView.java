package com.huangjie.hjandroiddemos.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.huangjie.hjandroiddemos.utils.MyLogger;

/**
 * Created by HuangJie on 2017/10/24.
 */

public class TouchPullView extends View {
    public static MyLogger loggerHJ = MyLogger.getHuangJie();
    //圆的画笔
    private Paint mCirclePaint;
    //半径
    private float mCircleRadius = 40;
    //进度值
    private float mProgress;
    private float mCirclePointX;
    private float mCirclePointY;
    //可拖动的高度
    private int mDrayHeight = 800;
    //目标宽度
    private int mTargetWidth;
    //贝塞尔曲线的路径以及画笔
    private Path mPath = new Path();
    private Paint mPathPaint;
    //重心点最终高度,决定控制点的Y坐标
    private int mTargetGravityHeight;
    //角度变换 0-120度
    private int mTargetAngle = 120;

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
        //设置抗锯齿
        p.setAntiAlias(true);
        //设置防抖动
        p.setDither(true);
        //设置填充方式
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xFF000000);
        mCirclePaint = p;

        //初始化路径部分画笔
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置抗锯齿
        p.setAntiAlias(true);
        //设置防抖动
        p.setDither(true);
        //设置填充方式
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xFF000000);
        mPathPaint = p;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画贝塞尔曲线
        canvas.drawPath(mPath, mPathPaint);
        //画圆
        canvas.drawCircle(mCirclePointX, mCirclePointY, mCircleRadius, mCirclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int iWidth = (int) (2 * mCircleRadius + getPaddingLeft() + getPaddingRight());
        int iHeight = (int) (mDrayHeight * mProgress + 0.5f) + getPaddingTop() + getPaddingBottom();

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
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //当高度变化时进行路径更新
        updatePathLayout();

    }

    public void setProgress(float progress) {
        loggerHJ.d("progress" + progress);
        this.mProgress = progress;
        requestLayout();//请求重新进行测量
    }

    /**
     * 更新我们的路径等相关操作
     */
    private void updatePathLayout() {
        //获取进度
        final float progress = mProgress;
        //获取可绘制区域高度宽度
        final float w = getValueByLine(getWidth(),mTargetWidth,progress);
        final float h = getValueByLine(getHeight(),mDrayHeight,progress);
        //X对称轴的参数,圆的圆心X
        final float cPointx = w/2;
        final float cRadius = mCircleRadius;
        //圆的圆心Y坐标
        final float cPointy = h - cRadius;
        //控制点结束Y的值
        final float endControlY = mTargetGravityHeight;
        //更新圆的坐标
        mCirclePointX = cPointx;
        mCirclePointY = cPointy;
        //路径
        final Path path = mPath;
        //复位操作
        path.reset();
        path.moveTo(0,0);
    }

    /**
     * 获取当前值
     *
     * @param start    起始值
     * @param end      结束值
     * @param progress 进度
     * @return 当前进度的值
     */
    private float getValueByLine(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

}
