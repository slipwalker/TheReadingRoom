package com.readingroom.pages;

import com.readingroom.webdriver.ExtendedWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Image;
import java.util.List;
import static com.thoughtworks.selenium.SeleneseTestBase.assertFalse;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

public class BookshelfPage extends Page<BookshelfPage> {
    private final static String PAGE_ROOT_NODE = ".user-bookshelf-module";

    @FindBy(css = PAGE_ROOT_NODE + " img")
    private List<Image> bookCovers;

    public BookshelfPage(ExtendedWebDriver web) {
        super(web);
    }

    @Override
    public void init() {
    }

    @Override
    public void load() {
        web.waitUntilElementAppearVisible(getPageLocator());
        web.waitUntilElementDisappear(getLoadingLocator());
    }

    @Override
    public void unload() {
        web.waitUntilElementDisappear(getPageLocator());
    }

    @Override
    public void isLoaded() throws Error {
        assertTrue(web.isElementVisible(getPageLocator()));
        assertFalse(web.isElementVisible(getLoadingLocator()));
    }

    public int getBookCoversSize() {
        return bookCovers.size();
    }

    private By getPageLocator() {
        return By.cssSelector(PAGE_ROOT_NODE);
    }

    private By getLoadingLocator() {
        return By.xpath("//*[contains(.,'Loading')]");
    }
}