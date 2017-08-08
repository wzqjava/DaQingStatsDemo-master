package com.stats.daqing.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.stats.daqing.R;
import com.stats.daqing.common.ThemeHelper;

import org.xutils.common.util.LogUtil;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_base);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary_dark));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null,
                    ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description);
        }
    }

    /**
     * 更新主题颜色
     * @param currentTheme
     */
    public void updateTheme(int currentTheme) {
        LogUtil.e("updateTheme: currentTheme" + currentTheme);
        if (ThemeHelper.getTheme(this) != currentTheme) {
            ThemeHelper.setTheme(this, currentTheme);
            ThemeUtils.refreshUI(this, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            //for global setting, just do once
                            if (Build.VERSION.SDK_INT >= 21) {
                                // toolbar背景色
                                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(getBaseContext(), android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);
                                // 状态栏颜色
                                getWindow().setStatusBarColor(ThemeUtils.getColorById(getBaseContext(), R.color.theme_color_primary_dark));
                            }
                        }

                        @Override
                        public void refreshSpecificView(View view) {
                            //TODO: will do this for each traversal
                        }
                    }
            );
        }
    }

}
