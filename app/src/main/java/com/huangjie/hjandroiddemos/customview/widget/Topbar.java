package com.huangjie.hjandroiddemos.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.R;

import java.security.Policy;

/**
 * Created by HuangJie on 2017/8/4.
 */

public class Topbar extends RelativeLayout {
    private TextView tvTitle;
    private String   title;
    private float    titleTextSize;
    private int      titleTextColor;
    private Drawable titleBackground;

    private LayoutParams titleParams;

    public Topbar(Context context) {
        super(context);
    }

    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar);

        title = ta.getString(R.styleable.Topbar_title);
        titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize, 0);
        titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColor, 0);
        titleBackground = ta.getDrawable(R.styleable.Topbar_titleBackground);

        ta.recycle();

        tvTitle = new TextView(context);
        tvTitle.setText(title);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setBackgroundDrawable(titleBackground);

        setBackgroundResource(R.color.colorAccent);
        titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(tvTitle, titleParams);
    }
}
