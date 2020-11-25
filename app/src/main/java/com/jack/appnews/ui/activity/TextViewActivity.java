package com.jack.appnews.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jack.appnews.R;
import com.jack.appnews.ui.BaseActivity;
import com.paradoxie.autoscrolltextview.VerticalTextview;

public class TextViewActivity extends BaseActivity {
    @Override
    protected int inflateLayoutId() {
        return R.layout.news_detail_test;
    }

    @Override
    protected void initViews() {
        TextView title = (TextView) findViewById(R.id.text_title);
        TextView text_author = (TextView) findViewById(R.id.text_author);
        TextView text_time = (TextView) findViewById(R.id.text_time);
        LinearLayout scroller = (LinearLayout) findViewById(R.id.scroll);
        TextView content = new TextView(this);
        content.setTextColor(Color.BLACK);
        Bundle bundle = this.getIntent().getExtras();
        title.setText(bundle.getString("title"));
        text_author.setText(bundle.getString("text_author"));
        text_time.setText(bundle.getString("text_time"));
        content.setText(bundle.getString("content"));
        scroller.addView(content);

    }
}
