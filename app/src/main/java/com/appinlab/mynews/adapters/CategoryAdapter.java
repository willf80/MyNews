package com.appinlab.mynews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.appinlab.mynews.R;
import com.appinlab.mynews.models.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ArticleCategoryItemViewHolder> {

    private List<Category> mCategoryList;

    private OnCategoryDispatchListener mOnCategoryDispatchListener;

    public interface OnCategoryDispatchListener {
        void onCategoryStateChanged(Category category);
    }

    public CategoryAdapter(List<Category> categoryList) {
        mCategoryList = categoryList;
    }

    public CategoryAdapter(List<Category> categoryList, OnCategoryDispatchListener onCategoryDispatchListener) {
        mCategoryList = categoryList;
        mOnCategoryDispatchListener = onCategoryDispatchListener;
    }

    public List<Category> getCheckedCategories() {
        List<Category> categoryList = new ArrayList<>();

        for (Category category : mCategoryList) {
            if (!category.isChecked()) continue;
            categoryList.add(category);
        }

        return categoryList;
    }

    public void setCategoryListChecked(List<Category> checkedCategoryList) {
        for (Category category : mCategoryList) {
            for (Category checkedItem : checkedCategoryList) {
                if(!category.getLibelle().equalsIgnoreCase(checkedItem.getLibelle())) continue;

                category.setChecked(true);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleCategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item_view,
                viewGroup, false);
        return new ArticleCategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArticleCategoryItemViewHolder articleCategoryItemViewHolder, int i) {
        final Category category = mCategoryList.get(i);
        articleCategoryItemViewHolder.mCheckBox.setText(category.getLibelle());

        articleCategoryItemViewHolder.mCheckBox.setChecked(category.isChecked());

        articleCategoryItemViewHolder.mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            category.setChecked(isChecked);
            articleCategoryItemViewHolder.mCheckBox.setChecked(isChecked);

            if(mOnCategoryDispatchListener != null) {
                mOnCategoryDispatchListener.onCategoryStateChanged(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    class ArticleCategoryItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkBox)
        CheckBox mCheckBox;

        ArticleCategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
