package com.appinlab.mynews.adapters;

import com.appinlab.mynews.models.Image;
import com.appinlab.mynews.models.Media;
import com.appinlab.mynews.models.MostPopularArticle;
import com.appinlab.mynews.utils.DateUtils;

import java.util.List;

public class MostPopularArticleAdapter extends AbstractArticleAdapter<MostPopularArticle> {

    public MostPopularArticleAdapter(List<MostPopularArticle> articleList,
                                     OnDispatchListener<MostPopularArticle> articleOnDispatchListener) {
        super(articleList, articleOnDispatchListener);
    }

    @Override
    void bind(AbstractArticleAdapter.ArticleItemViewHolder articleItemViewHolder, MostPopularArticle article) {
        List<Media> mediaList = article.getMediaList();
        List<Image> imageList = null;

        if(mediaList.size() > 0) {
            imageList = mediaList.get(0).getImageList();
        }

        if(imageList != null) {
            Image image = getArticleImage(imageList);
            // Show current article image
            showImage(articleItemViewHolder.mContext, image, articleItemViewHolder.mImageView);
        }

        articleItemViewHolder.mTitleTextView.setText(article.getTitle());
        articleItemViewHolder.mSectionTitleTextView.setText(doBreadCrumb(article.getSection(), article.getSubsection()));

        String publishedDate = DateUtils.stringDateFormattedWithPattern(
                article.getPublishedDate(), "yyyy-MM-dd", "dd/MM/yy");
        articleItemViewHolder.mDateTextView.setText(publishedDate);
    }
}
