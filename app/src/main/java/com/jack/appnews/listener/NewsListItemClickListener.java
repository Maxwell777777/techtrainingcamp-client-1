package com.jack.appnews.listener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jack.appnews.adapter.NewsListRecycleAdapter;
import com.jack.appnews.api.Api;
import com.jack.appnews.api.LoginCallBack;
import com.jack.appnews.bean.NewsBean;
import com.jack.appnews.bean.NewsListBean;
import com.jack.appnews.entity.TextResponse;
import com.jack.appnews.ui.activity.LoginActivity;
import com.jack.appnews.ui.activity.NewsDetailActivity;
import com.jack.appnews.ui.activity.TextViewActivity;

import java.util.HashMap;
import java.util.List;

/**
 * 新闻列表
 * 点击  监听
 */
public class NewsListItemClickListener implements NewsListRecycleAdapter.ClickListener {
    private Context mContext;

    public NewsListItemClickListener(Context context) {
        mContext = context;
    }

    @Override
    public void onItemClick(View v, int position, List<NewsBean> items) {
        try{

            SharedPreferences sp = mContext.getSharedPreferences("sp_login", Context.MODE_PRIVATE);
            String token = sp.getString("token", "null");
            HashMap<String, Object> m = new HashMap<>();
            m.put("Authorization", "Bearer " + token);
            //根据position选择id
            final int news_id = position % 5;
            String id = "";
            switch (news_id){
                case 1:
                    id = "bytetalk_01";
                    break;
                case 2:
                    id = "event_01";
                    break;
                case 3:
                    id = "event_02";
                    break;
                case 4:
                    id = "teamBuilding_04";
                    break;
                case 0:
                    id = "teamBuilding_09";
                    break;
                default:
                    Log.i("news_id", news_id + "");
            }
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
                        Intent intent = new Intent(mContext, TextViewActivity.class);
                        Log.i("res111", res);
                        Gson gson = new Gson();
                        TextResponse textResponse = gson.fromJson(res, TextResponse.class);
                        String text = textResponse.getData();
                        Bundle bundle = new Bundle();
                        //根据id解析json获取title等
                        switch (news_id){
                            case 1:
                                bundle.putString("title", "绝对坦率：打造反馈文化");
                                bundle.putString("text_time", "2020年7月7日");
                                bundle.putString("text_author","bytedance");
                                break;
                            case 2:
                                bundle.putString("title", "2020字节跳动全球员工摄影大赛邀请函");
                                bundle.putString("text_time", "2020年10月7日");
                                bundle.putString("text_author","bytedance");
                                break;
                            case 3:
                                bundle.putString("title", "Lark·巡洋计划开发者大赛圆满结束");
                                bundle.putString("text_time", "2019年10月7日");
                                bundle.putString("text_author","bytedance");
                                break;
                            case 4:
                                bundle.putString("title", "4-12 虹桥天地，蹦起来吧！");
                                bundle.putString("text_time",  "2019年4月11日");
                                bundle.putString("text_author","vc_team");
                                break;
                            case 0:
                                bundle.putString("title", "9月18日淀山湖户外团建");
                                bundle.putString("text_time", "2020年9月7日");
                                bundle.putString("text_author","vc mobile team");
                                break;
                            default:
                                Log.i("news_id", news_id + "");
                        }
                        bundle.putString("content", text);
                        intent.putExtras(bundle);
                        Log.i("hiiii", res);
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