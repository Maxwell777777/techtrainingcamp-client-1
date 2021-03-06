package com.jack.appnews.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(inflateLayoutId());

        ButterKnife.bind(this);

        context = this;
        prepare();
        initViews();
    }

    protected abstract int inflateLayoutId();

    /**
     * 初始化布局前的准备工作
     */
    protected void prepare(){}

    protected void initViews(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showToast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToastAsync(String msg){
        Looper.prepare();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}