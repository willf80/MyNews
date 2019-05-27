package com.appinlab.mynews.adapters;

import com.appinlab.mynews.models.Image;
import com.appinlab.mynews.models.TopStoryArticle;
import com.appinlab.mynews.utils.DateUtils;

import java.util.List;

public class TopStoryArticleAdapter extends AbstractArticleAdapter<TopStoryArticle> {

    public TopStoryArticleAdapter(List<TopStoryArticle> articleList, OnDispatchListener<TopStoryArticle> articleOnDispatchListener) {
        super(articleList, articleOnDispatchListener);
    }

    @Override
    void bind(AbstractArticleAdapter.ArticleItemViewHolder articleItemViewHolder, TopStoryArticle article) {
        Image image = getArticleImage(article.getImageList());
        // Show current article image
        showImage(articleItemViewHolder.mContext, image, articleItemViewHolder.mImageView);

        articleItemViewHolder.mTitleTextView.setText(article.getTitle());
        articleItemViewHolder.mSectionTitleTextView.setText(doBreadCrumb(article.getSection(), article.getSubsection()));

        String publishedDate = DateUtils.stringDateFormatted(article.getPublishedDate(), "dd/MM/yy");
        articleItemViewHolder.mDateTextView.setText(publishedDate);
    }

}
