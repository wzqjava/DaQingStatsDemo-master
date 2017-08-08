package com.stats.daqing.feature.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.utils.SPUtil;


public class SettingActivity extends BaseActivity implements View.OnClickListener {


    private TintToolbar mToolBar;
    private RelativeLayout rlPersonalInfo;
    private RelativeLayout rlAbout;
    private RelativeLayout rlExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        setListener();
    }


    private void initView() {
        rlPersonalInfo = (RelativeLayout) findViewById(R.id.rl_personal_info);
        rlAbout = (RelativeLayout) findViewById(R.id.rl_about);
        rlExit = (RelativeLayout) findViewById(R.id.rl_exit);

        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(null);
            // 设置返回按钮
            supportActionBar.setHomeButtonEnabled(true);
        }
    }


    private void setListener() {
        rlPersonalInfo.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        rlExit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.rl_personal_info:
                // 个人中心
                intent = new Intent(SettingActivity.this, PersonalInfoActivity.class);
                startActivity(intent);

                break;

            case R.id.rl_about:
                // 关于
                intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);

                break;

            case R.id.rl_exit:
                // 退出
                ToastAlone.showShortToast("退出");
                SPUtil.clear(SPUtil.TYPE_USER);
                finish();
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }









}
