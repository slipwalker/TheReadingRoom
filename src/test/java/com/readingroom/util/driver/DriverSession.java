package com.readingroom.util.driver;

import com.readingroom.webdriver.ExtendedWebDriver;

public class DriverSession {

    private String driverSession;
    private ExtendedWebDriver driver;
    private String testName;

    public DriverSession(String driverSession, ExtendedWebDriver driver, String testName) {
        this.driverSession = driverSession;
        this.driver = driver;
        this.testName = testName;
    }

    public String getDriverSession() {
        return driverSession;
    }

    public void setDriverSession(String driverSession) {
        this.driverSession = driverSession;
    }

    public ExtendedWebDriver getDriver() {
        return driver;
    }

    public void setDriver(ExtendedWebDriver driver) {
        this.driver = driver;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}