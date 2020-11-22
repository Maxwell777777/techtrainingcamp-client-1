package com.jack.appnews.api;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
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
}
