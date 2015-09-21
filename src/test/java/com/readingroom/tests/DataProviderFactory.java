package com.readingroom.tests;

import com.readingroom.enums.BookShelf;
import com.readingroom.models.User;
import com.readingroom.util.testdata.TestDataContainer;
import org.testng.annotations.DataProvider;

public class DataProviderFactory {

    @DataProvider(name = "WrongUsers")
    public static User[][] getWrongUsers() {
        return new User[][]{ {TestDataContainer.getUserByType("WrongUser")} };
    }

    @DataProvider(name = "BookShelves")
    public static Object[][] getBookShelves() {
        return new Object[][] {
                { TestDataContainer.getDefaultBook(), BookShelf.WANT_TO_READ },
                { TestDataContainer.getDefaultBook(), BookShelf.HAVE_READ },
                { TestDataContainer.getDefaultBook(), BookShelf.READING_NOW }
        };
    }
}