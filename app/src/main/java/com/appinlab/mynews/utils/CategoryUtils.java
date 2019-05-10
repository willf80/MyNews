package com.appinlab.mynews.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CategoryUtils {

    private static String [] sCategoryList = new String[]{
            "Arts", "Business", "Entrepreneurs", "Politics", "Sports", "Travel"
    };

    public static String getTopStoriesCategoryName() {
        return "Top stories";
    }

    public static String getMostPopularCategoryName() {
        return "Most popular";
    }

    public static String[] getCategoryList() {
        return sCategoryList;
    }

    public static String[] getAllCategoryList() {

        String [] specialCategories = new String[]{getTopStoriesCategoryName(), getMostPopularCategoryName()};

        List<String> mergeList = new ArrayList<>();

        //Merge array list
        Collections.addAll(mergeList, specialCategories);
        Collections.addAll(mergeList, getCategoryList());

        //Create the final category array merged
        String [] totalList = new String[mergeList.size()];

        return mergeList.toArray(totalList);
    }
}
