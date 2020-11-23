package com.jack.appnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jack.appnews.R;
import com.jack.appnews.bean.NewsBean;
import com.jack.appnews.util.GlideUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<NewsBean> items;
    private ClickListener clickListener;

    public NewsListRecycleAdapter(Context context, List<NewsBean> items) {
        this.context = context;
        this.items = items;
    }

    public interface ClickListener {
        void onItemClick(View v, int position, List<NewsBean> items);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        String type = String.valueOf(viewType);
        Log.i("type0",type);
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list_style_4, parent, false);
                viewHolder = new ViewHolderNone(view);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list_style_1, parent, false);
                viewHolder = new ViewHolderOne(view);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list_style_3, parent, false);
                viewHolder = new ViewHolderOne(view);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list_style_5, parent, false);
                viewHolder = new ViewHolderOne(view);
                break;
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list_style_2, parent, false);
                viewHolder = new ViewHolderMuti(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final XRecyclerView.ViewHolder holder, int position) {
        NewsBean newsBean = items.get(position);
        Log.i("newsbean",newsBean.getId());
        //String path = "file:///android_assert/";
        String type = String.valueOf(getItemViewType(position));
        Log.i("type",type);
        if(getItemViewType(position)==4) {
            ViewHolderMuti viewHolderMuti = (ViewHolderMuti) holder;
            viewHolderMuti.tvTitle.setText(newsBean.getTitle());
            viewHolderMuti.tvAuthor.setText(newsBean.getAuthor());
            viewHolderMuti.tvPublishTime.setText(newsBean.getTime());
            if (newsBean.getCovers() != null && newsBean.getCovers().size() == 4) {
                GlideUtil.load(context, R.drawable.tb09_1, viewHolderMuti.ivImage1);
                GlideUtil.load(context, R.drawable.tb09_2, viewHolderMuti.ivImage2);
                GlideUtil.load(context, R.drawable.tb09_3, viewHolderMuti.ivImage3);
                GlideUtil.load(context, R.drawable.tb09_4, viewHolderMuti.ivImage4);
            }
        }else if(getItemViewType(position)==0){
            ViewHolderNone viewHolderNone = (ViewHolderNone) holder;
            viewHolderNone.tvTitle.setText(newsBean.getTitle());
            viewHolderNone.tvAuthor.setText(newsBean.getAuthor());
            viewHolderNone.tvPublishTime.setText(newsBean.getTime());
        }else{
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            viewHolderOne.tvTitle.setText(newsBean.getTitle());
            viewHolderOne.tvAuthor.setText(newsBean.getAuthor());
            viewHolderOne.tvPublishTime.setText(newsBean.getTime());
            //加载图片
            Log.i("path1", newsBean.getCover());
            if ("tancheng.jpg".equals(newsBean.getCover())) {
                GlideUtil.load(context, R.mipmap.tancheng, viewHolderOne.ivImage);
                Log.i("signal", "true");
            }else if("event02.png".equals(newsBean.getCover())){
                GlideUtil.load(context, R.mipmap.event_02, viewHolderOne.ivImage);
            }else if("teamBuilding_04.png".equals(newsBean.getCover())){
                GlideUtil.load(context, R.mipmap.teambuilding_04, viewHolderOne.ivImage);
            }
            Log.i("signal1", "true");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                clickListener.onItemClick(holder.itemView, pos, items);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //1张图
    public static class ViewHolderNone extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvPublishTime;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolderNone(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public static class ViewHolderOne extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvPublishTime;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.iv_main_img)
        ImageView ivImage;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolderOne(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    //多张图
    public static class ViewHolderMuti extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_time)
        TextView tvPublishTime;
        @BindView(R.id.iv_main_img1)
        ImageView ivImage1;
        @BindView(R.id.iv_main_img2)
        ImageView ivImage2;
        @BindView(R.id.iv_main_img3)
        ImageView ivImage3;
        @BindView(R.id.iv_main_img4)
        ImageView ivImage4;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        public ViewHolderMuti(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}