package com.jack.appnews.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jack.appnews.adapter.ImageListRecycleAdapter;
import com.jack.appnews.bean.ItemImageBean;
import com.jack.appnews.ui.activity.LoginActivity;
import com.jack.appnews.ui.activity.PhotoViewActivity;

import java.util.List;

import static com.jack.appnews.Constants.FLAG;

public class ImageListItemClickListener implements ImageListRecycleAdapter.ClickListener {
    private Context mContext;
//    private int flag = 0;

    public ImageListItemClickListener(Context context) {
        mContext = context;
    }

    @Override
    public void onItemClick(View v, int position, List<ItemImageBean> items) {
        if(FLAG == 0){
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        }else{
            Intent intent = new Intent(mContext, PhotoViewActivity.class);//当前页面 跳转页面
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataBean", items.get(position));
            intent.putExtras(bundle);
            intent.putExtra("curPos", 0);

            mContext.startActivity(intent);
        }

    }
}