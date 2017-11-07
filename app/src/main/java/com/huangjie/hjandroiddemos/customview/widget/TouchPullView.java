package com.huangjie.hjandroiddemos.customview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.MyLogger;
import com.huangjie.hjandroiddemos.utils.UIUtils;

/**
 * Created by HuangJie on 2017/10/24.
 */

public class TouchPullView extends View {
    public static MyLogger loggerHJ = MyLogger.getHuangJie();
    //圆的画笔
    private Paint mCirclePaint;
    //半径
    private float mCircleRadius = 50;
    //进度值
    private float mProgress;
    private float mCirclePointX;
    private float mCirclePointY;
    //可拖动的高度
    private int mDrayHeight = 300;
    //目标宽度
    private int mTargetWidth = 400;
    //贝塞尔曲线的路径以及画笔
    private Path mPath = new Path();
    private Paint mPathPaint;
    //重心点最终高度,决定控制点的Y坐标
    private int mTargetGravityHeight = 10;
    //角度变换 0-120度
    private int mTangentAngle = 105;

    private Interpolator mProgressInterpolator = new DecelerateInterpolator();
    private Interpolator mTanentAngleInterpolator;

    private Drawable mContent = null;
    private int mContentMargin = 0;


    public TouchPullView(Context context) {
        this(context, null);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        //得到用户设置的参数
        Context context = getContext();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TouchPullView);
        int color = array.getColor(R.styleable.TouchPullView_pColor, 0xFF4081);
        mCircleRadius = array.getDimension(R.styleable.TouchPullView_pRadius, mCircleRadius);
        mDrayHeight = array.getDimensionPixelOffset(R.styleable.TouchPullView_pDragHeight, mDrayHeight);
        mTangentAngle = array.getInteger(R.styleable.TouchPullView_pTangentAngle, mTangentAngle);
        mTargetWidth = array.getDimensionPixelOffset(R.styleable.TouchPullView_pTargetWidth, mTargetWidth);
        mTargetGravityHeight = array.getDimensionPixelOffset(R.styleable.TouchPullView_pTargetGravityHeight, mTargetGravityHeight);
        mContent = array.getDrawable(R.styleable.TouchPullView_pContentDrawable);
        mContentMargin = array.getDimensionPixelOffset(R.styleable.TouchPullView_pContentDrawableMargin, 0);
        //销毁
        array.recycle();
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置抗锯齿
        p.setAntiAlias(true);
        //设置防抖动
        p.setDither(true);
        //设置填充方式
        p.setStyle(Paint.Style.FILL);
        p.setColor(UIUtils.getColor(R.color.color_fFa0b1));
        mCirclePaint = p;

        //初始化路径部分画笔
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置抗锯齿
        p.setAntiAlias(true);
        //设置防抖动
        p.setDither(true);
        //设置填充方式
        p.setStyle(Paint.Style.FILL);
        p.setColor(UIUtils.getColor(R.color.color_ff4081));
        mPathPaint = p;

        //切角路径插值器
        mTanentAngleInterpolator = PathInterpolatorCompat.create(
                (mCircleRadius * 2.0f) / mDrayHeight,
                90.0f / mTangentAngle);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //进行基础坐标参数系改变
        int count = canvas.save();
        float tranX = (getWidth() - getValueByLine(getWidth(), mTargetWidth, mProgress)) / 2;
        canvas.translate(tranX, 0);
        //画贝塞尔曲线
        canvas.drawPath(mPath, mPathPaint);
        //画圆
        canvas.drawCircle(mCirclePointX, mCirclePointY, mCircleRadius, mCirclePaint);

        Drawable drawable = mContent;
        if (drawable != null) {
            canvas.save();
            //剪切矩形区域
            canvas.clipRect(drawable.getBounds());
            drawable.draw(canvas);
            canvas.restore();
        }

        canvas.restoreToCount(count);
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
        this.mProgress = progress;
        requestLayout();//请求重新进行测量
    }

    /**
     * 更新我们的路径等相关操作
     */
    private void updatePathLayout() {
        //获取进度
        final float progress = mProgressInterpolator.getInterpolation(mProgress);
        //获取可绘制区域高度宽度
        //final float w = getWidth();
        final float w = getValueByLine(getWidth(), mTargetWidth, mProgress);
        final float h = getValueByLine(0, mDrayHeight, mProgress);
        //X对称轴的参数,圆的圆心X
        final float cPointX = w / 2.0f;
        final float cRadius = mCircleRadius;
        //圆的圆心Y坐标
        final float cPointY = h - cRadius;
        //控制点结束Y的值
        final float endControlY = mTargetGravityHeight;
        //更新圆的坐标
        mCirclePointX = cPointX;
        mCirclePointY = cPointY;
        loggerHJ.d("progress:" + progress + " cPointx:" + cPointX + " cPointy:" + cPointY);
        //路径
        final Path path = mPath;
        //复位操作
        path.reset();
        path.moveTo(0, 0);

        //左边部分的结束点和控制点
        float lEndPointX, lEndPointY;
        float lControlPointX, lControlPointY;

        //获取当前切线的弧度
        float angle = mTangentAngle * mTanentAngleInterpolator.getInterpolation(progress);
        double radian = Math.toRadians(angle);
        float x = (float) (Math.sin(radian) * cRadius);
        float y = (float) (Math.cos(radian) * cRadius);

        //
        lEndPointX = cPointX - x;
        lEndPointY = cPointY + y;

        //控制点的Y坐标变化
        lControlPointY = getValueByLine(0, endControlY, progress);
        //控制点与结束点之间的高度
        float tHeight = lEndPointY - lControlPointY;
        //控制点与X的坐标距离
        float tWidth = (float) (tHeight / Math.tan(radian));
        lControlPointX = lEndPointX - tWidth;

        //贝塞尔曲线
        path.quadTo(lControlPointX, lControlPointY, lEndPointX, lEndPointY);
        //链接到右边
        path.lineTo(cPointX + (cPointX - lEndPointX), lEndPointY);
        //画右边的贝塞尔曲线
        path.quadTo(cPointX + cPointX - lControlPointX, lControlPointY, w, 0);
        //更新内容部分Drawable
        updateContentLayout(cPointX, cPointY, cRadius);

    }

    /**
     * 对内容部分进行测量并设置
     *
     * @param cx     圆心X
     * @param cy     圆心Y
     * @param radius 半径
     */
    private void updateContentLayout(float cx, float cy, float radius) {
        Drawable drawable = mContent;
        if (drawable != null) {
            int margin = mContentMargin;
            int l = (int) (cx - radius + margin);
            int r = (int) (cx + radius - margin);
            int t = (int) (cy - radius + margin);
            int b = (int) (cy + radius - margin);
            drawable.setBounds(l, t, r, b);
        }
    }

    //释放动画
    private ValueAnimator valueAnimator;

    /**
     * 添加释放动作
     */

    public void release() {
        if (valueAnimator == null) {
            ValueAnimator animator = ValueAnimator.ofFloat(mProgress, 0f);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(400);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object val = animation.getAnimatedValue();
                    if (val instanceof Float) {
                        setProgress((Float) val);
                    }
                }
            });
            valueAnimator = animator;
        } else {
            valueAnimator.cancel();
            valueAnimator.setFloatValues(mProgress, 0f);
        }
        valueAnimator.start();
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
