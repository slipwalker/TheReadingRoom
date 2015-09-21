package com.readingroom.pages;

import com.readingroom.elements.DropdownMenu;
import com.readingroom.webdriver.ExtendedWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.Image;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

public class BookOverviewPage extends Page<BookOverviewPage> {

    private static final String PAGE_ROOT_NODE = "#book-contributor-overview";
    private static final String ALERT_NODE = "alert";

    @FindBy(className = "trr-book-reading-status-dropdown-action")
    private DropdownMenu addToShelfMenu;

    @FindBy(css = PAGE_ROOT_NODE + " .image img")
    private Image bookTile;

    @FindBy(className = ALERT_NODE)
    private WebElement alert;

    public BookOverviewPage(ExtendedWebDriver web) {
        super(web);
    }

    @Override
    public void init() {
    }

    @Override
    public void load() {
        web.waitUntilElementAppearVisible(getPageLocator());
    }

    @Override
    public void unload() {
        web.waitUntilElementDisappear(getPageLocator());
    }

    @Override
    public void isLoaded() throws Error {
        assertTrue(web.isElementVisible(getPageLocator()));
    }

    @Step("Add book to shelf {0}")
    public BookOverviewPage addToShelf(String bookShelfName) {
        addToShelfMenu.selectByVisibleText(bookShelfName);
        web.sleep(1000);
        return this;
    }

    public String getSelectedBookShelf() {
        return addToShelfMenu.getSelectedOption();
    }

    public String getAlertMessage() {
        web.waitUntilElementAppearVisible(By.className(ALERT_NODE));
        return alert.getText().trim();
    }

    private By getPageLocator() {
        return By.cssSelector(PAGE_ROOT_NODE);
    }
}