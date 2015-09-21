package com.readingroom.factorycomponents;

import com.readingroom.pages.BookshelfPage;
import com.readingroom.pages.HomePage;
import com.readingroom.pages.UserOverviewPage;
import com.readingroom.webdriver.ExtendedWebDriver;

public class PageCreator extends MyPageFactory {
    public PageCreator(ExtendedWebDriver webDriver) {
        super(webDriver);
    }

    public HomePage getHomePage() {
        return getPage(HomePage.class);
    }

    public UserOverviewPage getUserOverviewPage() {
        return getPage(UserOverviewPage.class);
    }

    public BookshelfPage getBookShelfPage() {
        return getPage(BookshelfPage.class);
    }
}