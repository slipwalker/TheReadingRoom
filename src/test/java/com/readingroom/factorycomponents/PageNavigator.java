package com.readingroom.factorycomponents;

import com.readingroom.pages.BookshelfPage;
import com.readingroom.pages.HomePage;
import com.readingroom.pages.UserOverviewPage;
import com.readingroom.webdriver.ExtendedWebDriver;
import ru.yandex.qatools.allure.annotations.Step;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PageNavigator extends PageCreator {
    private final Map<Class, String> pageLinksMap = new HashMap<>();

    public PageNavigator(ExtendedWebDriver webDriver, URL baseUrl) {
        super(webDriver);
        initPageLinksMap(baseUrl);
    }

    private void initPageLinksMap(URL baseUrl) {
        pageLinksMap.put(HomePage.class, baseUrl.toString());
        pageLinksMap.put(UserOverviewPage.class, baseUrl.toString());
        pageLinksMap.put(BookshelfPage.class, baseUrl + "%s/bookshelf/#!/%s");
    }

    @Step("Open Home page")
    public HomePage getHomePage() {
        navigateTo(HomePage.class);
        return super.getHomePage();
    }

    @Step("Open User overview page")
    public UserOverviewPage getUserOverviewPage() {
        navigateTo(UserOverviewPage.class);
        return super.getUserOverviewPage();
    }

    @Step("Open All Books bookshelf")
    public BookshelfPage getAllBooksBookShelfPage(String userName) {
        return getBookShelfPage(userName, "all-books-0");
    }

    @Step("Open Want To Read bookshelf")
    public BookshelfPage getWantToReadBookShelfPage(String userName) {
        return getBookShelfPage(userName, "want-to-read-1");
    }

    @Step("Open Reading Now bookshelf")
    public BookshelfPage getReadingNowBookShelfPage(String userName) {
        return getBookShelfPage(userName, "reading-now-2");
    }

    @Step("Open Have Read bookshelf")
    public BookshelfPage getHaveReadBookShelfPage(String userName) {
        return getBookShelfPage(userName, "have-read-3");
    }

    private BookshelfPage getBookShelfPage(String userName, String bookShelfName) {
        navigateTo(BookshelfPage.class, userName, bookShelfName);
        return super.getBookShelfPage();
    }

    public void navigateTo(Class pageClassToProxy) {
        navigateTo(pageClassToProxy, "");
    }

    public void navigateTo(Class pageClassToProxy, String... urlParam) {
        String desiredUrl = String.format(pageLinksMap.get(pageClassToProxy), urlParam);
        if (!isItCurrentUrl(desiredUrl)) {
            webDriver.get(desiredUrl);
        }
    }

    public boolean isItCurrentUrl(String desiredUrl) {
        return webDriver.getCurrentUrl().equalsIgnoreCase(desiredUrl);
    }
}