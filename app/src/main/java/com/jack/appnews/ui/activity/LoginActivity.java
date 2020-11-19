package com.jack.appnews.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.appnews.R;

import com.jack.appnews.ui.BaseActivity;
import com.jack.appnews.util.SpUtil;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.usrInput)
    EditText usrName;
    @BindView(R.id.passInput)
    EditText passWord;
    @BindView(R.id.btn_signIn)
    Button btnLogin;
    @BindView(R.id.btn_signUp)
    Button btnRegister;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        usrName = (EditText) findViewById(R.id.usrInput);
//        passWord = (EditText) findViewById(R.id.passInput);
//        btnLogin = (Button) findViewById(R.id.btn_signIn);
//        btnRegister = (Button) findViewById(R.id.btn_signUp);
//        btnLogin.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                String account = usrName.getText().toString().trim();
//                String pwd = passWord.getText().toString().trim();
//                login(account, pwd);
//            }
//        });
//    }

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
                login(account, pwd);
                break;
            case R.id.btn_signUp:
                register(account, pwd);
                break;
            default:
                break;
        }
    }

    private void login(String account, String pwd){
        if(SpUtil.isEmpty(account)){
//            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            showToast("请输入账号");
            return;
        }
        if(SpUtil.isEmpty(pwd)){
//            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            showToast("请输入密码");
            return;
        }
    }

    private void register(String account, String pwd){
        if(SpUtil.isEmpty(account)){
            showToast("请输入账号");
            return;
        }
        if(SpUtil.isEmpty(pwd)){
            showToast("请输入密码");
            return;
        }
    }

    @Override
    protected int inflateLayoutId() {
        return R.layout.activity_login;
    }
}