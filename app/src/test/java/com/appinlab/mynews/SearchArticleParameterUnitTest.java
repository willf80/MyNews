package com.appinlab.mynews;

import com.appinlab.mynews.models.Category;
import com.appinlab.mynews.models.SearchArticleParameter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class SearchArticleParameterUnitTest {

    @Test
    public void isValidForSearch_whenQueryIsNull_returnFalse() {

        // Arrange
        boolean expected = false;
        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();

        // Act
        boolean result = searchArticleParameter.isValidForSearch();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void isValidForSearch_whenQueryIsEmpty_returnFalse() {

        // Arrange
        boolean expected = false;
        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();
        searchArticleParameter.setQuery("");

        // Act
        boolean result = searchArticleParameter.isValidForSearch();

        // Assert
        assertEquals(expected, result);
    }

//    @Test
//    public void isValidForSearch_whenQueryIsNotNullAndNotEmpty_returnTrue() {
//
//        // Arrange
//        boolean expected = true;
//        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();
//        searchArticleParameter.setQuery("Game of thrones");
//
//        // Act
//        boolean result = searchArticleParameter.isValidForSearch();
//
//        // Assert
//        assertEquals(expected, result);
//    }

    @Test
    public void isValidForSearch_whenQueryIsValidAndCategoryListIsEmpty_returnFalse() {

        // Arrange
        boolean expected = false;
        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();
        searchArticleParameter.setQuery("Game of thrones");

        // Act
        boolean result = searchArticleParameter.isValidForSearch();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void isValidForSearch_whenQueryIsValidAndCategoryListIsNotEmpty_returnTrue() {

        // Arrange
        boolean expected = true;
        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();
        searchArticleParameter.setQuery("Game of thrones");

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Art"));
        searchArticleParameter.setCategoryList(categoryList);

        // Act
        boolean result = searchArticleParameter.isValidForSearch();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void isValidForSearch_whenQueryIsValid_and_categoryListIsNotEmpty_and_startDateGreaterThanEndDate_returnFalse() {

        // Arrange
        boolean expected = false;
        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();
        searchArticleParameter.setQuery("Game of thrones");

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Art"));
        searchArticleParameter.setCategoryList(categoryList);

        Calendar start = Calendar.getInstance();
        start.add(Calendar.DAY_OF_MONTH, 1);//Current day + 1

        Calendar end = Calendar.getInstance();

        // startDate > endDate
        searchArticleParameter.setStartDate(start.getTime());
        searchArticleParameter.setEndDate(end.getTime());

        // Act
        boolean result = searchArticleParameter.isValidForSearch();

        // Assert
        assertEquals(expected, result);
    }


    ///--------------------------------------------------------///
    ///-- isValidForNotification_whenQueryIsNull_returnFalse --///
    ///--------------------------------------------------------///
    @Test
    public void isValidForNotification_whenQueryIsNull_returnFalse() {

        // Arrange
        boolean expected = false;
        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();

        // Act
        boolean result = searchArticleParameter.isValidForNotification();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void isValidForNotification_whenQueryIsEmpty_returnFalse() {

        // Arrange
        boolean expected = false;
        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();
        searchArticleParameter.setQuery("");

        // Act
        boolean result = searchArticleParameter.isValidForNotification();

        // Assert
        assertEquals(expected, result);
    }

//    @Test
//    public void isValidForNotification_whenQueryIsNotNullAndNotEmpty_returnTrue() {
//
//        // Arrange
//        boolean expected = true;
//        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();
//        searchArticleParameter.setQuery("Game of thrones");
//
//        // Act
//        boolean result = searchArticleParameter.isValidForNotification();
//
//        // Assert
//        assertEquals(expected, result);
//    }

    @Test
    public void isValidForNotification_whenQueryIsValidAndCategoryListIsEmpty_returnFalse() {

        // Arrange
        boolean expected = false;
        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();
        searchArticleParameter.setQuery("Game of thrones");

        // Act
        boolean result = searchArticleParameter.isValidForNotification();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void isValidForNotification_whenQueryIsValidAndCategoryListIsNotEmpty_returnTrue() {

        // Arrange
        boolean expected = true;
        SearchArticleParameter searchArticleParameter = new SearchArticleParameter();
        searchArticleParameter.setQuery("Game of thrones");

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Art"));
        searchArticleParameter.setCategoryList(categoryList);

        // Act
        boolean result = searchArticleParameter.isValidForNotification();

        // Assert
        assertEquals(expected, result);
    }

}