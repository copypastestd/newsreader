package com.perfilyev.newsreader.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.perfilyev.newsreader.R;
import com.perfilyev.newsreader.models.Article;
import com.perfilyev.newsreader.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * RecyclerView будет обращаться к этому адаптеру, когда ViewHolder должен быть создан или связан
 * с объектом Article.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private List<Article> articles;

    public NewsAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater
                .inflate(R.layout.list_item_newsfeed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.bindArticle(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    /**
     * Класс, который содержит в себе единственный View.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.list_item_title_textview)
        TextView titleTv;
        @Bind(R.id.list_item_date_textview)
        TextView dateTv;
        @Bind(R.id.list_item_thumbnail_imageview)
        ImageView thumbnailIv;

        private Article article;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        /**
         * Метод, служащий для обновления полей во ViewHolder.
         */
        public void bindArticle(Article article) {
            this.article = article;
            titleTv.setText(article.getTitle());
            dateTv.setText(article.getDate());
            Picasso.with(thumbnailIv.getContext()).load(article.getImage()).fit().centerCrop().into(thumbnailIv);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = DetailActivity.newIntent(context, article);
            context.startActivity(intent);
        }
    }
}
