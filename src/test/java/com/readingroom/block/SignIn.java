package com.readingroom.block;

import com.readingroom.elements.Edit;
import com.readingroom.models.User;
import com.readingroom.pages.UserOverviewPage;
import com.readingroom.util.EntitiesContainer;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import static com.readingroom.util.reporter.Reporter.reportScreenshot;

@FindBy(className = "signup-v2")
public class SignIn extends HtmlElement {

    public static final String SING_IN_FORM_NODE = ".signin-form";

    @FindBy(css = SING_IN_FORM_NODE + " #username_email")
    private Edit userName;

    @FindBy(css = SING_IN_FORM_NODE + " [name='password']")
    private Edit password;

    @FindBy(className = "btn-signin")
    private Button signInBtn;

    @FindBy(css = "[ng-repeat*='error']")
    private WebElement warningMessage;

    @Step
    public SignIn enterUserName(String name) {
        userName.type(name);
        return this;
    }

    @Step
    public SignIn enterPassword(String userPassword) {
        password.type(userPassword);
        return this;
    }

    @Step
    public SignIn signIn() {
        signInBtn.click();
        return this;
    }

    public UserOverviewPage loginAs(User user) {
        enterUserName(user.getName());
        enterPassword(user.getPassword());
        signIn();
        return new UserOverviewPage(EntitiesContainer.getExtendedDriver());
    }

    public String getWarningMessage() {
        return warningMessage.getText().trim();
    }

    @Step
    public SignIn verifyWarningDisplayed() {
        reportScreenshot("Warning Displayed");
        return this;
    }
}