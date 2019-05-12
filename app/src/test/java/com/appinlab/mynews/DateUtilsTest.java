package com.appinlab.mynews;

import com.appinlab.mynews.utils.CategoryUtils;
import com.appinlab.mynews.utils.DateUtils;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtilsTest {

    @Test(expected = NullPointerException.class)
    public void parseStringToDate_WhenDateStringIsNull_ThenNullPointerExceptionSatisfied() {
        //Arrange
        String dateString = null;

        //Act
        DateUtils.parseStringToDate(dateString);
    }

    @Test
    public void parseStringToDate_WhenDateStringIsEmpty_ThenReturnNullSatisfied() {
        // Arrange
        String dateString = "";

        // Act
        Date date = DateUtils.parseStringToDate(dateString);

        // Assert
        Assert.assertNull(date);
    }

    @Test
    public void parseStringToDate_WhenInvalidDate_ThenReturnNullSatisfied() {
        // Arrange
        String dateString = "20/10/2019";

        // Act
        Date date = DateUtils.parseStringToDate(dateString);

        // Assert
        Assert.assertNull(date);
    }

    @Test
    public void parseStringToDate_WhendDateIsValid_ThenReturnDateSatisfied() {
        // Arrange
        String dateString = "2019-05-10T21:31:15-04:00";
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        calendar.set(2019, 4, 10, 21, 31, 15);

        Date dateExpected = calendar.getTime();

        // Act
        Date date = DateUtils.parseStringToDate(dateString);

        // Assert
        Assert.assertNotNull(date);
        Assert.assertEquals(dateExpected.toString(), date.toString());
    }

    @Test
    public void stringDateFormatted_WhendDateIsValid_ThenReturnDateSatisfied() {
        // Arrange
        String dateString = "2019-05-10T21:31:15-04:00";
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        calendar.set(2019, 4, 10, 21, 31, 15);

        String dateExpected = "11/05/19";

        // Act
        String stringDateFormatted = DateUtils.stringDateFormatted(dateString, "dd/MM/yy");

        // Assert
        Assert.assertEquals(dateExpected, stringDateFormatted);
    }

}
