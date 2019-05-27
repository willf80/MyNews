package com.appinlab.mynews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appinlab.mynews.ArticleWebViewActivity;
import com.appinlab.mynews.R;
import com.appinlab.mynews.models.Image;
import com.appinlab.mynews.models.Media;
import com.appinlab.mynews.models.TopStoryArticle;
import com.appinlab.mynews.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AbstractArticleAdapter<T> extends RecyclerView.Adapter<AbstractArticleAdapter.ArticleItemViewHolder> {

    private List<T> mArticleList;
    private OnDispatchListener<T> mOnDispatchListener;

    public interface OnDispatchListener<T> {
        void onItemClick(T article);
    }

    AbstractArticleAdapter(List<T> articleList, OnDispatchListener<T> onDispatchListener) {
        mArticleList = articleList;
        mOnDispatchListener = onDispatchListener;
    }

    public void setArticleList(List<T> articleList) {
        mArticleList = articleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbstractArticleAdapter.ArticleItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item_view,
                viewGroup, false);
        return new AbstractArticleAdapter.ArticleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AbstractArticleAdapter.ArticleItemViewHolder articleItemViewHolder, int i) {
        final T article = mArticleList.get(i);
        bind(articleItemViewHolder, article);

        articleItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDispatchListener.onItemClick(article);
            }
        });
    }

    void showImage(Context context, Image image, ImageView intoImageView) {
        // When image is null then return default image
        if(image == null || image.getUrl() == null) {
            image = new Image();
            image.setUrl("");
        }

        String imageUrl = image.getUrl();
        String prefix = "https://";
        if(!imageUrl.startsWith(prefix)) {
            imageUrl = context.getString(R.string.image_base_url) + imageUrl;
        }

        Picasso.with(context)
                .load(imageUrl)
                .centerCrop()
                .resize(75, 75)
                .placeholder(R.drawable.nytimes_placeholder)
                .error(R.drawable.nytimes_placeholder)
                .into(intoImageView);
    }

    // Get first image of article
    Image getArticleImage(List<Image> imageList) {
        Image image = null;

        if(imageList != null  && imageList.size() > 0) {
            image = imageList.get(0);
        }

        return image;
    }

    // Create breadcrumb with section and subsection
    String doBreadCrumb(String section, String subSection) {
        String breadcrumb = section;
        if(subSection != null && subSection.length() > 0){
            breadcrumb = String.format("%s > %s", section, subSection);
        }

        return  breadcrumb;
    }

    abstract void bind(AbstractArticleAdapter.ArticleItemViewHolder articleItemViewHolder, T article);

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public class ArticleItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        public ImageView mImageView;

        @BindView(R.id.sectionTitleTextView)
        public TextView mSectionTitleTextView;

        @BindView(R.id.dateTextView)
        public TextView mDateTextView;

        @BindView(R.id.titleTextView)
        public TextView mTitleTextView;

        Context mContext;

        ArticleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
        }
    }
}
