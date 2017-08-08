package com.stats.daqing.feature.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.google.gson.Gson;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.bean.LoginResultBean;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.common.Urls;
import com.stats.daqing.utils.SPUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private TintToolbar mToolBar;
    private LinearLayout logLl;
    private TextView tvRegist;
    private TextView textWangjiPasswrod;
    private EditText etPassword;
    private EditText etName;
    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener(); 
    }

    private void setListener() {
        tvRegist.setOnClickListener(this);
        textWangjiPasswrod.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }


    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        logLl = (LinearLayout) findViewById(R.id.log_ll);
        etName = (EditText) findViewById(R.id.et_name);
        etPassword = (EditText) findViewById(R.id.et_password);
        tvRegist = (TextView) findViewById(R.id.tv_regist);
        textWangjiPasswrod = (TextView) findViewById(R.id.text_wangji_passwrod);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(null);
            // 设置返回按钮
            supportActionBar.setHomeButtonEnabled(true);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_regist:
                // 注册
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                break;

            case R.id.text_wangji_passwrod:
                // 忘记密码
                Intent intent1 = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent1);

                break;
            case R.id.btn_submit:
                // 登录
                login();

                break;
        }
    }

    private void login() {

        String name = etName.getText().toString();
        String password = etPassword.getText().toString();

        RequestParams params = new RequestParams(Urls.URL_APP_LOGIN);
        params.addBodyParameter("phoneNum",name);
        params.addBodyParameter("passWord",password);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                LoginResultBean resultBean = gson.fromJson(result, LoginResultBean.class);
                if (resultBean.getStatus() == 200) {
                    SPUtil.setPreferences(SPUtil.TYPE_USER,"userInfo",result);
                    ToastAlone.showShortToast("登录成功");
                    finish();
                    Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
                    startActivity(intent);
                }else{
                    ToastAlone.showShortToast(resultBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

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
