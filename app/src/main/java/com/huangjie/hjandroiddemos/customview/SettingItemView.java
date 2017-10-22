package com.huangjie.hjandroiddemos.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.R;

public class SettingItemView extends RelativeLayout {
	private CheckBox cb_box;
	private TextView tv_des;
	private String destext;
	private String offtext;
	private String ontext;

	public SettingItemView(Context context) {
		this(context,null);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		View.inflate(context, R.layout.setting_item_view, this);

		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_des = (TextView) findViewById(R.id.tv_des);
		cb_box = (CheckBox) findViewById(R.id.cb_box);
		TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.SettingItemView);
		destext = ta.getString(R.styleable.SettingItemView_destext);
		offtext = ta.getString(R.styleable.SettingItemView_offtest);
		ontext = ta.getString(R.styleable.SettingItemView_ontest);
		tv_title.setText(destext);
	}
	

	/**
	 * 判断是否开启的方法
	 * @return	返回当前SettingItemView是否选中状态	true开启(checkBox返回true)	false关闭(checkBox返回true)
	 */
	public boolean isCheck(){
		//由checkBox的选中结果,决定当前条目是否开启
		return cb_box.isChecked();
	}

	/**
	 * @param isCheck	是否作为开启的变量,由点击过程中去做传递
	 */
	public void setCheck(boolean isCheck){
		//当前条目在选择的过程中,cb_box选中状态也在跟随(isCheck)变化
		cb_box.setChecked(isCheck);
		if(isCheck){
			//开启
			tv_des.setText(ontext);
		}else{
			//关闭
			tv_des.setText(offtext);
		}
	}
	
}
