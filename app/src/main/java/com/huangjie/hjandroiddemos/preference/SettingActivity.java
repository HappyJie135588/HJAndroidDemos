package com.huangjie.hjandroiddemos.preference;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.MyLogger;
import com.huangjie.hjandroiddemos.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends PreferenceActivity {
    protected static MyLogger loggerHJ = MyLogger.getHuangJie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从资源文件中添Preferences,选择的值将会自动保存到SharePreFerences
        addPreferencesFromResource(R.xml.checkbox);
        //CheckBoxPreference组件
        CheckBoxPreference mCheckbox0 = (CheckBoxPreference) findPreference("checkbox_0");
        mCheckbox0.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //这里可以监听到这个CheckBox 的点击事件
                return true;
            }
        });
        mCheckbox0.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //这里可以监听到checkBox中值是否改变了
                //并且可以拿到新改变的值
                ToastUtils.showToast("checkBox_0改变的值为" + (Boolean) newValue);
                return true;
            }
        });
        CheckBoxPreference mCheckbox1 = (CheckBoxPreference) findPreference("checkbox_1");
        mCheckbox1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //这里可以监听到这个CheckBox 的点击事件
                return true;
            }
        });
        mCheckbox1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //这里可以监听到checkBox中值是否改变了
                //并且可以拿到新改变的值
                ToastUtils.showToast("checkBox_1改变的值为" + (Boolean) newValue);
                return true;
            }
        });
        //EditTextPreference组件
        EditTextPreference mEditText = (EditTextPreference) findPreference("edit_0");
        mEditText.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mEditText.setSummary((CharSequence) newValue);
                return true;
            }
        });
        mEditText.setSummary(mEditText.getText());
        //设置dialog按钮信息
        mEditText.setPositiveButtonText("确定");
        mEditText.setNegativeButtonText("取消");
        //设置按钮图标
        mEditText.setDialogIcon(R.mipmap.ic_launcher);
        //自定义布局A
        Preference preference0 = findPreference("pref_key_0");
        preference0.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                loggerHJ.d("自定义布局A被修改"+newValue);
                return true;
            }
        });
        //自定义布局B
        Preference preference1 = findPreference("pref_key_1");
        preference1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ToastUtils.showToast("自定义布局B被按下");
                return true;
            }
        });
    }

    public boolean getCheckbox_0(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean checkbox_0 = prefs.getBoolean("checkbox_0",false);
        ToastUtils.showToast("单选框的值为"+checkbox_0);
        return checkbox_0;
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }
}
