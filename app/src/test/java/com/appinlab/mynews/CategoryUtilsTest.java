package com.appinlab.mynews;

import com.appinlab.mynews.utils.CategoryUtils;

import org.junit.Assert;
import org.junit.Test;

public class CategoryUtilsTest {

    @Test
    public void topStoriesCategory_IsNotNull() {
        //Arrange
        String expected = "Top stories";

        //Act
        String topStoriesCategoryName = CategoryUtils.getTopStoriesCategoryName();

        //Assert
        Assert.assertNotNull(topStoriesCategoryName);
        Assert.assertEquals(expected, topStoriesCategoryName);
    }

    @Test
    public void mostPopularCategory_IsNotNull() {
        //Arrange
        String expected = "Most popular";

        //Act
        String mostPopularCategoryName = CategoryUtils.getMostPopularCategoryName();

        //Assert
        Assert.assertNotNull(mostPopularCategoryName);
        Assert.assertEquals(expected, mostPopularCategoryName);
    }

    @Test
    public void categoryList_CheckIf_IsNotEmpty_And_LengthIs_6() {
        //Arrange
        int expected = 6;

        //Act
        String [] categoryList = CategoryUtils.getCategoryList();

        //Assert
        Assert.assertEquals(expected, categoryList.length);
    }

    @Test
    public void allCategoryList_CheckIf_Length() {
        //Arrange
        int expectedLength = 8;
        String categoryNameOfIndex3 = "Business";

        //Act
        String [] categoryList = CategoryUtils.getAllCategoryList();

        //Assert
        Assert.assertEquals(expectedLength, categoryList.length);
        Assert.assertEquals(categoryNameOfIndex3, categoryList[3]);
    }
}
