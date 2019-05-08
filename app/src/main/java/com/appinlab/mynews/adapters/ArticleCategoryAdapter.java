package com.appinlab.mynews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appinlab.mynews.R;
import com.appinlab.mynews.models.ArticleCategory;

import java.util.List;

import butterknife.ButterKnife;

public class ArticleCategoryAdapter extends RecyclerView.Adapter<ArticleCategoryAdapter.ArticleCategoryItemViewHolder> {

    private List<ArticleCategory> mArticleCategoryList;

    public ArticleCategoryAdapter(List<ArticleCategory> articleCategoryList) {
        mArticleCategoryList = articleCategoryList;
    }

    @NonNull
    @Override
    public ArticleCategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_category_item_view,
                viewGroup, false);
        return new ArticleCategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleCategoryItemViewHolder articleCategoryItemViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mArticleCategoryList.size();
    }

    class ArticleCategoryItemViewHolder extends RecyclerView.ViewHolder {

        ArticleCategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
