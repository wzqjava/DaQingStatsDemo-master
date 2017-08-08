package com.stats.daqing.feature.activity;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.bean.FindPasswordBean;
import com.stats.daqing.bean.QuestionsBean;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.common.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class FindPasswordActivity extends BaseActivity implements View.OnClickListener {


    private ArrayAdapter<String> arr_adapter;
    private List<QuestionsBean> questions;
    private QuestionsBean currentQuestion;
    private LinearLayout llPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        initView();
        setListener();
        getQuestion();
    }


    private void setListener() {
        btnFindPassword.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                currentQuestion = questions.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }




















    private TintToolbar toolbar;
    private TextView tvPwd;
    private Spinner spinner;
    private EditText etAnswer;
    private EditText etPhone;
    private Button btnFindPassword;

    private void initView() {
        toolbar = (TintToolbar) findViewById(R.id.toolbar);
        tvPwd = (TextView) findViewById(R.id.tv_pwd);
        spinner = (Spinner) findViewById(R.id.spinner);
        etAnswer = (EditText) findViewById(R.id.et_answer);
        etPhone = (EditText) findViewById(R.id.et_phone);
        llPhone = (LinearLayout)findViewById(R.id.ll_phone);
        btnFindPassword = (Button) findViewById(R.id.btn_find_password);




        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(null);
            // 设置返回按钮
            supportActionBar.setHomeButtonEnabled(true);
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


    /**
     * 获取注册密保问题
     */
    private void getQuestion(){
        RequestParams entity = new RequestParams(Urls.URL_APP_REGISTER_QUESTIONS);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                questions = parseJson2Array(result);
                ArrayList<String> questionData = new ArrayList<>();

                //数据
                for (int i = 0; i < questions.size(); i++) {
                    questionData.add(questions.get(i).getQuestions());
                }

                //适配器
                arr_adapter= new ArrayAdapter<String>(FindPasswordActivity.this, android.R.layout.simple_spinner_item, questionData);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //加载适配器
                spinner.setAdapter(arr_adapter);

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


    /**
     *
     */
    private void findPassword(){
        String phone = etPhone.getText().toString();
        String answer = etAnswer.getText().toString();

        RequestParams entity = new RequestParams(Urls.URL_APP_FIND_PASSWORD);
        entity.addBodyParameter("phoneNum",phone);
        entity.addBodyParameter("passId",currentQuestion.getId() + "");
        entity.addBodyParameter("passAnswers",answer);

        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                FindPasswordBean bean = gson.fromJson(result, FindPasswordBean.class);

                if (bean.getStatus() == 200) {
                    llPhone.setVisibility(View.VISIBLE);
                    tvPwd.setText(bean.getPassword());
                }else{
                    llPhone.setVisibility(View.GONE);
                }
                ToastAlone.showShortToast(bean.getMessage());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                llPhone.setVisibility(View.GONE);
                ToastAlone.showShortToast("找回密码失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }




    /**
     * 解析没有数据头的纯数组
     */
    private List<QuestionsBean> parseJson2Array(String json) {

        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(json).getAsJsonArray();

        Gson gson = new Gson();
        ArrayList<QuestionsBean> questions = new ArrayList<>();

        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            QuestionsBean bean = gson.fromJson(user, QuestionsBean.class);
            questions.add(bean);
        }

        return questions;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_find_password:
                findPassword();
                break;
        }
    }
}
