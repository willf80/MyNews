package com.appinlab.mynews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appinlab.mynews.R;
import com.appinlab.mynews.models.Article;

import java.util.List;

import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleItemViewHolder> {

    private List<Article> mArticleList;

    public ArticleAdapter(List<Article> articleList) {
        mArticleList = articleList;
    }

    @NonNull
    @Override
    public ArticleItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item_view,
                viewGroup, false);
        return new ArticleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleItemViewHolder articleItemViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    class ArticleItemViewHolder extends RecyclerView.ViewHolder {

        ArticleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
