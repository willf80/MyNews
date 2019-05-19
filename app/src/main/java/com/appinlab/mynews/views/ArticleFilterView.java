package com.appinlab.mynews.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appinlab.mynews.R;
import com.appinlab.mynews.adapters.CategoryAdapter;
import com.appinlab.mynews.models.Category;
import com.appinlab.mynews.models.SearchArticleParameter;
import com.appinlab.mynews.utils.CategoryUtils;
import com.appinlab.mynews.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ArticleFilterView extends LinearLayout {
    final String datePattern = "dd/MM/yyyy";

    private boolean mShowDate = false;
    private List<Category> mArticleCategories;
    private CategoryAdapter mCategoryAdapter;

    private LinearLayout mDateFilterLayout;
    private TextView mStartDateTextView;
    private TextView mEndDateTextView;
    private EditText mQueryEditText;

    public ArticleFilterView(Context context) {
        super(context);
        init(null, 0);
    }

    public ArticleFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ArticleFilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void loadCategoryList() {
        String[] categorieStringList = CategoryUtils.getCategoryList();
        mArticleCategories = new ArrayList<>();
        for (String s : categorieStringList) {
            Category category = new Category(s);
            mArticleCategories.add(category);
        }
    }

    private void init(AttributeSet attrs, int defStyle) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.article_filter_view, this);
        RecyclerView recyclerView = view.findViewById(R.id.articleCategoryRecyclerView);

        mDateFilterLayout = view.findViewById(R.id.dateFilterLayout);
        mStartDateTextView = view.findViewById(R.id.startDateTextView);
        mEndDateTextView = view.findViewById(R.id.endDateTextView);
        mQueryEditText = view.findViewById(R.id.queryEditText);

        // Load category list
        loadCategoryList();

        mCategoryAdapter = new CategoryAdapter(mArticleCategories);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(mCategoryAdapter);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ArticleFilterView, defStyle, 0);

        mShowDate = a.getBoolean(R.styleable.ArticleFilterView_showDate, false);
        handleDate();

        // Handle events
        handleEvents();

        a.recycle();
    }

    private void handleEvents() {
        mStartDateTextView.setOnClickListener(view -> showCalendarForStartDate());
        mEndDateTextView.setOnClickListener( view -> showCalendarForEndDate());
    }

    private void showCalendarForStartDate() {
        Calendar now = Calendar.getInstance();
        new DatePickerDialog(getContext(),
            (view, year, month, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(year, month, dayOfMonth);

                mStartDateTextView.setText(DateUtils.parseDateToString(calendar.getTime(), datePattern));
            },
            now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void showCalendarForEndDate() {
        Calendar now = Calendar.getInstance();
        new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.set(year, month, dayOfMonth);
                    mEndDateTextView.setText(DateUtils.parseDateToString(calendar.getTime(), datePattern));
                },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void handleDate() {
        if(mShowDate) {
            mDateFilterLayout.setVisibility(VISIBLE);
        } else {
            mDateFilterLayout.setVisibility(GONE);
        }
    }

    public boolean isShowDate() {
        return mShowDate;
    }

    public void setShowDate(boolean showDate) {
        mShowDate = showDate;
        handleDate();
    }

    public List<Category> getArticleCategories() {
        return mArticleCategories;
    }

    public void setArticleCategories(List<Category> articleCategories) {
        mArticleCategories = articleCategories;
        mCategoryAdapter.notifyDataSetChanged();
    }

    /**
     * Get search parameters
     * @return SearchArticleParameter
     */
    public SearchArticleParameter getSearchArticleParameters() {
        SearchArticleParameter articleParameter = new SearchArticleParameter();

        String startDateString = mStartDateTextView.getText().toString();
        String endDateString = mEndDateTextView.getText().toString();

        if(!startDateString.isEmpty()) {
            articleParameter.setStartDate(
                    DateUtils.parseStringToDate(mStartDateTextView.getText().toString(), datePattern));
        }

        if(!endDateString.isEmpty()) {
            articleParameter.setEndDate(
                    DateUtils.parseStringToDate(mEndDateTextView.getText().toString(), datePattern));
        }

        articleParameter.setQuery(mQueryEditText.getText().toString());
        articleParameter.setCategoryList(mCategoryAdapter.getCheckedCategories());

        return articleParameter;
    }
}
