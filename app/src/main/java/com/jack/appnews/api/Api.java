package com.jack.appnews.api;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.jack.appnews.Constants.ZI_JIE_URL;

public class Api {
    private OkHttpClient client;
    private String requestUrl;
    private HashMap<String, Object> mParams;
//    public static Api api = new Api();
    public Api(){

    }
    public Api(String url, HashMap<String, Object> params){
        client = new OkHttpClient.Builder().build();
        requestUrl = ZI_JIE_URL + url;
        mParams = params;
    }
    public void postRequest(final LoginCallBack callBack){
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json"), jsonStr);
        //创建Request
        Request request = new Request.Builder()
                .url(requestUrl)
//                .addHeader("contentType", "application/json;charset=UTF-8")
                .post(requestBodyJson)
                .build();
        //创建call回调对象
        final Call call = client.newCall(request);
        //发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                callBack.onSuccess(result);
            }
        });
    }

    public void getRequest(final LoginCallBack callBack, boolean markdown){
        //添加header信息
        Headers.Builder builder_header = new Headers.Builder();
        for (String key : mParams.keySet()) {
            builder_header.add(key, (String) Objects.requireNonNull(mParams.get(key)));
        }
        Headers headers = builder_header.build();

        HttpUrl.Builder httpBuilder = HttpUrl.parse(requestUrl).newBuilder();
        if(markdown == true){
            httpBuilder.addQueryParameter("markdown", "true");
        }else{
            httpBuilder.addQueryParameter("markdown", "false");
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(httpBuilder.build()).headers(headers).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callBack.onFailure(e);
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                callBack.onSuccess(result);
            }
        });
    }

}
