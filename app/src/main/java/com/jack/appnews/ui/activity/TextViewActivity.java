package com.jack.appnews.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jack.appnews.R;
import com.jack.appnews.ui.BaseActivity;
import com.zzhoujay.richtext.RichText;
import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//        content.setText(bundle.getString("content"));
//        Log.i("markdown", bundle.getString("content"));

//        String path = null;
//        InputStream abpath = getClass().getResourceAsStream("/assets/teambuilding_04.png");
//        try {
//            path = new String(InputStreamToByte(abpath));
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        //缓存
//        File file = getAlbumStorageDir(this, "album");
//        RichText.initCacheDir(file);
        String regex = "(\\(([^)]*)\\))";
        Pattern pattern = Pattern.compile(regex);
        String contentString = bundle.getString("content");
        Matcher matcher = pattern.matcher(contentString);
        while(matcher.find()){
            int temp = 0;
            Log.i("regex" + temp, matcher.group(1));
            String regexString = matcher.group(1);
            try{
                String[] ss = regexString.split("_");
                String replaceText = "(file:///android_asset/" + ss[0].substring(1) + ss[1] + "\n";
                replaceText = replaceText.toLowerCase();
                //            String replaceText2 = "file:///android_asset/teambuilding04.png";
                contentString = replaceAll(contentString, regexString, replaceText);
            }catch (Exception e){
                String replaceText = "(file:///android_asset/" + matcher.group(1).substring(1);
                contentString = replaceAll(contentString, regexString, replaceText);
            }


        }
        Log.i("markdown", contentString);
//        String testString = "hi maxwell， 欢迎阅读\n\n- 时间：2020 年 7 月 30 日（周四） 10:30-12:30 \n- 直播地址：xxxxxxxx\n- 语言：英文，有中文同传\n    \n    我们提倡坦诚清晰，那反馈到底怎么说，能既让对方重视，又不会引起敌意呢？ \n    收到他人反馈难免有些本能的防御，怎样能训练出谦逊开放的心态，真正地把反馈作为成长的助力？\n    曾在苹果和谷歌担任高管的金·斯科特（Kim Scott） 基于自己多年的职业经验，提出了“绝对坦率”的理论框架——只有同时做到“个体关怀”，并敢于“直接挑战”，才能让沟通更高效，团队关系更融洽。\n    本期 ByteTalk，Kim 将结合案例解读“绝对坦率”的概念与价值，并教大家一些简单易上手的表达技巧。\n    \n ## 演讲嘉宾\n    \n![](tancheng.jpg)\n    金·斯科特，《绝对坦率》作者，Candor 有限公司创始人。\n    曾领导 AdSense、YouTube 及谷歌 Doubleclick 在线广告销售及运营部门，后加入苹果公司，从事领导力交流会的开发及教授，并在硅谷多家科技公司担任 CEO 教练。";
//        RichText.fromMarkdown(contentString).into(content);
        contentString = contentString.replaceAll(" ", "");
        RichText.fromMarkdown(contentString).into(content);

        scroller.addView(content);

    }

    private String replaceAll(String parent, String targetEle, String replaceEle){
        if(parent.indexOf(targetEle) == -1){
            return parent;
        }else{
            int beginIndex = parent.indexOf(targetEle);
            int endIndex = beginIndex + targetEle.length();
            return parent.substring(0, beginIndex) + replaceEle + parent.substring(endIndex);
        }
    }
    public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.i("LOG_TAG", "Directory not created");
        }
        return file;
    }

}
