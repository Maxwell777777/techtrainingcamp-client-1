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
        String regex = "\\(([^)]*)\\)";
        Pattern pattern = Pattern.compile(regex);
        String contentString = bundle.getString("content");
        Matcher matcher = pattern.matcher(contentString);
        while(matcher.find()){
            int temp = 0;
            Log.i("regex" + temp, matcher.group(1));
            String regexString = matcher.group(1);
            try{
                String[] ss = regexString.split("_");
                String replaceText = "file:///android_asset/" + ss[0] + ss[1];
                replaceText = replaceText.toLowerCase();
                //            String replaceText2 = "file:///android_asset/teambuilding04.png";
                contentString = replaceAll(contentString, regexString, replaceText);
            }catch (Exception e){
                String replaceText = "file:///android_asset/" + matcher.group(1);
                contentString = replaceAll(contentString, regexString, replaceText);
            }


        }
        Log.i("markdown", contentString);
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
