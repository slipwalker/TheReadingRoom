package com.readingroom.tests.login;

import com.readingroom.block.SignIn;
import com.readingroom.pages.HomePage;
import com.readingroom.tests.DataProviderFactory;
import com.readingroom.util.driver.DriverFactory;
import com.readingroom.webdriver.ExtendedWebDriver;
import com.readingroom.models.User;
import com.readingroom.util.*;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Features({"Login"})
public class LoginTest {

    private HomePage homePage;

    private HomePage openHomePage() {
        return EntitiesContainer.getPageManager().getPageNavigator().getHomePage();
    }

    private HomePage getHomePage() {
        return homePage;
    }

    @Test(dataProvider = "WrongUsers", dataProviderClass = DataProviderFactory.class)
    @Stories("Login with incorrect credentials")
    @Description("Verify that it is NOT possible to login with incorrect credentials")
    public void CheckThatLoginIncorrectWithBadCred(User user) {
        SignIn signInForm = getHomePage().getSignInForm();
        signInForm
                .enterUserName(user.getName())
                .enterPassword(user.getPassword())
                .signIn()
                .verifyWarningDisplayed();

        assertThat("Check warning message: ", signInForm.getWarningMessage(), equalTo("Incorrect credentials"));
    }

    @Parameters({"url", "browser", "downloadFolder"})
    @BeforeSuite
    public void initSuite(@Optional("http://dev.thereadingroom.com/") String url,
                          @Optional("firefox") String browser,
                          @Optional("test-temp") String downloadFolder)
    {
        App.init(url, browser, downloadFolder);
    }

    @BeforeTest
    public void openLogin(XmlTest test) {
        ExtendedWebDriver driver = DriverFactory.initDriver(App.getBrowserName(), App.getBrowserDownloadFolder(), test.getName());
        EntitiesContainer.init(driver);
        homePage = openHomePage();
    }

    @AfterTest
    public void quitDriver() {
        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitAllDrivers();
    }
}