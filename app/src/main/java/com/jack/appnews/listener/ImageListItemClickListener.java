package com.jack.appnews.listener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jack.appnews.adapter.ImageListRecycleAdapter;
import com.jack.appnews.api.Api;
import com.jack.appnews.api.LoginCallBack;
import com.jack.appnews.bean.ItemImageBean;
import com.jack.appnews.ui.activity.LoginActivity;
import com.jack.appnews.ui.activity.PhotoViewActivity;

import java.util.HashMap;
import java.util.List;


public class ImageListItemClickListener implements ImageListRecycleAdapter.ClickListener {
    private Context mContext;
//    private int flag = 0;

    public ImageListItemClickListener(Context context) {
        mContext = context;
    }

    @Override
    public void onItemClick(View v, final int position, final List<ItemImageBean> items) {
        try{

            SharedPreferences sp = mContext.getSharedPreferences("sp_login", Context.MODE_PRIVATE);
            String token = sp.getString("token", "null");
            HashMap<String, Object> m = new HashMap<>();
            m.put("Authorization", "Bearer " + token);
            //根据position选择id
            String id = "bytetalk_01";
//            Log.i("token111", token);

            Api api_content = new Api("/article/" + id, m);
            api_content.getRequest(new LoginCallBack() {
                @Override
                public void onSuccess(final String res) {
                    if(res.indexOf("Unauthorized") != -1){
                        Intent intent_login = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(intent_login);
                    }else{
                        //跳转到自定义TextView
//                        Intent intent = new Intent(mContext, TextViewActivity.class);
//                        Log.i("res111", res);
//                        Gson gson = new Gson();
//                        TextResponse textResponse = gson.fromJson(res, TextResponse.class);
//                        String text = textResponse.getData();
//                        Bundle bundle = new Bundle();
//                        //根据id解析json获取title等
//                        bundle.putString("title", "绝对坦率：打造反馈文化");
//                        bundle.putString("detail", "bytedance" + "\t" + "2020年7月7日");
//                        bundle.putString("content", text);
//                        intent.putExtras(bundle);
//                        Log.i("hiiii", res);
//                        mContext.startActivity(intent);

                        //跳转到图片显示activity
                        Intent intent = new Intent(mContext, PhotoViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dataBean", items.get(position));
                        intent.putExtras(bundle);
                        intent.putExtra("curPos", 0);

                        mContext.startActivity(intent);
                    }
                }
                @Override
                public void onFailure(Exception e) {
                    Log.i("fuck", e.getMessage());
                }
            }, true);


        }catch (Exception e){
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        }


    }
}