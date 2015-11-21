package com.readingroom.tests;

import com.readingroom.pages.HomePage;
import com.readingroom.util.*;
import com.readingroom.util.driver.DriverFactory;
import com.readingroom.util.testdata.TestDataContainer;
import com.readingroom.webdriver.ExtendedWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

public class TestBase {

    protected PageManager getPageManager() {
        return EntitiesContainer.getPageManager();
    }

    private HomePage openHomePage() {
        return getPageManager().getPageNavigator().getHomePage();
    }

    @Parameters({"url", "browser", "downloadFolder"})
    @BeforeSuite
    public void initSuite(@Optional("http://thereadingroom.com/") String url,
                          @Optional("firefox") String browser,
                          @Optional("test-temp") String downloadFolder)
    {
        App.init(url, browser, downloadFolder);
    }

    @BeforeTest
    public void openLogin(XmlTest test) {
        ExtendedWebDriver driver = DriverFactory.initDriver(App.getBrowserName(), App.getBrowserDownloadFolder(), test.getName());
        EntitiesContainer.init(driver, TestDataContainer.getUserByType("CorrectUser"));
        openHomePage()
                .getSignInForm()
                .loginAs(EntitiesContainer.getCurrentUser());
    }

    @AfterMethod
    public void setScreenshot(ITestResult result) {
        EntitiesContainer.getExtendedDriver().setScreenshot(result);
    }

    @AfterTest
    public void afterTest() {
        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitAllDrivers();
    }
}