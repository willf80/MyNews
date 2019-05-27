package com.appinlab.mynews.adapters;

import com.appinlab.mynews.models.Article;
import com.appinlab.mynews.models.Image;
import com.appinlab.mynews.utils.DateUtils;

import java.util.List;

public class ArticleAdapter extends AbstractArticleAdapter<Article> {

    public ArticleAdapter(List<Article> articleList, OnDispatchListener<Article> articleOnDispatchListener) {
        super(articleList, articleOnDispatchListener);
    }

    @Override
    void bind(AbstractArticleAdapter.ArticleItemViewHolder articleItemViewHolder, Article article) {
        Image image = getArticleImage(article.getImageList());
        // Show current article image
        showImage(articleItemViewHolder.mContext, image, articleItemViewHolder.mImageView);

        articleItemViewHolder.mTitleTextView.setText(article.getTitle());
        articleItemViewHolder.mSectionTitleTextView.setText(doBreadCrumb(article.getSection(), article.getSubsection()));

        String publishedDate = DateUtils.stringDateFormatted(article.getPublishedDate(), "dd/MM/yy");
        articleItemViewHolder.mDateTextView.setText(publishedDate);
    }

}
