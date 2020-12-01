package com.jack.appnews.ui.activity;

import android.content.SharedPreferences;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jack.appnews.R;

import com.jack.appnews.api.Api;
import com.jack.appnews.api.LoginCallBack;
import com.jack.appnews.bean.LoginResponse;
import com.jack.appnews.ui.BaseActivity;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.jack.appnews.Constants.BASE_URL;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.usrInput)
    EditText usrName;
    @BindView(R.id.passInput)
    EditText passWord;
    @BindView(R.id.btn_signIn)
    Button btnLogin;
    @BindView(R.id.btn_signUp)
    Button btnRegister;

    @Override
    protected void initViews() {
        usrName = (EditText) findViewById(R.id.usrInput);
        passWord = (EditText) findViewById(R.id.passInput);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String account = usrName.getText().toString().trim();
        String pwd = passWord.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_signIn:
//                showToast(account + " " + pwd);
                login(account, pwd);//异步post请求
//                loginGet(account, pwd);//get请求（弃用）
                break;
            case R.id.btn_signUp:
                register(account, pwd);
                break;
            default:
                break;
        }
    }
    private void login(String account, String pwd){
        if(isEmpty(account)){
//            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            showToast("请输入账号");
            return;
        }
        if(isEmpty(pwd)){
//            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            showToast("请输入密码");
            return;
        }
        HashMap<String, Object> m = new HashMap<>();
//        m.put("name", account);//自己的springboot后台，目前开发数据库登录功能，待开发注册功能
//        m.put("password", pwd);
        m.put("username", account);//字节后台，用户名密码可以随意填写，接口不会进行验证，没有注册功能
        m.put("password", pwd);
        Api api_login = new Api("/login", m);
        api_login.postRequest(new LoginCallBack() {
            @Override
            public void onSuccess(final String res) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(res);
//                    }
//                });
//                showToastAsync(res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if(loginResponse.getCode() == 0){
//                    FLAG = 1;
                    String token = loginResponse.getToken();
                    SharedPreferences sp = getSharedPreferences("sp_login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token", token);
                    editor.commit();
                    LoginActivity.this.finish();
                    showToastAsync("登录成功");
                }else{
                    showToastAsync("登录失败");
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("onFailure", e.getMessage());
            }
        });
    }

    public static boolean isEmpty(String str){
        if(str == null || str.length() <= 0){
            return true;
        }else{
            return false;
        }
    }

    private void loginGet(String account, String pwd){
        final OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(BASE_URL + "/user/login?" + "name=" + account + "&password=" + pwd)
                .build();
        //开启一个异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        //请求失败
                    }

                    //获得到接口数据
//                    System.out.println(responseBody.string());
                    final String result = responseBody.string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(result);
                        }
                    });
                }
            }
        });


    }

    private void register(String account, String pwd){
        if(isEmpty(account)){
            showToast("请输入账号");
            return;
        }
        if(isEmpty(pwd)){
            showToast("请输入密码");
            return;
        }
    }

    @Override
    protected int inflateLayoutId() {
        return R.layout.activity_login;
    }
}