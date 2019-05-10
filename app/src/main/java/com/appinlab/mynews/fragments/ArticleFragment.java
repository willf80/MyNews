package com.appinlab.mynews.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appinlab.mynews.R;
import com.appinlab.mynews.adapters.ArticleAdapter;
import com.appinlab.mynews.models.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleFragment extends Fragment {
    private static final String ARG_CATEGORIE_NAME = "categoryName";

    private String mCategoryName;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    ArticleAdapter mArticleAdapter;


    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance(String category) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORIE_NAME, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryName = getArguments().getString(ARG_CATEGORIE_NAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<Article> articleList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            articleList.add(new Article());
        }

        mArticleAdapter = new ArticleAdapter(articleList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mArticleAdapter);
    }
}
