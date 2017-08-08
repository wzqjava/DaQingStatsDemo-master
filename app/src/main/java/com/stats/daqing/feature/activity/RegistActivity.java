package com.stats.daqing.feature.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.bean.QuestionsBean;
import com.stats.daqing.bean.LoginResultBean;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.common.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 注册
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener {

    private TintToolbar mToolBar;
    private EditText registerEmail;
    private EditText registerName;
    // private TextView textSelectWt;
    private EditText registerAnswer;
    private EditText registerPhone;
    private Button registerSubmit;
    private Spinner spinner;
    private ArrayAdapter<String> arr_adapter;
    private List<QuestionsBean> questions;
    private QuestionsBean currentQuestion;
    private EditText registerPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();

        setListener();
        initData();
    }

    private void setListener() {
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


        registerSubmit.setOnClickListener(this);
    }

    private void initData() {
        getQuestion();
    }


    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        registerEmail = (EditText) findViewById(R.id.register_email);
        registerName = (EditText) findViewById(R.id.register_name);
        registerPwd = (EditText) findViewById(R.id.register_pwd);
        // textSelectWt = (TextView) findViewById(R.id.text_select_wt);
        registerAnswer = (EditText) findViewById(R.id.register_answer);
        registerPhone = (EditText) findViewById(R.id.register_phone);
        registerSubmit = (Button) findViewById(R.id.register_submit);
        spinner = (Spinner) findViewById(R.id.spinner);



        setSupportActionBar(mToolBar);
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
                    arr_adapter= new ArrayAdapter<String>(RegistActivity.this, android.R.layout.simple_spinner_item, questionData);
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
            case R.id.register_submit:

                submit();
                break;
        }
    }

    private void submit() {
        String email = registerEmail.getText().toString();
        String name = registerName.getText().toString();
        String pwd = registerPwd.getText().toString();
        String answer = registerAnswer.getText().toString();
        String phone = registerPhone.getText().toString();

        RequestParams params = new RequestParams(Urls.URL_APP_REGISTER);
        params.addBodyParameter("userName",name);
        params.addBodyParameter("passWord",pwd);
        params.addBodyParameter("phoneNum",phone);
        params.addBodyParameter("email",email);
        params.addBodyParameter("passId",currentQuestion.getId()+"");
        params.addBodyParameter("passAnswers",answer);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                LoginResultBean resultBean = gson.fromJson(result, LoginResultBean.class);
                if (resultBean.getStatus() == 200) {
                    ToastAlone.showShortToast("注册成功");
                    Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
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














}
