package com.readingroom.pages;

import com.readingroom.block.BookBl;
import com.readingroom.webdriver.ExtendedWebDriver;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;
import java.util.List;
import static com.thoughtworks.selenium.SeleneseTestBase.assertFalse;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

public class UserOverviewPage extends Page<UserOverviewPage> {

    private static final String PAGE_ROOT_NODE = "user-profile-overview";

    private List<BookBl> books;

    public UserOverviewPage(ExtendedWebDriver web) {
        super(web);
    }

    @Override
    public void init() {
    }

    @Override
    public void load() {
        web.waitUntilElementDisappear(getMemberRecommendationSpinnerLocator());
        web.waitUntilElementAppearVisible(getMemberRecommendationsLocator());
    }

    @Override
    public void unload() {
        web.waitUntilElementDisappear(getPageLocator());
    }

    @Override
    public void isLoaded() throws Error {
        assertFalse(web.isElementVisible(getMemberRecommendationSpinnerLocator()));
        assertTrue(web.isElementVisible(getMemberRecommendationsLocator()));
    }

    @Step("Open book {0} overview page")
    public BookOverviewPage openBookOverviewPage(String title) {
        getBookByTitle(title).click();
        return new BookOverviewPage(web);
    }

    public BookBl getBookByTitle(String title) {
        for (BookBl book: books)
            if (book.getTitle().equals(title))
                return book;
        throw new IllegalArgumentException("No book with following title: " + title);
    }

    private By getPageLocator() {
        return By.id(PAGE_ROOT_NODE);
    }

    private By getMemberRecommendationSpinnerLocator() {
        return By.cssSelector(".members-recommendations .spinner");
    }
    private By getMemberRecommendationsLocator() {
        return By.cssSelector("[ng-include*='memberRecommendations']");
    }
}