package com.readingroom.pages;

import com.readingroom.block.SignIn;
import com.readingroom.webdriver.ExtendedWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.Button;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

public class HomePage extends Page<HomePage> {

    private static final String PAGE_ROOT_NODE = "home-page";
    private static final String BRANDING_BAR_NODE = ".branding-bar";

    @FindBy(css = BRANDING_BAR_NODE + " [href*='/signup']")
    private Button signUpBtn;

    @FindBy(css = BRANDING_BAR_NODE + " [href*='/signin']")
    private Button signInBtn;

    private SignIn signIn;

    public HomePage(ExtendedWebDriver webDriver) {
        super(webDriver);
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

    @Step("Open Sign In form")
    public SignIn getSignInForm() {
        if (!web.isElementVisible(By.cssSelector(SignIn.SING_IN_FORM_NODE)))
            signInBtnClick();
        return signIn;
    }

    private void signInBtnClick() {
        signInBtn.click();
    }

    private void signUpBtnClick() {
        signUpBtn.click();
    }

    private By getPageLocator() {
        return By.className(PAGE_ROOT_NODE);
    }
}